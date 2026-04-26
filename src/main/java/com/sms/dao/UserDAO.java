package com.sms.dao;

import com.sms.model.User;
import java.util.Optional;

public interface UserDAO extends GenericDAO<User, Integer> {
    Optional<User> findByUsername(String username);
}
