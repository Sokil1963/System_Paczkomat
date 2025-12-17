CREATE SEQUENCE IF NOT EXISTS awaria_paczkomatu_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS blokada_paczkomatu_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS historia_statusu_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS paczkomat_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS przesylka_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS skrytka_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sortownia_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS status_przesylki_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS uzytkownik_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS zadanie_dystrybucyjne_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS zgloszenie_nieprawidlowej_przesylki_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE awaria_paczkomatu
(
    id              INTEGER                     NOT NULL,
    paczkomat_id    INTEGER                     NOT NULL,
    wymaga_blokady  BOOLEAN                     NOT NULL,
    zglaszajacy_id  INTEGER,
    data_zgloszenia TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status_awarii   VARCHAR(20)                 NOT NULL,
    opis            TEXT                        NOT NULL,
    CONSTRAINT awaria_paczkomatu_pkey PRIMARY KEY (id)
);

CREATE TABLE blokada_paczkomatu
(
    awaria_id         INTEGER,
    id                INTEGER                     NOT NULL,
    paczkomat_id      INTEGER                     NOT NULL,
    data_blokady      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_odblokowania TIMESTAMP WITHOUT TIME ZONE,
    powod             TEXT,
    CONSTRAINT blokada_paczkomatu_pkey PRIMARY KEY (id)
);

CREATE TABLE historia_statusu
(
    id            INTEGER                     NOT NULL,
    przesylka_id  INTEGER                     NOT NULL,
    status_id     INTEGER                     NOT NULL,
    uzytkownik_id INTEGER,
    czas_zmiany   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    komentarz     TEXT,
    CONSTRAINT historia_statusu_pkey PRIMARY KEY (id)
);

CREATE TABLE paczkomat
(
    id           INTEGER     NOT NULL,
    sortownia_id INTEGER,
    kod          VARCHAR(20) NOT NULL,
    status       VARCHAR(20) NOT NULL,
    adres        TEXT        NOT NULL,
    CONSTRAINT paczkomat_pkey PRIMARY KEY (id)
);

CREATE TABLE przesylka_zadanie
(
    przesylka_id INTEGER NOT NULL,
    zadanie_id   INTEGER NOT NULL
);

CREATE TABLE skrytka
(
    id           INTEGER     NOT NULL,
    paczkomat_id INTEGER     NOT NULL,
    numer        VARCHAR(10) NOT NULL,
    rozmiar      VARCHAR(10) NOT NULL,
    status       VARCHAR(20) NOT NULL,
    CONSTRAINT skrytka_pkey PRIMARY KEY (id)
);

CREATE TABLE sortownia
(
    id    INTEGER      NOT NULL,
    nazwa VARCHAR(100) NOT NULL,
    adres TEXT         NOT NULL,
    CONSTRAINT sortownia_pkey PRIMARY KEY (id)
);

CREATE TABLE status_przesylki
(
    id   INTEGER     NOT NULL,
    kod  VARCHAR(30) NOT NULL,
    opis TEXT,
    CONSTRAINT status_przesylki_pkey PRIMARY KEY (id)
);

CREATE TABLE zadanie_dystrybucyjne
(
    id           INTEGER     NOT NULL,
    kurier_id    INTEGER,
    sortownia_id INTEGER,
    data_konca   TIMESTAMP WITHOUT TIME ZONE,
    data_startu  TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(20) NOT NULL,
    typ          VARCHAR(20) NOT NULL,
    CONSTRAINT zadanie_dystrybucyjne_pkey PRIMARY KEY (id)
);

CREATE TABLE zgloszenie_nieprawidlowej_przesylki
(
    id              INTEGER                     NOT NULL,
    przesylka_id    INTEGER                     NOT NULL,
    zglaszajacy_id  INTEGER,
    data_zgloszenia TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    typ             VARCHAR(30)                 NOT NULL,
    opis            TEXT,
    CONSTRAINT zgloszenie_nieprawidlowej_przesylki_pkey PRIMARY KEY (id)
);

ALTER TABLE paczkomat
    ADD CONSTRAINT paczkomat_kod_key UNIQUE (kod);

ALTER TABLE skrytka
    ADD CONSTRAINT skrytka_paczkomat_id_numer_key UNIQUE (paczkomat_id, numer);

