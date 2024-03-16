package ru.otus.spring.homeworks.ticketpro.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.otus.spring.homeworks.ticketpro.dtos.bookings.SeatBookingDto;
import ru.otus.spring.homeworks.ticketpro.mappers.DtoMapper;
import ru.otus.spring.homeworks.ticketpro.mappers.DtoMapperImpl;
import ru.otus.spring.homeworks.ticketpro.models.search.FlightSearchFields;
import ru.otus.spring.homeworks.ticketpro.repositories.TestDataHolder;
import ru.otus.spring.homeworks.ticketpro.services.BookingService;
import ru.otus.spring.homeworks.ticketpro.services.UserDetailsServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Bookings API controller test")
@WebMvcTest(BookingsApiController.class)
@Import({DtoMapperImpl.class, SecurityConfig.class, UserDetailsServiceImpl.class})
@AutoConfigureWebMvc
@WithMockUser(
        value = "bookingservice",
        password = "mng",
        username = "bookingservice",
        authorities = {"READ_BOOKINGS", "CREATE_BOOKINGS", "READ_BOOKINGS_OWNER" }
)
public class BookingsControllerTest {
    private static final String BOOKINGS_URL = "/api/v1/bookings";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookingService bookingService;

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

    @DisplayName("Should return correct bookings list")
    @Test
    public void shouldReturnCorrectBookingList() throws Exception {

        List<SeatBookingDto> bookingsDtoList = TestDataHolder.getDbBookings().stream().map(mapper::seatBookingToSeatBookingDto).toList();
        when(bookingService.findBookings(Mockito.any())).thenReturn(bookingsDtoList);

        SearchRequest<FlightSearchFields> request = new SearchRequest<>();
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, BOOKINGS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        SearchResponse<SeatBookingDto> actualBookingsDtoList = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(bookingsDtoList, actualBookingsDtoList.getResult());
    }

    @DisplayName("Security - unauthenticated. Should return 401 (unauthorized) status")
    @Test
    @WithAnonymousUser
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get(BOOKINGS_URL))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post(BOOKINGS_URL).with(csrf()))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(delete(BOOKINGS_URL).with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Security - unauthenticated. Should return 403 (forbidden) status")
    @Test
    @WithMockUser(
            value = "flightservice",
            password = "mng",
            username = "flightservice",
            authorities = {"READ_FLIGHTS", "CREATE_FLIGHTS"}
    )
    public void testForbidden() throws Exception {
        mockMvc.perform(get(BOOKINGS_URL))
                .andExpect(status().isForbidden());
        mockMvc.perform(post(BOOKINGS_URL))
                .andExpect(status().isForbidden());
        mockMvc.perform(delete(BOOKINGS_URL))
                .andExpect(status().isForbidden());
    }
}
