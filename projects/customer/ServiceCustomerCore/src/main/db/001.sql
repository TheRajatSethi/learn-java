CREATE TABLE customer (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    customer_type VARCHAR(10) NOT NULL,
    tenancy VARCHAR(10) not null
);

CREATE TABLE person (
    id INT PRIMARY KEY REFERENCES customer(id),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(10)
);

CREATE TABLE company (
    id INT PRIMARY KEY REFERENCES customer(id),
    name VARCHAR(255) NOT NULL,
    registration_date DATE,
    registration_number VARCHAR(100)
);

