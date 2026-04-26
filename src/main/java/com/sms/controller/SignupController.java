package com.sms.controller;

import com.sms.Main;
import com.sms.model.Role;
import com.sms.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    private AuthService authService = new AuthService();

    @FXML
    public void handleSignup(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Please fill all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        try {
            authService.registerUser(username, password, Role.STUDENT);
            // Automatically log them in after registration
            if (authService.login(username, password)) {
                Main.setRoot("Dashboard");
            } else {
                errorLabel.setText("Registration successful, but login failed.");
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void handleGoToLogin(ActionEvent event) {
        try {
            Main.setRoot("Login");
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error loading login page.");
        }
    }
}
