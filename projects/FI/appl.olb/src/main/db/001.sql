-------------- BLOG -------------

CREATE TABLE Category (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE Tag (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Post (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title VARCHAR NOT NULL,
    category_id INTEGER REFERENCES Category(id) ON DELETE SET NULL,
    content TEXT NOT NULL,
    draft boolean,
    published_at DATE,
    updated_at DATE
);

-- many-to-many relationship
CREATE TABLE PostTag (
    post_id INTEGER REFERENCES Post(id) ON DELETE CASCADE,
    tag_id INTEGER REFERENCES Tag(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);

-------------- BLOG DATA -------------


INSERT INTO main.category ("name",description) VALUES
	 ('investment','Investment'),
	 ('retirement','Retirement'),
	 ('foreign-currency','Foreign Currency');


-------------- ACCOUNT -------------

CREATE TABLE Account(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(1024),
    created_at TIMESTAMPZ DEFAULT NOW()
);

--CREATE TABLE Profile(
--    id INTEGER REFERENCES Account(id) ON DELETE CASCADE,
--);

CREATE TABLE Role(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) UNIQUE NOT NULL,
);

CREATE TABLE AccountRole(
    account_id INTEGER REFERENCES Account(id) ON DELETE CASCADE,
    role_id INTEGER REFERENCES Role(id) ON DELETE CASCADE,
    PRIMARY KEY (account_id, role_id)
);
