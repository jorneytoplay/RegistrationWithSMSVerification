create table if not exists roles(
    id serial PRIMARY KEY ,
    role varchar(15) NOT NULL UNIQUE
);

create table if not exists users_roles_relation(
    id serial PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    UNIQUE (user_id,role_id)
)