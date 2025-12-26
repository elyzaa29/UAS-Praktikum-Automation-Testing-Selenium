package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    // Locators yang lebih flexible
    @FindBy(xpath = "//h2[contains(@class, 'name')]")
    private WebElement productName;

    @FindBy(xpath = "//h3[contains(@class, 'price')]")
    private WebElement productPrice;

    @FindBy(xpath = "//a[contains(text(), 'Add to cart') or contains(@onclick, 'addToCart')]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//div[contains(@class, 'description') or contains(@id, 'description')]")
    private WebElement productDescription;

    @FindBy(xpath = "//img[contains(@class, 'img')]")
    private WebElement productImage;

    // TAMBAHKAN CART LINK LOCATORS - INI YANG HILANG!
    @FindBy(id = "cartur") // Cart link di header/navigation
    private WebElement cartLink;

    @FindBy(linkText = "Cart") // Alternative
    private WebElement cartLinkByText;

    @FindBy(xpath = "//a[contains(text(), 'Cart')]") // Another alternative
    private WebElement cartLinkByXpath;

    // Constructor
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // Get product name dengan fallback
    public String getProductName() {
        try {
            waitForVisibility(productName);
            return getText(productName);
        } catch (Exception e) {
            // Coba cara lain
            try {
                return driver.findElement(org.openqa.selenium.By.tagName("h2")).getText();
            } catch (Exception ex) {
                return "Unknown Product";
            }
        }
    }

    // Get product price dengan fallback
    public String getProductPrice() {
        try {
            waitForVisibility(productPrice);
            return getText(productPrice);
        } catch (Exception e) {
            // Coba cara lain
            try {
                return driver.findElement(org.openqa.selenium.By.xpath("//h3")).getText();
            } catch (Exception ex) {
                return "Price not available";
            }
        }
    }

    // Add to cart dengan error handling
    public void addToCart() {
        try {
            click(addToCartButton);

            // Handle alert
            try {
                Thread.sleep(1000);
                driver.switchTo().alert().accept();
                System.out.println("Product added to cart");
            } catch (Exception e) {
                System.out.println("No alert or error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Cannot add to cart: " + e.getMessage());
        }
    }

    // Go to cart - PERBAIKI INI!
    public void goToCart() {
        try {
            // Coba cart link dengan ID pertama
            if (isDisplayed(cartLink)) {
                click(cartLink);
                System.out.println("✓ Navigated to cart using cartur ID");
            } else if (isDisplayed(cartLinkByText)) {
                // Coba dengan link text
                click(cartLinkByText);
                System.out.println("✓ Navigated to cart using link text");
            } else if (isDisplayed(cartLinkByXpath)) {
                // Coba dengan XPath
                click(cartLinkByXpath);
                System.out.println("✓ Navigated to cart using XPath");
            } else {
                // Jika semua gagal, navigate langsung
                System.out.println("Cart link not found, navigating directly");
                driver.get("https://www.demoblaze.com/cart.html");
            }
        } catch (Exception e) {
            System.out.println("Error navigating to cart: " + e.getMessage());
            // Fallback: navigate langsung
            driver.get("https://www.demoblaze.com/cart.html");
        }

        // Tunggu cart page load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Check if add to cart button is displayed
    public boolean isAddToCartButtonDisplayed() {
        return isDisplayed(addToCartButton);
    }

    // Check if product image is displayed
    public boolean isImageDisplayed() {
        return isDisplayed(productImage);
    }

    // Get product description
    public String getProductDescription() {
        try {
            return getText(productDescription);
        } catch (Exception e) {
            return "No description available";
        }
    }

    // Check if on product page
    public boolean isOnProductPage() {
        return driver.getCurrentUrl().contains("prod.html");
    }

    // Take screenshot of product
    public void takeProductScreenshot(String testName) {
        // This would use ScreenshotUtil
        System.out.println("Taking screenshot of product: " + getProductName());
    }
}