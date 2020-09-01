DROP TABLE IF EXISTS users;
CREATE TABLE users (
                        id         SERIAL PRIMARY KEY,
                        first_name VARCHAR(30),
                        last_name  VARCHAR(30),
                        address    VARCHAR(255),
                        city       VARCHAR(80),
                        telephone  VARCHAR(20)
);

INSERT INTO users VALUES (1, 'John', 'Doe', '110 W. Liberty St.', 'Reston', '7035551212');