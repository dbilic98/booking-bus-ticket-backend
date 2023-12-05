CREATE TABLE reservation
(
    id                    BIGINT(20) AUTO_INCREMENT NOT NULL,
    date_of_reservation   DATETIME                  NOT NULL,
    status                VARCHAR(20)               NOT NULL,
    passenger_category_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (passenger_category_id) REFERENCES passenger_category (id)
);