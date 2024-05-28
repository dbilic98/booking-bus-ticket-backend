CREATE TABLE bus
(
    id            BIGINT(20) AUTO_INCREMENT NOT NULL,
    model         VARCHAR(50)               NOT NULL,
    license_plate VARCHAR(20)               NOT NULL,
    company_id    BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_bus_company_id FOREIGN KEY (company_id) REFERENCES company (id)
);