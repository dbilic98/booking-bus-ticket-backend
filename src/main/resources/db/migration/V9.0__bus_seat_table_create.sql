CREATE TABLE bus_seat
(
    id               BIGINT(20) AUTO_INCREMENT NOT NULL,
    reservation_date DATE                      NOT NULL,
    is_taken         BIT(1)                    NOT NULL,
    bus_id           BIGINT,
    seat_id          BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE bus_seat
    ADD CONSTRAINT fk_bus_seat_bus FOREIGN KEY (bus_id) REFERENCES bus (id);

ALTER TABLE bus_seat
    ADD CONSTRAINT fk_bus_seat_seat FOREIGN KEY (seat_id) REFERENCES seat (id);