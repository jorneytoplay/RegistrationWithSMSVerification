CREATE TABLE "users"(
    "id" serial PRIMARY KEY,
    "phone"  varchar(12) NOT NULL UNIQUE,
    "mail" varchar(50),
    "full_name" varchar(50) NOT NULL,
    "date_of_birth" Date NOT NULL,
    "password" varchar(255) NOT NULL
)