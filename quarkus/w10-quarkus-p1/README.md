# Read Me

## I have implemented the basic funtionality of login and logout with quarkus and sqlite.



## SQL

```roomsql
-- Deprication begins
CREATE TABLE "users" (
    "id" INTEGER NOT NULL UNIQUE,
    "uuid" TEXT NOT NULL UNIQUE,
    "email" TEXT NOT NULL UNIQUE,
    "name" TEXT,
	"password" TEXT,
    "created" DATETIME DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW', 'LOCALTIME')),
    "updated" DATETIME DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW', 'LOCALTIME')),
    PRIMARY KEY("id" AUTOINCREMENT)
);
-- Deprication Ends


-- Better store timestamp in unix epoh at GMT time saves a lot of hastle later on with conversions and daylight saving time etc...
-- Seems to also work better with jdbi else if its stored in text fashion gets mapping exception

CREATE TABLE "users" (
    "id" INTEGER NOT NULL UNIQUE,
    "uuid" TEXT NOT NULL UNIQUE,
    "email" TEXT NOT NULL UNIQUE,
    "name" TEXT,
	"password" TEXT,
    "created" DATETIME DEFAULT (STRFTIME('%s', 'NOW')),
    "updated" DATETIME DEFAULT (STRFTIME('%s', 'NOW')),
    PRIMARY KEY("id" AUTOINCREMENT)
);


CREATE TRIGGER users_updated_row
AFTER UPDATE ON users
BEGIN
    update users SET updated = (STRFTIME('%s', 'NOW'));
END

CREATE TABLE "sessions" (
	"id"	TEXT NOT NULL UNIQUE,
	"userId"	INTEGER,
	"created"	DATETIME,
	"max" DATETIME,
	FOREIGN KEY("userId") REFERENCES "users"("id"),
	PRIMARY KEY("id")
);


```