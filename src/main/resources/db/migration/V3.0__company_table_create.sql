CREATE TABLE company
(
    id           BIGINT(20) AUTO_INCREMENT NOT NULL,
    company_name VARCHAR(255) UNIQUE       NOT NULL,
    company_uuid BINARY(16)                NOT NULL,
    PRIMARY KEY (id)
);