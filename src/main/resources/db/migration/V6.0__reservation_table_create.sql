CREATE TABLE reservation
(
    id                  BIGINT(20) AUTO_INCREMENT NOT NULL,
    date_of_reservation DATETIME                  NOT NULL,
    status              VARCHAR(20)               NOT NULL,
    user_id             BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_reservation_user_id FOREIGN KEY (user_id) REFERENCES user (id)
);