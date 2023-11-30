CREATE TABLE bus_route
(
    id       BIGINT(20) AUTO_INCREMENT NOT NULL,
    bus_id   BIGINT,
    route_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (bus_id) REFERENCES bus(id),
    FOREIGN KEY (route_id) REFERENCES route(id)
);