CREATE TABLE "users"(
    "id" serial PRIMARY KEY,
    "phone"  varchar(12) NOT NULL UNIQUE,
    "mail" varchar(50),
    "full_name" varchar(50),
    "date_of_birth" Date,
    "password" varchar(255),
    "refresh_token" varchar(255)
)