package ru.otus.spring.homeworks.ticketpro.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.homeworks.ticketpro.config.SecurityConfig;
import ru.otus.spring.homeworks.ticketpro.dtos.SearchRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.SearchResponse;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightCreationRequest;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightDto;
import ru.otus.spring.homeworks.ticketpro.dtos.flights.FlightSummaryResponse;
import ru.otus.spring.homeworks.ticketpro.mappers.DtoMapper;
import ru.otus.spring.homeworks.ticketpro.mappers.DtoMapperImpl;
import ru.otus.spring.homeworks.ticketpro.models.db.ScheduleRecord;
import ru.otus.spring.homeworks.ticketpro.models.db.flights.Flight;
import ru.otus.spring.homeworks.ticketpro.models.search.FlightSearchFields;
import ru.otus.spring.homeworks.ticketpro.repositories.TestDataHolder;
import ru.otus.spring.homeworks.ticketpro.services.FlightService;
import ru.otus.spring.homeworks.ticketpro.services.UserDetailsServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Flight API controller test")
@WebMvcTest(FlightsApiController.class)
@Import({DtoMapperImpl.class, SecurityConfig.class, UserDetailsServiceImpl.class})
@AutoConfigureWebMvc
@WithMockUser(
        value = "flightservice",
        password = "mng",
        username = "flightservice",
        authorities = {"READ_FLIGHTS", "CREATE_FLIGHTS"}
)

public class FlightApiControllerTest {

    private static final String FLIGHT_URL = "/api/v1/flights";
    private static final String FLIGHT_SUMMARY_URL = "/api/v1/flights/{flightNumber}/{flightDate}";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FlightService flightService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
    }

    @DisplayName("Should return correct flights list")
    @Test
    public void shouldReturnCorrectFlightList() throws Exception {

        List<FlightDto> flightsDtoList = TestDataHolder.getDbFlights().stream().map(mapper::flightToFlightDto).toList();
        SearchResponse<FlightDto> searchResponse = new SearchResponse<>();
        searchResponse.setResult(flightsDtoList);
        when(flightService.getFlights(Mockito.any())).thenReturn(searchResponse);

        SearchRequest<FlightSearchFields> request = new SearchRequest<>();
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, FLIGHT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        SearchResponse<FlightDto> actualFlightsDtoList = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(flightsDtoList, actualFlightsDtoList.getResult());
    }

    @DisplayName("Should return flight summary data")
    @Test
    public void shouldReturnFlightSummaryData() throws Exception {
        Flight ExpectedFlight = TestDataHolder.getDbFlights().stream().findFirst().get();
        FlightSummaryResponse flightSummaryResponseExpected =
                FlightSummaryResponse.builder().seatsFree(200L).seatsBusy(50L).seatsTotalAmount(250L).build();
        String expectedFlightNumber = ExpectedFlight.getNumber();
        LocalDate expectedFlightDate = ExpectedFlight.getSchedule().get(0).getDepartureDateTime().toLocalDate();
        when(flightService.getFlightSummary(expectedFlightNumber, expectedFlightDate)).thenReturn(flightSummaryResponseExpected);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, FLIGHT_SUMMARY_URL, ExpectedFlight.getNumber(), expectedFlightDate))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        FlightSummaryResponse actualResponse = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(flightSummaryResponseExpected, actualResponse);
    }

    @DisplayName("Should return new flight")
    @Test
    public void shouldReturnNewFlightData() throws Exception {
        Flight expectedFlight = TestDataHolder.getDbFlights().stream().findFirst().get();
        FlightCreationRequest flightCreationRequest = getFlightCreationRequest(expectedFlight);
        when(flightService.addFlight(flightCreationRequest)).thenAnswer(
                invocationOnMock -> mapper.flightToFlightDto(getFlight(invocationOnMock.getArgument(0))));

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post(FLIGHT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightCreationRequest))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        FlightDto actualResponse = objectMapper.readValue(responseContent, new TypeReference<>() {});
        Assertions.assertEquals(mapper.flightToFlightDto(expectedFlight), actualResponse);
    }

    @DisplayName("Security - unauthenticated. Should return 401 (unauthorized) status")
    @Test
    @WithAnonymousUser
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get(FLIGHT_URL))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get(FLIGHT_SUMMARY_URL, "1", "2024-03-10"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Security - unauthenticated. Should return 403 (forbidden) status")
    @Test
    @WithMockUser(
            value = "flightservice",
            password = "mng",
            username = "flightservice",
            authorities = {"READ_BOOKINGS", "CREATE_BOOKINGS"}
    )
    public void testForbidden() throws Exception {
        mockMvc.perform(get(FLIGHT_URL))
                .andExpect(status().isForbidden());
        mockMvc.perform(get(FLIGHT_SUMMARY_URL, "1", "2024-03-10"))
                .andExpect(status().isForbidden());
    }

    private static FlightCreationRequest getFlightCreationRequest(Flight expectedFlight) {
        FlightCreationRequest flightCreationRequest = new FlightCreationRequest();
        flightCreationRequest.setId(expectedFlight.getId());
        flightCreationRequest.setAircraftId(expectedFlight.getAircraft().getId());
        flightCreationRequest.setNumber(expectedFlight.getNumber());
        flightCreationRequest.setAirlineId(expectedFlight.getAirline().getId());
        flightCreationRequest.setDepartureAirportId(expectedFlight.getDepartureAirport().getId());
        flightCreationRequest.setArrivalAirportId(expectedFlight.getArrivalAirport().getId());
        flightCreationRequest.setDepartureDateTime(expectedFlight.getSchedule().get(0).getDepartureDateTime());
        flightCreationRequest.setArrivalDateTime(expectedFlight.getSchedule().get(0).getArrivalDateTime());
        return flightCreationRequest;
    }

    private static Flight getFlight(FlightCreationRequest flightCreationRequest) {
        Flight flight = new Flight();
        flight.setId(flightCreationRequest.getId());
        flight.setNumber(flightCreationRequest.getNumber());
        flight.setAircraft(TestDataHolder.getAircraftById(flightCreationRequest.getAircraftId()));
        flight.setAirline(TestDataHolder.getAirlineById(flightCreationRequest.getAirlineId()));
        flight.setDepartureAirport(TestDataHolder.getAirportById(flightCreationRequest.getDepartureAirportId()));
        flight.setArrivalAirport(TestDataHolder.getAirportById(flightCreationRequest.getArrivalAirportId()));
        var flightScheduleRecord = new ScheduleRecord();
        flightScheduleRecord.setDepartureDateTime(flightCreationRequest.getDepartureDateTime());
        flightScheduleRecord.setArrivalDateTime(flightCreationRequest.getArrivalDateTime());
        flight.setSchedule(List.of(flightScheduleRecord));
        return flight;
    }
}
