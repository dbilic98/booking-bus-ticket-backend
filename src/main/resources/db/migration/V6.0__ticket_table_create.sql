CREATE TABLE ticket
(
    id                    BIGINT(20) AUTO_INCREMENT NOT NULL,
    price                 DECIMAL(10, 2)            NOT NULL,
    date_of_departure     DATETIME                  NOT NULL,
    reservation_id        BIGINT,
    one_way_route_id      BIGINT,
    return_route_id       BIGINT,
    passenger_category_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (reservation_id) REFERENCES reservation (id),
    FOREIGN KEY (one_way_route_id) REFERENCES route (id),
    FOREIGN KEY (return_route_id) REFERENCES route (id),
    FOREIGN KEY (passenger_category_id) REFERENCES passenger_category (id)
);