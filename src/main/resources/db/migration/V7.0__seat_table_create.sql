CREATE TABLE seat
(
    id          BIGINT(20) AUTO_INCREMENT NOT NULL,
    seat_number TINYINT(6)                NOT NULL,
    is_taken BOOLEAN NOT NULL,
    bus_id BIGINT,
    reservation_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (bus_id) REFERENCES bus(id),
    FOREIGN KEY (reservation_id) REFERENCES reservation(id)
);