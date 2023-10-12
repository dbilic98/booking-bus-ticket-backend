CREATE TABLE bus_stop_route
(
    id BIGINT(20) AUTO_INCREMENT NOT NULL,
    stop_order INTEGER(11) NOT NULL,
    bus_stop_id BIGINT,
    route_id BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE bus_stop_route
ADD CONSTRAINT fk_bus_stop_route_bus_stop FOREIGN KEY (bus_stop_id) REFERENCES bus_stop (id);

ALTER TABLE bus_stop_route
ADD CONSTRAINT fk_bus_stop_route_route FOREIGN KEY (route_id) REFERENCES route (id);