CREATE TABLE route
(
    id             BIGINT(20) AUTO_INCREMENT NOT NULL,
    base_price     FLOAT          NOT NULL,
    total_distance DECIMAL(10, 2) NOT NULL,
    start_place_id BIGINT,
    end_point_id   BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (start_place_id) REFERENCES place (id),
    FOREIGN KEY (end_point_id) REFERENCES place (id)
);