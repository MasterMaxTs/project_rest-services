CREATE TABLE IF NOT EXISTS person
(
    id       SERIAL,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    CONSTRAINT person_id_pk PRIMARY KEY (id),
    CONSTRAINT person_login_uniq UNIQUE (login)
);
