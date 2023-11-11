CREATE TABLE IF NOT EXISTS registration_users(
    id SERIAL PRIMARY KEY,
    "user_id"  BIGINT NOT NULL UNIQUE,
    registration_step INT NOT NULL DEFAULT 0,
    registration_complete boolean NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id)
)