ALTER TABLE status_przesylki
    ADD CONSTRAINT status_przesylki_kod_key UNIQUE (kod);

ALTER TABLE zadanie_dystrybucyjne
    ADD CONSTRAINT fk14914lcr8i6w0p0u74n7f7d19 FOREIGN KEY (sortownia_id) REFERENCES sortownia (id) ON DELETE NO ACTION;

ALTER TABLE awaria_paczkomatu
    ADD CONSTRAINT fk3xy6rwh1mmsuwfmi6o7llvjj3 FOREIGN KEY (paczkomat_id) REFERENCES paczkomat (id) ON DELETE NO ACTION;

ALTER TABLE przesylka_zadanie
    ADD CONSTRAINT fk4oidmod7p4qiha36ehfsecr93 FOREIGN KEY (zadanie_id) REFERENCES zadanie_dystrybucyjne (id) ON DELETE NO ACTION;

ALTER TABLE blokada_paczkomatu
    ADD CONSTRAINT fk6c7g409v6rpogy20130pg5o04 FOREIGN KEY (paczkomat_id) REFERENCES paczkomat (id) ON DELETE NO ACTION;

ALTER TABLE blokada_paczkomatu
    ADD CONSTRAINT fk7co3atdc0kc5w4vbl32kfuuom FOREIGN KEY (awaria_id) REFERENCES awaria_paczkomatu (id) ON DELETE NO ACTION;

ALTER TABLE paczkomat
    ADD CONSTRAINT fkffnnr49wam5y0jpv8ih6enyha FOREIGN KEY (sortownia_id) REFERENCES sortownia (id) ON DELETE NO ACTION;

ALTER TABLE skrytka
    ADD CONSTRAINT fkl3akxxmeluo69dyrj038o2r0u FOREIGN KEY (paczkomat_id) REFERENCES paczkomat (id) ON DELETE NO ACTION;

ALTER TABLE historia_statusu
    ADD CONSTRAINT fkop7577r6fynbr2wx96l292s83 FOREIGN KEY (status_id) REFERENCES status_przesylki (id) ON DELETE NO ACTION;
CREATE SEQUENCE IF NOT EXISTS awaria_paczkomatu_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS blokada_paczkomatu_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS historia_statusu_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS paczkomat_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS przesylka_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS skrytka_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sortownia_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS status_przesylki_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS uzytkownik_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS zadanie_dystrybucyjne_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS zgloszenie_nieprawidlowej_przesylki_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE awaria_paczkomatu
(
    id              INTEGER                     NOT NULL,
    paczkomat_id    INTEGER                     NOT NULL,
    wymaga_blokady  BOOLEAN                     NOT NULL,
    zglaszajacy_id  INTEGER,
    data_zgloszenia TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status_awarii   VARCHAR(20)                 NOT NULL,
    opis            TEXT                        NOT NULL,
    CONSTRAINT awaria_paczkomatu_pkey PRIMARY KEY (id)
);

CREATE TABLE blokada_paczkomatu
(
    awaria_id         INTEGER,
    id                INTEGER                     NOT NULL,
    paczkomat_id      INTEGER                     NOT NULL,
    data_blokady      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_odblokowania TIMESTAMP WITHOUT TIME ZONE,
    powod             TEXT,
    CONSTRAINT blokada_paczkomatu_pkey PRIMARY KEY (id)
);

CREATE TABLE historia_statusu
(
    id            INTEGER                     NOT NULL,
    przesylka_id  INTEGER                     NOT NULL,
    status_id     INTEGER                     NOT NULL,
    uzytkownik_id INTEGER,
    czas_zmiany   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    komentarz     TEXT,
    CONSTRAINT historia_statusu_pkey PRIMARY KEY (id)
);

CREATE TABLE paczkomat
(
    id           INTEGER     NOT NULL,
    sortownia_id INTEGER,
    kod          VARCHAR(20) NOT NULL,
    status       VARCHAR(20) NOT NULL,
    adres        TEXT        NOT NULL,
    CONSTRAINT paczkomat_pkey PRIMARY KEY (id)
);

CREATE TABLE przesylka_zadanie
(
    przesylka_id INTEGER NOT NULL,
    zadanie_id   INTEGER NOT NULL
);

