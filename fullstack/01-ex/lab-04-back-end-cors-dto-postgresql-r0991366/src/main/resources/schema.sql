DROP TABLE IF EXISTS course_lecturer;
DROP TABLE IF EXISTS lecturer;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS users;


CREATE TABLE lecturer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    expertise VARCHAR(100)
);

CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    name VARCHAR(25) NOT NULL CHECK (length(name) BETWEEN 5 AND 25),
    credits INT CHECK (credits BETWEEN 1 AND 18)
);

CREATE TABLE course_lecturer (
    course_id   INTEGER NOT NULL,
    lecturer_id INTEGER NOT NULL,
    PRIMARY KEY (course_id, lecturer_id),
    FOREIGN KEY (course_id)   REFERENCES course(id)   ON DELETE CASCADE,
    FOREIGN KEY (lecturer_id) REFERENCES lecturer(id) ON DELETE CASCADE
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    birth_date DATE,
    role TEXT NOT NULL
);
