CREATE TABLE seat
(
    id          BIGINT(20) AUTO_INCREMENT NOT NULL,
    seat_number TINYINT(6)                NOT NULL,
    bus_id      BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_seat_bus_id FOREIGN KEY (bus_id) REFERENCES bus (id)
);