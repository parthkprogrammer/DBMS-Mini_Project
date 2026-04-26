package com.sms.controller;

import com.sms.Main;
import com.sms.model.User;
import com.sms.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private javafx.scene.control.Button coursesButton;

    @FXML
    private javafx.scene.control.Button gradesButton;

    @FXML
    private Label dashboardContentLabel;

    @FXML
    private javafx.scene.layout.VBox contentArea;

    @FXML
    public void initialize() {
        User user = AuthService.getCurrentUser();
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
            roleLabel.setText("Role: " + user.getRole().name());
            
            if (user.getRole() == com.sms.model.Role.STUDENT) {
                coursesButton.setText("My Courses");
                gradesButton.setText("My Grades");
                dashboardContentLabel.setText("Welcome to your Student Dashboard.\nHere you can view your enrolled courses and grades.");
            } else {
                coursesButton.setText("Manage Courses");
                gradesButton.setText("Student Profiles");
                dashboardContentLabel.setText("Welcome to the Admin/Teacher Dashboard.\nHere you can manage courses, grades, and student profiles.");
            }
        }
    }

    @FXML
    public void handleHome(ActionEvent event) {
        contentArea.getChildren().clear();
        User user = AuthService.getCurrentUser();
        
        if (user.getRole() == com.sms.model.Role.STUDENT) {
            // Student Home View: Motivational & Productive
            Label welcome = new Label("Welcome back, " + user.getUsername() + "!");
            welcome.getStyleClass().add("header-text");
            
            String[] quotes = {
                "\"The future belongs to those who believe in the beauty of their dreams.\" - Eleanor Roosevelt",
                "\"Success is the sum of small efforts, repeated day in and day out.\" - Robert Collier",
                "\"Education is the passport to the future.\" - Malcolm X",
                "\"Believe you can and you're halfway there.\" - Theodore Roosevelt"
            };
            String randomQuote = quotes[new java.util.Random().nextInt(quotes.length)];
            
            Label quoteLabel = new Label(randomQuote);
            quoteLabel.getStyleClass().add("sub-header-text");
            quoteLabel.setWrapText(true);
            quoteLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            quoteLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #555555;");
            
            com.sms.model.Student student = new com.sms.dao.impl.StudentDAOImpl().findByUserId(user.getId()).orElse(null);
            int courseCount = student != null ? new com.sms.dao.impl.EnrollmentDAOImpl().findByStudentId(student.getId()).size() : 0;
            
            Label statsLabel = new Label("You are currently enrolled in " + courseCount + " subjects.\nKeep pushing forward and stay productive!");
            statsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            statsLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            
            javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(20, welcome, quoteLabel, statsLabel);
            card.setAlignment(javafx.geometry.Pos.CENTER);
            card.getStyleClass().add("dashboard-card");
            card.setPadding(new javafx.geometry.Insets(40));
            card.setMaxWidth(600);
            
            contentArea.getChildren().add(card);
            
        } else {
            // Admin/Teacher Home View: Subject Information & Overview
            Label welcome = new Label("System Command Center");
            welcome.getStyleClass().add("header-text");
            
            Label subWelcome = new Label("Here is the current overview of the Student Management System.");
            subWelcome.getStyleClass().add("sub-header-text");
            
            int totalStudents = new com.sms.dao.impl.StudentDAOImpl().findAll().size();
            int totalCourses = new com.sms.service.CourseService().getAllCourses().size();
            int totalEnrollments = new com.sms.dao.impl.EnrollmentDAOImpl().findAll().size();
            
            javafx.scene.layout.HBox statsBox = new javafx.scene.layout.HBox(20);
            statsBox.setAlignment(javafx.geometry.Pos.CENTER);
            
            statsBox.getChildren().addAll(
                createStatCard("Registered Students", String.valueOf(totalStudents), "#3498db"),
                createStatCard("Active Subjects", String.valueOf(totalCourses), "#2ecc71"),
                createStatCard("Total Enrollments", String.valueOf(totalEnrollments), "#9b59b6")
            );
            
            javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(30, welcome, subWelcome, statsBox);
            card.setAlignment(javafx.geometry.Pos.CENTER);
            card.getStyleClass().add("dashboard-card");
            card.setPadding(new javafx.geometry.Insets(40));
            card.setMaxWidth(800);
            
            contentArea.getChildren().add(card);
        }
    }

    private javafx.scene.layout.VBox createStatCard(String title, String value, String colorHex) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d; -fx-font-weight: bold;");
        
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: " + colorHex + ";");
        
        javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(10, titleLabel, valueLabel);
        card.setAlignment(javafx.geometry.Pos.CENTER);
        card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10px; -fx-border-color: #e0e0e0; -fx-border-radius: 10px; -fx-padding: 20px;");
        card.setMinWidth(200);
        
        return card;
    }

    @FXML
    public void handleCourses(ActionEvent event) {
        contentArea.getChildren().clear();
        
        javafx.scene.control.TableView<com.sms.model.Course> table = new javafx.scene.control.TableView<>();
        
        javafx.scene.control.TableColumn<com.sms.model.Course, String> codeCol = new javafx.scene.control.TableColumn<>("Code");
        codeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("courseCode"));
        
        javafx.scene.control.TableColumn<com.sms.model.Course, String> nameCol = new javafx.scene.control.TableColumn<>("Name");
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("courseName"));
        
        javafx.scene.control.TableColumn<com.sms.model.Course, Integer> creditsCol = new javafx.scene.control.TableColumn<>("Credits");
        creditsCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("credits"));
        
        table.getColumns().addAll(codeCol, nameCol, creditsCol);
        table.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
        
        java.util.List<com.sms.model.Course> courses = new com.sms.service.CourseService().getAllCourses();
        table.setItems(javafx.collections.FXCollections.observableArrayList(courses));
        
        Label title = new Label("Courses");
        title.getStyleClass().add("header-text");
        
        javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(15, title, table);
        card.getStyleClass().add("dashboard-card");
        javafx.scene.layout.VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
        
        contentArea.getChildren().add(card);
    }

    @FXML
    public void handleGrades(ActionEvent event) {
        contentArea.getChildren().clear();
        
        javafx.scene.control.TableView<com.sms.model.StudentGradeRecord> table = new javafx.scene.control.TableView<>();
        
        javafx.scene.control.TableColumn<com.sms.model.StudentGradeRecord, Integer> enrollCol = new javafx.scene.control.TableColumn<>("Enrollment ID");
        enrollCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("enrollmentId"));
        
        javafx.scene.control.TableColumn<com.sms.model.StudentGradeRecord, String> studentCol = new javafx.scene.control.TableColumn<>("Student");
        studentCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("studentName"));
        
        javafx.scene.control.TableColumn<com.sms.model.StudentGradeRecord, String> codeCol = new javafx.scene.control.TableColumn<>("Course Code");
        codeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("courseCode"));
        
        javafx.scene.control.TableColumn<com.sms.model.StudentGradeRecord, String> nameCol = new javafx.scene.control.TableColumn<>("Course Name");
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("courseName"));
        
        javafx.scene.control.TableColumn<com.sms.model.StudentGradeRecord, Double> valCol = new javafx.scene.control.TableColumn<>("Score");
        valCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("gradeValue"));
        
        javafx.scene.control.TableColumn<com.sms.model.StudentGradeRecord, String> letterCol = new javafx.scene.control.TableColumn<>("Grade");
        letterCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("gradeLetter"));
        
        javafx.scene.control.TableColumn<com.sms.model.StudentGradeRecord, Integer> attCol = new javafx.scene.control.TableColumn<>("Attendance %");
        attCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("attendance"));
        
        User user = AuthService.getCurrentUser();
        
        if (user.getRole() == com.sms.model.Role.STUDENT) {
            table.getColumns().addAll(codeCol, nameCol, valCol, letterCol, attCol);
        } else {
            table.getColumns().addAll(enrollCol, studentCol, codeCol, nameCol, valCol, letterCol, attCol);
        }
        table.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
        
        java.util.List<com.sms.model.StudentGradeRecord> records = getGradeRecords(user);
        table.setItems(javafx.collections.FXCollections.observableArrayList(records));
        
        Label title = new Label(user.getRole() == com.sms.model.Role.STUDENT ? "My Grades" : "Student Profiles & Marks");
        title.getStyleClass().add("header-text");
        
        javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(15, title, table);
        card.getStyleClass().add("dashboard-card");
        javafx.scene.layout.VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
        
        contentArea.getChildren().add(card);
        
        // Admin Form for Assigning Grades
        if (user.getRole() != com.sms.model.Role.STUDENT) {
            javafx.scene.layout.HBox form = new javafx.scene.layout.HBox(10);
            form.setAlignment(javafx.geometry.Pos.CENTER);
            
            javafx.scene.control.TextField enrollInput = new javafx.scene.control.TextField();
            enrollInput.setPromptText("Enrollment ID");
            
            javafx.scene.control.TextField scoreInput = new javafx.scene.control.TextField();
            scoreInput.setPromptText("Score (0-100)");
            
            javafx.scene.control.TextField attInput = new javafx.scene.control.TextField();
            attInput.setPromptText("Attendance %");
            
            javafx.scene.control.Button assignBtn = new javafx.scene.control.Button("Assign Grade");
            assignBtn.getStyleClass().add("primary-button");
            
            Label msgLabel = new Label();
            
            assignBtn.setOnAction(e -> {
                try {
                    int eId = Integer.parseInt(enrollInput.getText());
                    double score = Double.parseDouble(scoreInput.getText());
                    int att = Integer.parseInt(attInput.getText());
                    
                    new com.sms.service.GradeService().assignGrade(eId, score, att, "Assigned by Admin");
                    msgLabel.setText("Grade assigned!");
                    msgLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                    
                    // Refresh table
                    table.setItems(javafx.collections.FXCollections.observableArrayList(getGradeRecords(user)));
                } catch (Exception ex) {
                    msgLabel.setText("Invalid input!");
                    msgLabel.setTextFill(javafx.scene.paint.Color.RED);
                }
            });
            
            form.getChildren().addAll(enrollInput, scoreInput, attInput, assignBtn);
            javafx.scene.layout.VBox formCard = new javafx.scene.layout.VBox(10, new Label("Assign Marks & Attendance"), form, msgLabel);
            formCard.getStyleClass().add("dashboard-card");
            formCard.setAlignment(javafx.geometry.Pos.CENTER);
            
            contentArea.getChildren().add(formCard);
        }
    }
    
    private java.util.List<com.sms.model.StudentGradeRecord> getGradeRecords(User user) {
        java.util.List<com.sms.model.StudentGradeRecord> records = new java.util.ArrayList<>();
        com.sms.dao.EnrollmentDAO edao = new com.sms.dao.impl.EnrollmentDAOImpl();
        com.sms.service.GradeService gs = new com.sms.service.GradeService();
        com.sms.dao.CourseDAO cdao = new com.sms.dao.impl.CourseDAOImpl();
        com.sms.dao.StudentDAO sdao = new com.sms.dao.impl.StudentDAOImpl();
        
        java.util.List<com.sms.model.Enrollment> enrolls = new java.util.ArrayList<>();
        if (user.getRole() == com.sms.model.Role.STUDENT) {
            com.sms.model.Student student = sdao.findByUserId(user.getId()).orElse(null);
            if (student != null) {
                enrolls = edao.findByStudentId(student.getId());
            }
        } else {
            enrolls = edao.findAll();
        }
        
        for (com.sms.model.Enrollment e : enrolls) {
            com.sms.model.Grade g = gs.getGradeForEnrollment(e.getId()).orElse(new com.sms.model.Grade());
            com.sms.model.Course c = cdao.findById(e.getCourseId()).orElse(new com.sms.model.Course());
            com.sms.model.Student s = sdao.findById(e.getStudentId()).orElse(new com.sms.model.Student());
            
            String sName = (s.getFirstName() != null ? s.getFirstName() : "") + " " + (s.getLastName() != null ? s.getLastName() : "");
            records.add(new com.sms.model.StudentGradeRecord(
                e.getId(), sName.trim(), c.getCourseCode(), c.getCourseName(), 
                g.getGradeValue(), g.getGradeLetter(), g.getAttendance()
            ));
        }
        return records;
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            new AuthService().logout();
            Main.setRoot("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
