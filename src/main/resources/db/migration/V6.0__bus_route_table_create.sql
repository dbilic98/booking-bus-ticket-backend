CREATE TABLE bus_route
(
    id       BIGINT(20) AUTO_INCREMENT NOT NULL,
    bus_id   BIGINT,
    route_id BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE bus_route
    ADD CONSTRAINT fk_bus_route_bus FOREIGN KEY (bus_id) REFERENCES bus (id);

ALTER TABLE bus_route
    ADD CONSTRAINT fk_bus_route_route FOREIGN KEY (route_id) REFERENCES route (id);