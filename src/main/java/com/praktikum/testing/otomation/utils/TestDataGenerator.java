package com.praktikum.testing.otomation.utils;

import java.util.Random;

public class TestDataGenerator {
    private static final Random random = new Random();

    // First names
    private static final String[] FIRST_NAMES = {
            "John", "Jane", "David", "Sarah", "Michael",
            "Lisa", "Robert", "Maria", "William", "Anna"
    };

    // Last names
    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones",
            "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"
    };

    // Generate random username
    public static String generateUsername() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        int randomNum = random.nextInt(1000);
        return firstName.toLowerCase() + lastName.toLowerCase() + randomNum;
    }

    // Generate random password
    public static String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    // Generate random email
    public static String generateEmail() {
        return generateUsername() + "@test.com";
    }

    // Generate random product name (from actual Demoblaze products)
    public static String getRandomProductName() {
        String[] products = {
                "Samsung galaxy s6", "Nokia lumia 1520", "Nexus 6",
                "Samsung galaxy s7", "Iphone 6 32gb", "Sony xperia z5",
                "HTC One M9", "Sony vaio i5", "Sony vaio i7"
        };
        return products[random.nextInt(products.length)];
    }

    // Generate test user for Demoblaze
    public static class TestUser {
        public String username;
        public String password;
        public String email;

        public TestUser() {
            this.username = generateUsername();
            this.password = generatePassword();
            this.email = generateEmail();
        }

        @Override
        public String toString() {
            return "TestUser{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    // Get a new test user
    public static TestUser getNewTestUser() {
        return new TestUser();
    }

    // Main method for testing
    public static void main(String[] args) {
        System.out.println("=== TEST DATA GENERATOR ===");
        System.out.println("Username: " + generateUsername());
        System.out.println("Password: " + generatePassword());
        System.out.println("Email: " + generateEmail());
        System.out.println("Product: " + getRandomProductName());

        TestUser user = getNewTestUser();
        System.out.println("\nTest User: " + user);
    }
}