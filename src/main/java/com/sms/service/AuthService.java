package com.sms.service;

import com.sms.dao.UserDAO;
import com.sms.dao.impl.UserDAOImpl;
import com.sms.model.Role;
import com.sms.model.User;
import com.sms.util.PasswordUtil;

import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO;
    private static User currentUser; // Session state

    public AuthService() {
        this.userDAO = new UserDAOImpl();
    }

    public boolean login(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtil.checkPassword(password, user.getPasswordHash())) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public void registerUser(String username, String rawPassword, Role role) throws Exception {
        if (userDAO.findByUsername(username).isPresent()) {
            throw new Exception("Username already exists.");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(PasswordUtil.hashPassword(rawPassword));
        newUser.setRole(role);
        userDAO.save(newUser);
    }
}
