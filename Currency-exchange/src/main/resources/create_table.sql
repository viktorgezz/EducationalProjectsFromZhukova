CREATE TABLE Currencies
(
    id        integer PRIMARY KEY AUTOINCREMENT,
    code      varchar(3) UNIQUE NOT NULL,
    full_name varchar(80),
    sign      varchar(1)
);

CREATE TABLE Exchange_rates
(
    id                 integer PRIMARY KEY AUTOINCREMENT,
    base_currency_id   integer references Currencies (code)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    target_currency_id integer references Currencies (code)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    rate               decimal(6)
);