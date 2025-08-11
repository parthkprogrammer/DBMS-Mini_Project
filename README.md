# Student Information Management System

## 📌 Overview
This is a **JavaFX-based DBMS mini-project** designed to manage student information with role-based access for **Admin** and **User**.  
The system connects to a **MySQL database** and provides functionalities for adding, editing, viewing, and messaging students.

## 🚀 Features
### Admin
- Login to the admin dashboard
- Add new students
- Edit student details
- View all student records
- Send messages to students

### User
- Login as a student
- View personal information
- View messages from admin
- Contact the admin

## 🛠 Tech Stack
- **JavaFX** – UI framework
- **MySQL** – Database
- **Maven** – Build automation
- **FXML** – UI design
- **JDBC** – Database connectivity

## 📂 Project Structure
```
demo/
├── dump.sql                  # MySQL database dump
├── pom.xml                   # Maven dependencies
├── src/main/java             # Java source files
│   ├── com/example/demo      # Controllers
│   └── StudentInfo           # Database utility classes
└── src/main/resources        # FXML layouts and UI resources
```

## ⚙️ Setup Instructions
1. **Clone the repository**
   ```bash
   git clone https://github.com/parthkprogrammer/DBMS-Mini_Project.git
   cd DBMS-Mini_Project/demo
   ```

2. **Import Database**
   - Create a MySQL database (e.g., `student_db`).
   - Import `dump.sql` into MySQL:
     ```bash
     mysql -u root -p student_db < dump.sql
     ```

3. **Configure Database Connection**
   - Open `DBUtils.java` in `StudentInfo` package.
   - Update your MySQL username, password, and database name.

4. **Run the Application**
   - Using Maven:
     ```bash
     mvn clean install
     mvn javafx:run
     ```
   - Or run `Main.java` directly from your IDE.

## 🔑 Default Credentials
- **Admin**:  
  Username: `admin`  
  Password: `admin123`

- **User**:  
  Username: `student1`  
  Password: `student123`

*(You can change these in the database after importing.)*

## 📜 License
This project is for **educational purposes** only and can be freely modified for learning.
