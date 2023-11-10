CREATE TABLE IF NOT EXISTS phone_sms(
    id SERIAL PRIMARY KEY,
    phone varchar(15) NOT NULL UNIQUE,
    code int NOT NULL,
    create_date BIGINT NOT NULL
)