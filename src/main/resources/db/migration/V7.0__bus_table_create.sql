CREATE TABLE bus
(
    id            BIGINT(20) AUTO_INCREMENT NOT NULL,
    model         VARCHAR(50)               NOT NULL,
    license_plate VARCHAR(20)               NOT NULL,
    seats         INTEGER(11)               NOT NULL,
    PRIMARY KEY (id)
);