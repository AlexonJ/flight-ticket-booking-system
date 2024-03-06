INSERT INTO passengers (first_name, last_name, document_number, telephone_number, email)
VALUES
    ('Leonardo', 'DiCaprio', 'ABC123456', '123-456-7890', 'leo@example.com'),
    ('Meryl', 'Streep', 'DEF987654', '987-654-3210', 'meryl@example.com'),
    ('Tom', 'Hanks', 'GHI246810', '456-789-0123', 'tom@example.com'),
    ('Jennifer', 'Lawrence', 'JKL369258', '789-012-3456', 'jennifer@example.com'),
    ('Robert', 'Downey Jr.', 'MNO123456', '111-222-3333', 'robert@example.com'),
    ('Angelina', 'Jolie', 'PQR987654', '222-333-4444', 'angelina@example.com'),
    ('Denzel', 'Washington', 'STU246810', '333-444-5555', 'denzel@example.com'),
    ('Scarlett', 'Johansson', 'VWX369258', '444-555-6666', 'scarlett@example.com'),
    ('Johnny', 'Depp', 'YZA159357', '555-666-7777', 'johnny@example.com'),
    ('Chris', 'Evans', 'ABC456789', '666-777-8888', 'chris@example.com'),
    ('Emma', 'Watson', 'DEF123789', '777-888-9999', 'emma@example.com'),
    ('Brad', 'Pitt', 'GHI789123', '888-999-0000', 'brad@example.com'),
    ('Jennifer', 'Aniston', 'JKL456123', '999-000-1111', 'jennifer.aniston@example.com'),
    ('Will', 'Smith', 'MNO789456', '000-111-2222', 'will@example.com'),
    ('Julia', 'Roberts', 'PQR123789', '111-222-3333', 'julia@example.com'),
    ('Matt', 'Damon', 'STU789123', '222-333-4444', 'matt@example.com'),
    ('Natalie', 'Portman', 'VWX456123', '333-444-5555', 'natalie@example.com'),
    ('Bradley', 'Cooper', 'YZA789456', '444-555-6666', 'bradley@example.com');

INSERT INTO seat_bookings (flight_id, flight_date, user_id, seat_id, booking_date, passenger_id)
VALUES (1, '2024-02-15', 2, 1, '2024-02-15 10:30:00', 1),
       (1, '2024-02-15', 2, 5, '2024-02-16 12:45:00', 2),
       (1, '2024-02-15', 2, 15, '2024-02-17 09:15:00', 3),
       (1, '2024-02-15', 3, 18, '2024-02-18 11:00:00', 4),
       (1, '2024-02-15', 3, 25, '2024-02-19 13:20:00', 5),
       (1, '2024-02-15', 3, 27, '2024-02-15 10:30:00', 6),
       (1, '2024-02-15', 4, 28, '2024-02-16 12:45:00', 7),
       (1, '2024-02-15', 5, 30, '2024-02-17 09:15:00', 8),
       (1, '2024-02-15', 5, 32, '2024-02-18 11:00:00', 9),
       (1, '2024-02-15', 5, 33, '2024-02-19 13:20:00', 10);