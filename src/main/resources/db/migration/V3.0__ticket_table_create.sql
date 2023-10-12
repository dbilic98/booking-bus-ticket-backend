CREATE TABLE ticket
(
    id                BIGINT(20) AUTO_INCREMENT NOT NULL,
    price             DECIMAL(10, 2)            NOT NULL,
    date_of_departure DATETIME                  NOT NULL,
    one_way_route_id  BIGINT                    NOT NULL,
    return_route_id   BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE ticket
    ADD CONSTRAINT fk_ticket_one_way_route FOREIGN KEY (one_way_route_id) REFERENCES route (id);

ALTER TABLE ticket
    ADD CONSTRAINT fk_ticket_return_route FOREIGN KEY (return_route_id) REFERENCES route (id);
