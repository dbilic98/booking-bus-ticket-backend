CREATE TABLE route
(
    id             BIGINT(20) AUTO_INCREMENT NOT NULL,
    base_price     FLOAT                     NOT NULL,
    total_distance DECIMAL(10, 2)            NOT NULL,
    start_place_id BIGINT,
    end_place_id   BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_route_start_place_id FOREIGN KEY (start_place_id) REFERENCES place (id),
    CONSTRAINT fk_route_end_place_id FOREIGN KEY (end_place_id) REFERENCES place (id)
);