CREATE TABLE passenger_category_price
(
    id                    BIGINT(20) AUTO_INCREMENT NOT NULL,
    price                 DECIMAL(10, 2)            NOT NULL,
    route_id              BIGINT,
    passenger_category_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (route_id) REFERENCES route (id),
    FOREIGN KEY (passenger_category_id) REFERENCES passenger_category (id)
);