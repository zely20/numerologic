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


select * from clients;
delete from clients where id = 3;
drop table users, clients;
select * from users;

select * from users, clients where clients.user_id = users.id;