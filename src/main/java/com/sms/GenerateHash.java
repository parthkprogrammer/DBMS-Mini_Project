package com.sms;

import com.sms.util.PasswordUtil;

public class GenerateHash {
    public static void main(String[] args) {
        System.out.println("HASH FOR 123456: " + PasswordUtil.hashPassword("123456"));
    }
}
