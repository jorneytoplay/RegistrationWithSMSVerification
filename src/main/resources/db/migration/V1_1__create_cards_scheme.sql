CREATE TABLE IF NOT EXISTS cards(
    id serial PRIMARY KEY,
    number varchar(32) NOT NULL UNIQUE,
    blocked boolean NOT NULL DEFAULT FALSE,
    user_id INT,
    FOREIGN KEY(user_id) REFERENCES users(id)
)