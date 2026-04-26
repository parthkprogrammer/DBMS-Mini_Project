package com.sms.controller;

import com.sms.Main;
import com.sms.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private AuthService authService = new AuthService();

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password.");
            return;
        }

        if (authService.login(username, password)) {
            try {
                Main.setRoot("Dashboard");
            } catch (Exception e) {
                e.printStackTrace();
                errorLabel.setText("Error loading dashboard.");
            }
        } else {
            errorLabel.setText("Invalid username or password.");
        }
    }
    @FXML
    public void handleGoToSignup(ActionEvent event) {
        try {
            Main.setRoot("Signup");
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error loading sign up page.");
        }
    }
}
