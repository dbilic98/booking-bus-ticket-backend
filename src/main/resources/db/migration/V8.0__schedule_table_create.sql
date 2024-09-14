CREATE TABLE schedule
(
    id             BIGINT(20) AUTO_INCREMENT NOT NULL,
    schedule_date  DATE                      NOT NULL,
    departure_time TIME                      NOT NULL,
    arrival_time   TIME                      NOT NULL,
    route_id       BIGINT,
    bus_id         BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_schedule_route_id FOREIGN KEY (route_id) REFERENCES route (id),
    CONSTRAINT fk_schedule_bus_id FOREIGN KEY (bus_id) REFERENCES bus (id)
);