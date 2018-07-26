CREATE TABLE IF NOT EXISTS users (
    id INTEGER ,
    full_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS phones (
    id INTEGER ,
    num VARCHAR(255) NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);