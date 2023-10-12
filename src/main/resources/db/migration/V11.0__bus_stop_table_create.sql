CREATE TABLE bus_stop
(
    id BIGINT(20) AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    place_id BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE bus_stop
ADD CONSTRAINT fk_bus_stop_place FOREIGN KEY (place_id) REFERENCES place (id);