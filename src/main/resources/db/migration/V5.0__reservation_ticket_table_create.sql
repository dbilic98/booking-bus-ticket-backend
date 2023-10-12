CREATE TABLE reservation_ticket
(
    id             BIGINT(20) AUTO_INCREMENT NOT NULL,
    reservation_id BIGINT,
    ticket_id      BIGINT,
    PRIMARY KEY (id)
);
ALTER TABLE reservation_ticket
    ADD CONSTRAINT fk_reservation_ticket_reservation FOREIGN KEY (reservation_id) REFERENCES reservation (id);

ALTER TABLE reservation_ticket
    ADD CONSTRAINT fk_reservation_ticket_ticket FOREIGN KEY (ticket_id) REFERENCES ticket (id);