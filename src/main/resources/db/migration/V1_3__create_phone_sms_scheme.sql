CREATE TABLE IF NOT EXISTS phone_sms(
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    code int NOT NULL,
    create_date BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
)