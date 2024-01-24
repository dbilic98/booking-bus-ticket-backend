CREATE TABLE passenger_category
(
    id                  BIGINT(20) AUTO_INCREMENT NOT NULL,
    category_name       VARCHAR(255) UNIQUE       NOT NULL,
    discount_percentage FLOAT             NOT NULL,
    PRIMARY KEY (id)
);