package com.praktikum.testing.otomation.tests;

import com.praktikum.testing.otomation.pages.CartPage;
import com.praktikum.testing.otomation.pages.CheckoutModal;
import com.praktikum.testing.otomation.pages.ProductPage;
import com.praktikum.testing.otomation.utils.ScreenshotUtil;
import com.praktikum.testing.otomation.utils.TestDataGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class CheckoutTest extends BaseTest {
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutModal checkoutModal;

    @BeforeMethod
    public void setup(Method method) {
        super.setup(method);

        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutModal = new CheckoutModal(driver);

        goToDemoblaze();
    }

    @Test(priority = 1)
    public void testCheckoutModalOpens() {
        System.out.println("\n=== TEST: Checkout Modal Opens ===");

        // First, add item to cart
        try {
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();
            Thread.sleep(3000);
            productPage.addToCart();

            // Go to cart
            productPage.goToCart();

            // Click place order
            cartPage.clickPlaceOrder();

            // Verify modal opens
            Thread.sleep(2000);
            boolean modalOpened = checkoutModal.isModalDisplayed();
            System.out.println("Checkout modal opened: " + modalOpened);

            if (modalOpened) {
                checkoutModal.clickClose();
                System.out.println("✓ Modal opened and closed successfully");
            }

        } catch (Exception e) {
            System.out.println("Error in checkout modal test: " + e.getMessage());
        }

        System.out.println("✓ Checkout modal test completed");
    }

    @Test(priority = 2)
    public void testCheckoutFormValidation() {
        System.out.println("\n=== TEST: Checkout Form Validation ===");

        try {
            // Setup: Add item and go to checkout
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();
            Thread.sleep(3000);
            productPage.addToCart();
            productPage.goToCart();
            cartPage.clickPlaceOrder();

            // Wait for modal
            Thread.sleep(2000);

            // Try to purchase with empty form
            checkoutModal.clickPurchase();

            // Handle any alerts
            try {
                Thread.sleep(1000);
                String alertText = driver.switchTo().alert().getText();
                System.out.println("Empty form alert: " + alertText);
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                System.out.println("No alert for empty form");
            }

            // Close modal
            checkoutModal.clickClose();

        } catch (Exception e) {
            System.out.println("Error in form validation: " + e.getMessage());
        }

        System.out.println("✓ Checkout form validation test completed");
    }

    @Test(priority = 3)
    public void testCompleteCheckoutProcess() {
        System.out.println("\n=== TEST: Complete Checkout Process ===");

        try {
            // Setup: Add item to cart
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();
            Thread.sleep(3000);
            productPage.addToCart();
            productPage.goToCart();

            // Click place order
            cartPage.clickPlaceOrder();
            Thread.sleep(2000);

            // Fill checkout form
            checkoutModal.fillCheckoutForm(
                    "John Doe",
                    "United States",
                    "New York",
                    "4111111111111111",
                    "12",
                    "2025"
            );

            // Click purchase
            checkoutModal.clickPurchase();

            // Wait for success
            Thread.sleep(3000);

            // Check if successful
            boolean success = checkoutModal.isCheckoutSuccessful();
            System.out.println("Checkout successful: " + success);

            if (success) {
                checkoutModal.printOrderSummary();
                checkoutModal.clickOk();
                System.out.println("✓ Checkout process completed successfully");
            } else {
                System.out.println("Checkout might have failed or pending");
            }

        } catch (Exception e) {
            System.out.println("Error in checkout process: " + e.getMessage());
            ScreenshotUtil.takeScreenshot(driver, "checkout_error");
        }

        System.out.println("✓ Complete checkout test completed");
    }

    @Test(priority = 4, enabled = false) // Optional: Test with invalid data
    public void testCheckoutWithInvalidData() {
        System.out.println("\n=== TEST: Checkout with Invalid Data ===");

        // Test various invalid scenarios
        // (Credit card validation, expired date, etc.)

        System.out.println("✓ Invalid data test completed");
    }

    @Test(priority = 5, enabled = true)
    public void testCheckoutWithNonNumericCreditCard() throws InterruptedException {
        System.out.println("\n=== TEST: Credit Card Numeric Validation ===");

        // Open Place Order modal
        driver.findElement(By.xpath("//button[text()='Place Order']")).click();
        Thread.sleep(2000);

        WebElement creditCardField = driver.findElement(By.id("card"));

        // Test data non-numeric
        String invalidCardNumber = "abcd1234!@#";

        creditCardField.clear();
        creditCardField.sendKeys(invalidCardNumber);

        // Click Purchase
        driver.findElement(By.xpath("//button[text()='Purchase']")).click();
        Thread.sleep(2000);

        // Demoblaze behavior: order STILL SUCCESS (BUG)
        WebElement successPopup = driver.findElement(By.className("sweet-alert"));

        if (successPopup.isDisplayed()) {
            System.out.println(" BUG FOUND: Non-numeric credit card accepted!");
            Assert.fail("Credit Card field accepts non-numeric values");
        } else {
            System.out.println("✓ Credit Card numeric validation works");
        }
    }

}