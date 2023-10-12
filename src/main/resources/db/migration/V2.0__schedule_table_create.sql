CREATE TABLE schedule
(
    id             BIGINT(20) AUTO_INCREMENT NOT NULL,
    schedule_date  DATE       NOT NULL,
    departure_time TIME       NOT NULL,
    arrival_time   TIME       NOT NULL,
    route_id BIGINT,
    PRIMARY KEY (id)
);

ALTER TABLE schedule
    ADD CONSTRAINT fk_schedule_route FOREIGN KEY (route_id) REFERENCES route (id);
