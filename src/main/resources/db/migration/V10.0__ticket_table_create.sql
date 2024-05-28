CREATE TABLE ticket
(
    id                    BIGINT(20) AUTO_INCREMENT NOT NULL,
    price                 FLOAT                     NOT NULL,
    one_way_route_id      BIGINT,
    return_route_id       BIGINT,
    reservation_id        BIGINT,
    one_way_schedule_id   BIGINT,
    return_schedule_id    BIGINT,
    passenger_category_id BIGINT,
    one_way_seat_id       BIGINT,
    return_seat_id        BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_ticket_one_way_route_id FOREIGN KEY (one_way_route_id) REFERENCES route (id),
    CONSTRAINT fk_ticket_return_route_id FOREIGN KEY (return_route_id) REFERENCES route (id),
    CONSTRAINT fk_ticket_reservation_id FOREIGN KEY (reservation_id) REFERENCES reservation (id),
    CONSTRAINT fk_ticket_one_way_schedule_id FOREIGN KEY (one_way_schedule_id) REFERENCES schedule (id),
    CONSTRAINT fk_ticket_return_schedule_id FOREIGN KEY (return_schedule_id) REFERENCES schedule (id),
    CONSTRAINT fk_ticket_passenger_category_id FOREIGN KEY (passenger_category_id) REFERENCES passenger_category (id),
    CONSTRAINT fk_ticket_one_way_seat_id FOREIGN KEY (one_way_seat_id) REFERENCES seat (id),
    CONSTRAINT fk_ticket_return_seat_id FOREIGN KEY (return_seat_id) REFERENCES seat (id)
);