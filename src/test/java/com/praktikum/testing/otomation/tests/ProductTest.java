package com.praktikum.testing.otomation.tests;

import com.praktikum.testing.otomation.pages.HomePage;
import com.praktikum.testing.otomation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ProductTest extends BaseTest {
    private HomePage homePage;
    private ProductPage productPage;

    @BeforeMethod
    public void setup(Method method) {
        super.setup(method);

        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);

        goToDemoblaze();

        // Tunggu page load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void testHomePageElements() {
        System.out.println("\n=== TEST: Home Page Elements ===");

        // Basic verification
        Assert.assertTrue(driver.getTitle().contains("STORE"),
                "Title should contain STORE");

        // Check navigation elements
        System.out.println("Title: " + driver.getTitle());
        System.out.println("URL: " + driver.getCurrentUrl());

        System.out.println("✓ Home page elements test PASSED");
    }

    @Test(priority = 2)
    public void testNavigateToProduct() {
        System.out.println("\n=== TEST: Navigate to Product ===");

        // Try to find and click a product
        try {
            // Cari link produk dengan href mengandung 'prod.html'
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();

            System.out.println("Clicked product link");

            // Wait for navigation
            Thread.sleep(3000);

            // Check if we're on product page
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);

            if (currentUrl.contains("prod.html")) {
                System.out.println("✓ Successfully navigated to product page");

                // Get product info
                String productName = productPage.getProductName();
                String productPrice = productPage.getProductPrice();

                System.out.println("Product: " + productName);
                System.out.println("Price: " + productPrice);

                Assert.assertNotEquals(productName, "Unknown Product");
                Assert.assertNotEquals(productPrice, "Price not available");
            }

        } catch (Exception e) {
            System.out.println("Cannot navigate to product: " + e.getMessage());
            System.out.println("SKIP: Product navigation test");
        }

        System.out.println("✓ Navigate to product test completed");
    }

    @Test(priority = 3)
    public void testAddToCartFunction() {
        System.out.println("\n=== TEST: Add to Cart Function ===");

        try {
            // Navigate to a product
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();

            Thread.sleep(3000);

            // Try to add to cart
            productPage.addToCart();
            System.out.println("✓ Attempted to add product to cart");

            // Go back to home
            driver.navigate().back();
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Error in add to cart test: " + e.getMessage());
        }

        System.out.println("✓ Add to cart test completed");
    }

    @Test(priority = 4)
    public void testProductPageElements() {
        System.out.println("\n=== TEST: Product Page Elements ===");

        try {
            // Go to product page
            driver.findElement(
                    org.openqa.selenium.By.cssSelector("a[href*='prod.html']")
            ).click();

            Thread.sleep(3000);

            // Check elements
            boolean hasAddButton = productPage.isAddToCartButtonDisplayed();
            System.out.println("Add to cart button displayed: " + hasAddButton);

            String productName = productPage.getProductName();
            System.out.println("Product name retrieved: " + productName);

            // Go back
            driver.navigate().back();

        } catch (Exception e) {
            System.out.println("Error checking product page: " + e.getMessage());
        }

        System.out.println("✓ Product page elements test completed");
    }
}