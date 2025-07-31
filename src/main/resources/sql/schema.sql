CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       roles VARCHAR(100) NOT NULL DEFAULT 'ROLE_USER',
                       full_name VARCHAR(100) NOT NULL,
                       balance NUMERIC(19,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              user_id INT NOT NULL REFERENCES users(id),
                              amount NUMERIC(19,2) NOT NULL,
                              type VARCHAR(10) NOT NULL, -- CREDIT or DEBIT
                              timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
                              description VARCHAR(255)
);