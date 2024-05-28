CREATE TABLE bus_route
(
    id       BIGINT(20) AUTO_INCREMENT NOT NULL,
    bus_id   BIGINT,
    route_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_bus_route_bus_id FOREIGN KEY (bus_id) REFERENCES bus (id),
    CONSTRAINT fk_bus_route_route_id FOREIGN KEY (route_id) REFERENCES route (id)
);