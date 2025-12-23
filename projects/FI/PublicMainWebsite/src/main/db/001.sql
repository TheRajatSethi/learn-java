-- Users table
CREATE TABLE User(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Categories table
CREATE TABLE Category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- Tags table
CREATE TABLE Tag (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Posts table
CREATE TABLE Post (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES "User"(id) ON DELETE CASCADE,
    category_id INTEGER REFERENCES Category(id) ON DELETE SET NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Post-Tag many-to-many relationship
CREATE TABLE PostTag (
    post_id INTEGER REFERENCES Post(id) ON DELETE CASCADE,
    tag_id INTEGER REFERENCES Tag(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);


