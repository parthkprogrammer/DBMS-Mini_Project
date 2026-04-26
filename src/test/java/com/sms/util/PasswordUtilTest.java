package com.sms.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    public void testHashPassword() {
        String password = "mySecretPassword123";
        String hash = PasswordUtil.hashPassword(password);
        
        assertNotNull(hash);
        assertNotEquals(password, hash);
        assertTrue(hash.startsWith("$2a$"));
    }

    @Test
    public void testCheckPasswordSuccess() {
        String password = "mySecretPassword123";
        String hash = PasswordUtil.hashPassword(password);
        
        assertTrue(PasswordUtil.checkPassword(password, hash));
    }

    @Test
    public void testCheckPasswordFailure() {
        String password = "mySecretPassword123";
        String wrongPassword = "wrongPassword";
        String hash = PasswordUtil.hashPassword(password);
        
        assertFalse(PasswordUtil.checkPassword(wrongPassword, hash));
    }
}
