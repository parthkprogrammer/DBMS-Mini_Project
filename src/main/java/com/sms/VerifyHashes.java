package com.sms;

import com.sms.util.PasswordUtil;

public class VerifyHashes {
    public static void main(String[] args) {
        String adminHash = "$2a$10$9F/k/9GfC9XpWpP6C1TzT.t/T4e2aX2.6T2Z4X3.9W0Y0Z5Y0";
        String std2Hash = "$2a$10$S6XuJXoHoSEpQNhWo8LLEOrm97U7Nb8mBTQgBogOei/oLwt2Ez0zK";

        System.out.println("admin123 matches adminHash? " + PasswordUtil.checkPassword("admin123", adminHash));
        System.out.println("123456 matches std2Hash? " + PasswordUtil.checkPassword("123456", std2Hash));
        
        System.out.println("NEW admin123 hash: " + PasswordUtil.hashPassword("admin123"));
        System.out.println("NEW 123456 hash: " + PasswordUtil.hashPassword("123456"));
    }
}
