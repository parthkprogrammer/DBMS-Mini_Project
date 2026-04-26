CREATE DATABASE IF NOT EXISTS sms_db;
USE sms_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    email VARCHAR(100) UNIQUE,
    enrollment_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    teacher_id INT,
    credits INT NOT NULL DEFAULT 3,
    FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    UNIQUE(student_id, course_id)
);

CREATE TABLE IF NOT EXISTS grades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT NOT NULL,
    grade_value DECIMAL(5,2),
    grade_letter VARCHAR(2),
    attendance INT,
    remarks TEXT,
    graded_date DATE,
    FOREIGN KEY (enrollment_id) REFERENCES enrollments(id) ON DELETE CASCADE,
    UNIQUE(enrollment_id)
);

-- Optional: Initial Data Setup
-- Note: 'admin123' and '123456' hashes
INSERT IGNORE INTO users (id, username, password_hash, role) VALUES 
(1, 'admin', '$2a$10$b3C3OMMNzQ5PkIAZI7yUVOqQIzjV0dGe8cVIGChnLFRRx08Wb0yEi', 'ADMIN'),
(2, 'teacher1', '$2a$10$b3C3OMMNzQ5PkIAZI7yUVOqQIzjV0dGe8cVIGChnLFRRx08Wb0yEi', 'TEACHER'),
(3, 'std1', '$2a$10$b3C3OMMNzQ5PkIAZI7yUVOqQIzjV0dGe8cVIGChnLFRRx08Wb0yEi', 'STUDENT'),
(4, 'std2', '$2a$10$S6XuJXoHoSEpQNhWo8LLEOrm97U7Nb8mBTQgBogOei/oLwt2Ez0zK', 'STUDENT'),
(5, 'std3', '$2a$10$S6XuJXoHoSEpQNhWo8LLEOrm97U7Nb8mBTQgBogOei/oLwt2Ez0zK', 'STUDENT');

INSERT IGNORE INTO students (id, user_id, first_name, last_name, date_of_birth, email, enrollment_date) VALUES 
(1, 3, 'John', 'Doe', '2000-01-01', 'john@example.com', '2023-09-01'),
(2, 4, 'Jane', 'Smith', '2001-02-15', 'jane@example.com', '2023-09-01'),
(3, 5, 'Alice', 'Johnson', '2000-11-20', 'alice@example.com', '2023-09-01');

INSERT IGNORE INTO courses (id, course_code, course_name, teacher_id, credits) VALUES 
(1, 'CS101', 'Intro to Computer Science', 2, 4),
(2, 'MATH101', 'Calculus I', 2, 3),
(3, 'PHY101', 'Physics I', 2, 4);

INSERT IGNORE INTO enrollments (id, student_id, course_id, enrollment_date) VALUES 
(1, 1, 1, '2023-09-02'),
(2, 1, 2, '2023-09-03'),
(3, 2, 1, '2023-09-02'),
(4, 2, 3, '2023-09-04'),
(5, 3, 2, '2023-09-03');

INSERT IGNORE INTO grades (id, enrollment_id, grade_value, grade_letter, attendance, remarks, graded_date) VALUES 
(1, 1, 95.5, 'A', 98, 'Excellent work!', '2023-12-15'),
(2, 2, 82.0, 'B', 85, 'Good effort', '2023-12-16'),
(3, 3, 88.5, 'B', 92, 'Solid understanding', '2023-12-15'),
(4, 4, 75.0, 'C', 78, 'Needs to participate more', '2023-12-18'),
(5, 5, 91.0, 'A', 95, 'Great job!', '2023-12-16');
