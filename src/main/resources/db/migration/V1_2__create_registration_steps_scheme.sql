CREATE TABLE IF NOT EXISTS registration_users(
    id SERIAL PRIMARY KEY,
    "phone"  varchar(15) NOT NULL UNIQUE,
    "full_name" varchar(50),
    "date_of_birth" Date,
    registration_step INT NOT NULL DEFAULT 0,
    registration_complete boolean NOT NULL DEFAULT FALSE
)