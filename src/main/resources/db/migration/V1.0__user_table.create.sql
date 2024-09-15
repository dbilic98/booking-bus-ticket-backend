CREATE TABLE user
(
    id         BIGINT(20) AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255)              NOT NULL,
    last_name  VARCHAR(255)              NOT NULL,
    username   VARCHAR(255)              NOT NULL,
    user_uuid  BINARY(16)                NOT NULL,
    PRIMARY KEY (id)
);