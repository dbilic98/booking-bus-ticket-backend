CREATE TABLE route
(
    id             BIGINT(20) AUTO_INCREMENT NOT NULL,
    start_point    VARCHAR(255)   NOT NULL,
    end_point      VARCHAR(255)   NOT NULL,
    base_price     FLOAT          NOT NULL,
    total_distance DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
);