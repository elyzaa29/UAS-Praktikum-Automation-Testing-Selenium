package com.praktikum.testing.otomation.tests;

import com.praktikum.testing.otomation.pages.HomePage;
import com.praktikum.testing.otomation.pages.SignupModal;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class UserRegistrationTest extends BaseTest {
    private HomePage homePage;
    private SignupModal signupModal;

    @BeforeMethod
    public void setup(Method method) {
        super.setup(method);

        homePage = new HomePage(driver);
        signupModal = new SignupModal(driver);

        goToDemoblaze();
    }

    @Test(priority = 1)
    public void testSignupModalOpens() {
        System.out.println("\n=== TEST: Signup Modal Opens ===");

        // Click signup link
        homePage.clickSignup();

        // Verify modal opens
        Assert.assertTrue(signupModal.isModalDisplayed(),
                "Signup modal should be displayed");

        System.out.println("✓ Signup modal opened");

        // Close modal
        signupModal.clickClose();

        System.out.println("✓ Test PASSED");
    }

    @Test(priority = 2)
    public void testSignupWithRandomUser() {
        System.out.println("\n=== TEST: Signup with Random User ===");

        homePage.clickSignup();

        // Generate random username
        String randomUser = "testuser_" + System.currentTimeMillis();
        String password = "Test@123";

        System.out.println("Attempting signup with: " + randomUser);

        // Fill signup form
        signupModal.enterUsername(randomUser);
        signupModal.enterPassword(password);
        signupModal.clickSignup();

        // Handle alert
        try {
            Thread.sleep(2000);
            String alertText = driver.switchTo().alert().getText();
            System.out.println("Alert: " + alertText);
            driver.switchTo().alert().accept();

            // Check if successful or user exists
            if (alertText.contains("successful") || alertText.contains("Sign up")) {
                System.out.println("✓ Signup successful");
            } else if (alertText.contains("already") || alertText.contains("exist")) {
                System.out.println("✓ User already exists (expected for some cases)");
            }
        } catch (Exception e) {
            System.out.println("No alert: " + e.getMessage());
        }

        System.out.println("✓ Test completed");
    }

    @Test(priority = 3)
    public void testEmptySignupForm() {
        System.out.println("\n=== TEST: Empty Signup Form ===");

        homePage.clickSignup();

        // Try to signup with empty fields
        signupModal.clickSignup();

        // Handle alert
        try {
            Thread.sleep(1000);
            String alertText = driver.switchTo().alert().getText();
            System.out.println("Empty form alert: " + alertText);
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("No alert: " + e.getMessage());
        }

        signupModal.clickClose();
        System.out.println("✓ Test completed");
    }
}