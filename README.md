# Student Management System

A robust, enterprise-grade desktop application designed to manage student records, courses, and grades. This project is built using a clean **Model-View-Controller (MVC)** architecture combined with a **Data Access Object (DAO)** pattern to ensure high maintainability, security, and separation of concerns.

---

## 🎯 Project Motives

This project was built with two primary goals in mind:

1. **Practical Application (Real-World Utility):**
   Educational institutions handle massive amounts of data. This system digitizes that process, replacing error-prone manual spreadsheets. It centralizes records, automates grade calculations, and uses Role-Based Access Control (RBAC) to ensure Students, Teachers, and Admins only see the data relevant to them.

2. **Technical Mastery (Software Engineering):**
   This project serves as a masterclass in professional software development. By transitioning from basic code to a clean MVC architecture with Maven, it enforces enterprise standards:
   * **Separation of Concerns:** UI code never mixes with database code.
   * **Security:** Implementing proper password hashing instead of storing plain-text passwords.
   * **Scalability:** The DAO pattern allows the underlying database to be swapped out easily without breaking the application.

---

## 🛠️ Technology Stack

This project utilizes a modern, professional Java stack:

*   **Language:** Java 21 (LTS)
*   **User Interface:** JavaFX 21 (FXML for structure, CSS for styling)
*   **Database:** MySQL (via `mysql-connector-j` 8.0.33)
*   **Data Access:** Raw JDBC (Java Database Connectivity) with the DAO design pattern.
*   **Security:** BCrypt (`jbcrypt` 0.4) for secure password hashing.
*   **Build & Dependency Management:** Maven
*   **Testing:** JUnit 5 (`junit-jupiter`) for Test-Driven Development (TDD).

---

## 🏛️ Architecture Overview

The codebase is strictly divided into distinct layers. Data flows sequentially through these layers, ensuring that no single file becomes too complex.

### 1. View (`src/main/resources/fxml` & `css`)
Dictates **how the app looks**. FXML files define the layout (buttons, text fields, tables), and CSS files define the styling. There is zero Java logic here.

### 2. Controller (`com.sms.controller`)
Acts as the bridge between the UI and the backend. Controllers listen for button clicks, read user input, and update the screen. **Controllers never talk to the database.**

### 3. Service Layer (`com.sms.service`)
Contains the "business rules" of the application. For example, `AuthService` handles the logic of checking if a user already exists and hashing their password before registration. Services coordinate multiple DAOs.

### 4. DAO Layer (`com.sms.dao`)
The **only** place in the app that talks directly to the database. Each table (Users, Courses, Grades) has its own DAO that executes raw SQL queries (`SELECT`, `INSERT`, `UPDATE`) and maps the results into Java Objects.

### 5. Model (`com.sms.model`)
Simple Java classes (POJOs) that represent a single row in your database (e.g., `User`, `Course`, `Grade`).

---

## 🔄 How Users Interact with the System

**You primarily interact with this system through the Graphical User Interface (UI).** You do not need to manually write MySQL queries to use the app day-to-day.

1. **The User Perspective:** 
   You interact with graphical windows—typing into text fields and clicking buttons like "Sign Up", "Login", or "Courses".
2. **The Background Magic:** 
   When you click a button (e.g., "Sign Up"), the Java code captures your input and automatically generates the appropriate SQL query (e.g., `INSERT INTO users...`). It sends this query to MySQL securely in the background.
3. **Direct Database Interaction:** 
   The only time a human interacts directly with the MySQL database is during initial setup (creating the tables) or if the developer needs to debug the raw data using a tool like MySQL Workbench.

---

## 🚀 How to Run the Project

To compile and launch the application, open your terminal in the root directory of the project and run the following Maven command:

```bash
.\mvnw.cmd clean javafx:run
```

*(Note: Ensure your MySQL database is running and the credentials in your `DBConnection` utility are correct before launching).*
