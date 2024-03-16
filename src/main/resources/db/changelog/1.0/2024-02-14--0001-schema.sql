CREATE TABLE airports
(
    id   BIGSERIAL PRIMARY KEY,
    code VARCHAR(6),
    city VARCHAR(128)
);

CREATE TABLE airlines
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE aircrafts
(
    id           BIGSERIAL PRIMARY KEY,
    model        VARCHAR(20),
    manufacturer VARCHAR(100)
);

CREATE TABLE seats
(
    id          BIGSERIAL PRIMARY KEY,
    row_number  INTEGER,
    position    CHARACTER,
    location    VARCHAR(20),
    aircraft_id BIGSERIAL REFERENCES aircrafts(id) ON DELETE CASCADE
);

CREATE TABLE flights
(
    id                      BIGSERIAL PRIMARY KEY,
    number                  VARCHAR(10),
    airline_id              BIGSERIAL REFERENCES airlines(id),
    aircraft_id             BIGSERIAL REFERENCES aircrafts(id),
    departure_airport_id    BIGSERIAL REFERENCES airports(id),
    arrival_airport_id      BIGSERIAL REFERENCES airports(id)

);

CREATE TABLE flights_schedule
(
    id                      BIGSERIAL PRIMARY KEY,
    flight_id               BIGSERIAL REFERENCES flights(id),
    departure_date_time     TIMESTAMP,
    arrival_date_time       TIMESTAMP
);

CREATE TABLE passengers
(
    id                      BIGSERIAL PRIMARY KEY,
    first_name              VARCHAR(50),
    last_name               VARCHAR(50),
    document_number         VARCHAR(20),
    telephone_number        VARCHAR(12),
    email                   VARCHAR(50)
);

CREATE TABLE users
(
    id                      BIGSERIAL PRIMARY KEY,
    username                VARCHAR(30),
    password                varchar(100),
    role                    VARCHAR(50)
);

CREATE TABLE seat_bookings
(
    id                      BIGSERIAL PRIMARY KEY,
    booking_date            TIMESTAMP,
    flight_id               BIGSERIAL REFERENCES flights(id),
    flight_date             DATE,
    user_id                 BIGSERIAL REFERENCES users(id),
    seat_id                 BIGSERIAL REFERENCES seats(id),
    passenger_id            BIGSERIAL REFERENCES passengers(id) ON DELETE CASCADE
);

CREATE TABLE roles_authorities
(
    id        BIGSERIAL PRIMARY KEY,
    role      VARCHAR(50),
    authority VARCHAR(50)
);