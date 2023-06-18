CREATE TABLE IF NOT EXISTS person
(
    id       SERIAL,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    gender CHAR(1) NOT NULL,
    birthday DATE NOT NULL,
    CONSTRAINT person_id_pk PRIMARY KEY (id),
    CONSTRAINT person_login_uniq UNIQUE (login),
    CONSTRAINT person_email_uniq UNIQUE (email)
);
