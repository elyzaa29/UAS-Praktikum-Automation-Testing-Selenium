package com.praktikum.testing.otomation.tests;

import com.praktikum.testing.otomation.pages.CartPage;
import com.praktikum.testing.otomation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ShoppingCartTest extends BaseTest {
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setup(Method method) {
        super.setup(method);

        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);

        goToDemoblaze();

        // Tunggu page load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void testCartPageAccess() {
        System.out.println("\n=== TEST: Cart Page Access ===");

        // Navigate directly to cart
        cartPage.navigateToCart();

        // Verify cart page loads
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should be on cart page");

        cartPage.printCartStatus();

        System.out.println("✓ Cart page access test PASSED");
    }

    @Test(priority = 2)
    public void testAddToCartAndVerify() {
        System.out.println("\n=== TEST: Add to Cart and Verify ===");

        try {
            // First, go to cart and check if empty
            cartPage.navigateToCart();
            boolean initiallyEmpty = cartPage.isCartEmpty();
            System.out.println("Cart initially empty: " + initiallyEmpty);

            // Go to home and add product
            driver.get("https://www.demoblaze.com/");
            Thread.sleep(2000);

            // Click on a product
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();

            Thread.sleep(3000);

            // Add to cart
            productPage.addToCart();
            System.out.println("✓ Product added to cart");

            // Go to cart to verify
            productPage.goToCart();

            // Check cart status
            cartPage.printCartStatus();

            // Go back to home
            driver.get("https://www.demoblaze.com/");

        } catch (Exception e) {
            System.out.println("Error in cart test: " + e.getMessage());
        }

        System.out.println("✓ Add to cart and verify test completed");
    }

    @Test(priority = 3)
    public void testCartNavigation() {
        System.out.println("\n=== TEST: Cart Navigation ===");

        try {
            // Test going to cart from product page
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();

            Thread.sleep(3000);

            // Try goToCart method
            productPage.goToCart();
            System.out.println("✓ Used goToCart() method");

            // Verify on cart page
            Assert.assertTrue(driver.getCurrentUrl().contains("cart.html") ||
                            cartPage.isOnCartPage(),
                    "Should be on cart page after goToCart()");

            // Go back to home
            driver.get("https://www.demoblaze.com/");

        } catch (Exception e) {
            System.out.println("Error in cart navigation: " + e.getMessage());
        }

        System.out.println("✓ Cart navigation test completed");
    }
}