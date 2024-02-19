CREATE TABLE ticket
(
    id                    BIGINT(20) AUTO_INCREMENT NOT NULL,
    price                 FLOAT                     NOT NULL,
    reservation_id        BIGINT,
    one_way_route_id      BIGINT,
    return_route_id       BIGINT,
    passenger_category_id BIGINT,
    one_way_schedule_id   BIGINT,
    return_schedule_id    BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (reservation_id) REFERENCES reservation (id),
    FOREIGN KEY (one_way_route_id) REFERENCES route (id),
    FOREIGN KEY (return_route_id) REFERENCES route (id),
    FOREIGN KEY (passenger_category_id) REFERENCES passenger_category (id),
    FOREIGN KEY (one_way_schedule_id) REFERENCES schedule (id),
    FOREIGN KEY (return_schedule_id) REFERENCES schedule (id)
);