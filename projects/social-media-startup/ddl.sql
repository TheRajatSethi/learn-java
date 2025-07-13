CREATE TABLE
    users (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        uuid TEXT NOT NULL UNIQUE,
        email TEXT NOT NULL UNIQUE,
        password TEXT
    ) STRICT;

CREATE TABLE
    sqlite_sequence (name, seq);

CREATE TABLE
    groups (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        uuid TEXT NOT NULL UNIQUE,
        slug TEXT NOT NULL UNIQUE,
        name TEXT NOT NULL
    ) STRICT;

CREATE TABLE
    events (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        uuid TEXT NOT NULL UNIQUE,
        slug TEXT NOT NULL UNIQUE,
        name TEXT NOT NULL,
        start_time INTEGER NOT NULL,
        end_time INTEGER NOT NULL
    ) STRICT;