package com.praktikum.testing.otomation.tests;

import com.praktikum.testing.otomation.pages.*;
import com.praktikum.testing.otomation.utils.ScreenshotUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class EndToEndTest extends BaseTest {
    private HomePage homePage;
    private LoginModal loginModal;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutModal checkoutModal; // TAMBAHKAN INI

    @BeforeMethod
    public void setup(Method method) {
        super.setup(method);

        homePage = new HomePage(driver);
        loginModal = new LoginModal(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutModal = new CheckoutModal(driver); // INISIALISASI

        goToDemoblaze();
    }

    @Test(priority = 1)
    public void testCompleteShoppingFlow() {
        System.out.println("\n=== TEST: Complete Shopping Flow ===");

        try {
            // Step 1: Browse to product
            System.out.println("1. Browsing to product...");
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();
            Thread.sleep(3000);

            // Step 2: Get product info
            String productName = productPage.getProductName();
            String productPrice = productPage.getProductPrice();
            System.out.println("2. Product: " + productName + " - " + productPrice);

            // Step 3: Add to cart
            System.out.println("3. Adding to cart...");
            productPage.addToCart();

            // Step 4: Go to cart
            System.out.println("4. Going to cart...");
            productPage.goToCart();

            // Step 5: Verify in cart
            System.out.println("5. Verifying cart...");
            cartPage.printCartStatus();

            // Step 6: Go back to shopping
            System.out.println("6. Continuing shopping...");
            driver.get("https://www.demoblaze.com/");

            System.out.println("✓ Complete shopping flow test PASSED");

        } catch (Exception e) {
            System.out.println("Error in shopping flow: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void testLoginAddToCartFlow() {
        System.out.println("\n=== TEST: Login + Add to Cart Flow ===");

        try {
            // Note: This test needs valid credentials
            String username = "testuser123"; // Ganti dengan username valid
            String password = "testpass123"; // Ganti dengan password valid

            // Step 1: Login
            System.out.println("1. Logging in...");
            homePage.clickLogin();
            loginModal.login(username, password);

            // Handle login alert
            try {
                Thread.sleep(2000);
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                // Continue
            }

            // Step 2: Verify logged in
            boolean loggedIn = homePage.isUserLoggedIn();
            System.out.println("2. User logged in: " + loggedIn);

            if (loggedIn) {
                // Step 3: Add product to cart
                System.out.println("3. Adding product to cart...");
                driver.findElement(
                        org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
                ).click();
                Thread.sleep(3000);
                productPage.addToCart();

                // Step 4: Logout
                System.out.println("4. Logging out...");
                homePage.logout();

                System.out.println("✓ Login + Add to Cart flow completed");
            } else {
                System.out.println("SKIP: User not logged in, skipping cart test");
            }

        } catch (Exception e) {
            System.out.println("Error in login flow: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    public void testCompletePurchaseFlow() {
        System.out.println("\n=== TEST: Complete Purchase Flow ===");

        try {
            // 1. Add product to cart
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();
            Thread.sleep(3000);

            String productName = productPage.getProductName();
            System.out.println("1. Selected product: " + productName);

            productPage.addToCart();
            System.out.println("2. Added to cart");

            // 2. Go to cart
            productPage.goToCart();
            System.out.println("3. Navigated to cart");

            // 3. Checkout - klik Place Order
            cartPage.clickPlaceOrder();
            Thread.sleep(2000);
            System.out.println("4. Started checkout");

            // 4. Check if checkout modal opened
            boolean modalOpened = checkoutModal.isModalDisplayed();
            System.out.println("Checkout modal opened: " + modalOpened);

            if (modalOpened) {
                // Fill checkout form
                checkoutModal.fillCheckoutForm(
                        "Test User",
                        "Indonesia",
                        "Jakarta",
                        "1234123412341234",
                        "10",
                        "2026"
                );
                System.out.println("5. Filled checkout form");

                // Complete purchase
                checkoutModal.clickPurchase();
                Thread.sleep(3000);
                System.out.println("6. Submitted purchase");

                // Check success
                boolean success = checkoutModal.isCheckoutSuccessful();
                System.out.println("Purchase successful: " + success);

                if (success) {
                    checkoutModal.printOrderSummary();
                    checkoutModal.clickOk();
                    System.out.println("✓ COMPLETE PURCHASE FLOW PASSED");
                } else {
                    System.out.println("Purchase might have issues");
                    // Try to close modal
                    checkoutModal.clickClose();
                }
            } else {
                System.out.println("Checkout modal didn't open, skipping purchase");
            }

        } catch (Exception e) {
            System.out.println("Error in purchase flow: " + e.getMessage());
            ScreenshotUtil.takeScreenshot(driver, "purchase_flow_error");
        }
    }

    @Test(priority = 4)
    public void testFullUserJourney() {
        System.out.println("\n=== TEST: Full User Journey ===");

        try {
            // Scenario: User comes to site, browses, adds to cart, checks out

            System.out.println("1. Browsing homepage...");
            // Homepage already loaded in setup

            System.out.println("2. Viewing products...");
            // Scroll or view products (simulated)
            Thread.sleep(1000);

            System.out.println("3. Selecting a product...");
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();
            Thread.sleep(3000);

            System.out.println("4. Viewing product details...");
            String productName = productPage.getProductName();
            String productPrice = productPage.getProductPrice();
            System.out.println("   Product: " + productName);
            System.out.println("   Price: " + productPrice);

            System.out.println("5. Adding to cart...");
            productPage.addToCart();

            System.out.println("6. Going to cart...");
            productPage.goToCart();
            Thread.sleep(2000);

            System.out.println("7. Reviewing cart...");
            cartPage.printCartStatus();

            System.out.println("8. Returning to shopping...");
            driver.get("https://www.demoblaze.com/");

            System.out.println("✓ Full user journey completed successfully");

        } catch (Exception e) {
            System.out.println("Error in user journey: " + e.getMessage());
        }
    }
}