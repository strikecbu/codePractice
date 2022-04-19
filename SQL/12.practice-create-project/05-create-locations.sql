CREATE TABLE cities (
    name VARCHAR(20) PRIMARY KEY
);INSERT INTO locations (
    id,
    id,
    title,
    street,
    house_number,
    postal_code,
    city_name
  )
VALUES (
    'id:bigint',
    'id:bigint',
    'title:varchar',
    'street:varchar',
    'house_number:varchar',
    'postal_code:varchar',
    'city_name:varchar'
  );

CREATE TABLE locations (
    -- id INT PRIMARY KEY AUTO_INCREMENT, -- MYSQL ONLY
    id SERIAL PRIMARY KEY, -- POSTGRES ONLY
    title VARCHAR(300) NOT NULL,
    street VARCHAR(500) NOT NULL,
    house_number VARCHAR(10) NOT NULL,
    postal_code VARCHAR(5) NOT NULL,
    city_name VARCHAR(20) REFERENCES cities(name) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE events (
    id INT PRIMARY KEY AUTO_INCREMENT, -- MYSQL ONLY
    -- id SERIAL PRIMARY KEY, -- POSTGRES ONLY
    name VARCHAR(300) NOT NULL CHECK (LENGTH(name) > 5),
    date_planned TIMESTAMP NOT NULL,
    image VARCHAR(300),
    description TEXT NOT NULL,
    max_participants INT CHECK (max_participants > 0),
    min_age INT CHECK (min_age > 0),
    location_id INT REFERENCES locations ON DELETE CASCADE
);