CREATE TABLE skrytka
(
    id           INTEGER     NOT NULL,
    paczkomat_id INTEGER     NOT NULL,
    numer        VARCHAR(10) NOT NULL,
    rozmiar      VARCHAR(10) NOT NULL,
    status       VARCHAR(20) NOT NULL,
    CONSTRAINT skrytka_pkey PRIMARY KEY (id)
);

CREATE TABLE sortownia
(
    id    INTEGER      NOT NULL,
    nazwa VARCHAR(100) NOT NULL,
    adres TEXT         NOT NULL,
    CONSTRAINT sortownia_pkey PRIMARY KEY (id)
);

CREATE TABLE status_przesylki
(
    id   INTEGER     NOT NULL,
    kod  VARCHAR(30) NOT NULL,
    opis TEXT,
    CONSTRAINT status_przesylki_pkey PRIMARY KEY (id)
);

CREATE TABLE zadanie_dystrybucyjne
(
    id           INTEGER     NOT NULL,
    kurier_id    INTEGER,
    sortownia_id INTEGER,
    data_konca   TIMESTAMP WITHOUT TIME ZONE,
    data_startu  TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(20) NOT NULL,
    typ          VARCHAR(20) NOT NULL,
    CONSTRAINT zadanie_dystrybucyjne_pkey PRIMARY KEY (id)
);

CREATE TABLE zgloszenie_nieprawidlowej_przesylki
(
    id              INTEGER                     NOT NULL,
    przesylka_id    INTEGER                     NOT NULL,
    zglaszajacy_id  INTEGER,
    data_zgloszenia TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    typ             VARCHAR(30)                 NOT NULL,
    opis            TEXT,
    CONSTRAINT zgloszenie_nieprawidlowej_przesylki_pkey PRIMARY KEY (id)
);

ALTER TABLE paczkomat
    ADD CONSTRAINT paczkomat_kod_key UNIQUE (kod);

ALTER TABLE skrytka
    ADD CONSTRAINT skrytka_paczkomat_id_numer_key UNIQUE (paczkomat_id, numer);

ALTER TABLE status_przesylki
    ADD CONSTRAINT status_przesylki_kod_key UNIQUE (kod);

ALTER TABLE zadanie_dystrybucyjne
    ADD CONSTRAINT fk14914lcr8i6w0p0u74n7f7d19 FOREIGN KEY (sortownia_id) REFERENCES sortownia (id) ON DELETE NO ACTION;

ALTER TABLE awaria_paczkomatu
    ADD CONSTRAINT fk3xy6rwh1mmsuwfmi6o7llvjj3 FOREIGN KEY (paczkomat_id) REFERENCES paczkomat (id) ON DELETE NO ACTION;

ALTER TABLE przesylka_zadanie
    ADD CONSTRAINT fk4oidmod7p4qiha36ehfsecr93 FOREIGN KEY (zadanie_id) REFERENCES zadanie_dystrybucyjne (id) ON DELETE NO ACTION;

ALTER TABLE blokada_paczkomatu
    ADD CONSTRAINT fk6c7g409v6rpogy20130pg5o04 FOREIGN KEY (paczkomat_id) REFERENCES paczkomat (id) ON DELETE NO ACTION;

ALTER TABLE blokada_paczkomatu
    ADD CONSTRAINT fk7co3atdc0kc5w4vbl32kfuuom FOREIGN KEY (awaria_id) REFERENCES awaria_paczkomatu (id) ON DELETE NO ACTION;

ALTER TABLE paczkomat
    ADD CONSTRAINT fkffnnr49wam5y0jpv8ih6enyha FOREIGN KEY (sortownia_id) REFERENCES sortownia (id) ON DELETE NO ACTION;

ALTER TABLE skrytka
    ADD CONSTRAINT fkl3akxxmeluo69dyrj038o2r0u FOREIGN KEY (paczkomat_id) REFERENCES paczkomat (id) ON DELETE NO ACTION;

ALTER TABLE historia_statusu
    ADD CONSTRAINT fkop7577r6fynbr2wx96l292s83 FOREIGN KEY (status_id) REFERENCES status_przesylki (id) ON DELETE NO ACTION;