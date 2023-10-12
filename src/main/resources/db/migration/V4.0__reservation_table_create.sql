CREATE TABLE reservation
(
    id                  BIGINT(20) AUTO_INCREMENT NOT NULL,
    date_of_reservation DATETIME                  NOT NULL,
    status              VARCHAR(20)               NOT NULL,
    price               DECIMAL(10, 2)            NOT NULL,
    PRIMARY KEY (id)
);