INSERT INTO users(username, role, password)
VALUES ('admin', 'ADMIN', '$2a$12$WgTiyMzOuqjID1J.SWj50ulVD5VYKk9BS9B2YegG/XQ.6p/YaeyXC'),       -- adm
       ('skywalker92', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),  -- usr
       ('traveler123', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),  -- usr
       ('flyhigh007', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),   -- usr
       ('jetsetter22', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),  -- usr
       ('airborne88', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),   -- usr
       ('wanderlust99', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'), -- usr
       ('aviator55', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),    -- usr
       ('frequentflyer23', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'), -- usr
       ('cloudsurfer77', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'), -- usr
       ('adventureseeker11', 'USER', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'), -- usr
       ('bookingservice', 'MANAGER_BOOKING', '$2a$12$4Vk8blySGcA6XDQ82NH8oO5e00SF5aeTUDl9iN0fPqObtkktJhjf.'), -- mng
       ('flightservice', 'MANAGER_FLIGHTS', '$2a$12$4Vk8blySGcA6XDQ82NH8oO5e00SF5aeTUDl9iN0fPqObtkktJhjf.'); -- mng

INSERT INTO roles_authorities(role, authority)
VALUES
    -- ADMIN AUTHORITIES
    ('ADMIN', 'READ_BOOKINGS'),
    ('ADMIN', 'CREATE_BOOKINGS'),
    ('ADMIN', 'DELETE_BOOKINGS'),
    ('ADMIN', 'READ_FLIGHTS'),
    ('ADMIN', 'CREATE_FLIGHTS'),
    ('ADMIN', 'DELETE_FLIGHTS'),
    ('ADMIN', 'READ_SCHEDULE'),
    ('ADMIN', 'CREATE_SCHEDULE'),
    ('ADMIN', 'DELETE_SCHEDULE'),
    ('ADMIN', 'READ_SEATS'),

    -- USER AUTHORITIES
    ('USER', 'READ_BOOKINGS_OWNER'),
    ('USER', 'CREATE_BOOKINGS'),
    ('USER', 'DELETE_BOOKINGS_OWNER'),
    ('USER', 'READ_FLIGHTS'),
    ('USER', 'READ_SEATS'),

    -- MANAGER_BOOKINGS AUTHORITIES
    ('MANAGER_BOOKINGS', 'READ_BOOKINGS'),
    ('MANAGER_BOOKINGS', 'CREATE_BOOKINGS'),
    ('MANAGER_BOOKINGS', 'DELETE_BOOKINGS'),
    ('MANAGER_BOOKINGS', 'READ_SEATS'),
    ('MANAGER_BOOKINGS', 'READ_FLIGHTS'),

    -- MANAGER_FLIGHTS AUTHORITIES
    ('MANAGER_FLIGHTS', 'READ_FLIGHTS'),
    ('MANAGER_FLIGHTS', 'CREATE_BOOKINGS'),
    ('MANAGER_FLIGHTS', 'DELETE_BOOKINGS'),
    ('MANAGER_FLIGHTS', 'READ_FLIGHTS'),
    ('MANAGER_FLIGHTS', 'READ_SEATS'),

    -- MANAGER_SCHEDULE AUTHORITIES
    ('MANAGER_SCHEDULE', 'READ_SCHEDULE'),
    ('MANAGER_SCHEDULE', 'CREATE_SCHEDULE'),
    ('MANAGER_SCHEDULE', 'DELETE_SCHEDULE'),
    ('MANAGER_SCHEDULE', 'READ_FLIGHTS'),
    ('MANAGER_SCHEDULE', 'READ_SEATS'),

    -- AUDITOR AUTHORITIES
    ('MANAGER_FLIGHTS', 'READ_FLIGHTS'),
    ('MANAGER_FLIGHTS', 'READ_BOOKINGS'),
    ('MANAGER_FLIGHTS', 'READ_SEATS'),
    ('MANAGER_FLIGHTS', 'READ_SCHEDULE');