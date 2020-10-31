CREATE TABLE users (
                       id serial PRIMARY KEY,
                       user_name text,
                       password text,
                       role text,
                       status text
);

CREATE TABLE clients (
                         id serial PRIMARY KEY,
                         first_name text,
                         last_name text,
                         birth_day Date,
                         user_id bigint,
                         FOREIGN KEY(user_id) REFERENCES users(id)
);