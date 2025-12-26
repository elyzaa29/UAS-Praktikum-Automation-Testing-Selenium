package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    // Cart page locators
    @FindBy(xpath = "//button[text()='Place Order']")
    private WebElement placeOrderButton;

    @FindBy(xpath = "//td[text()='Total']/following-sibling::td")
    private WebElement totalAmount;

    @FindBy(linkText = "Delete")
    private List<WebElement> deleteLinks;

    @FindBy(xpath = "//button[text()='Home']")
    private WebElement homeButton;

    @FindBy(tagName = "h2")
    private WebElement pageHeader;

    // Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Navigate to cart
    public void navigateToCart() {
        driver.get("https://www.demoblaze.com/cart.html");
        wait.until(ExpectedConditions.visibilityOf(pageHeader));
    }

    // Check if on cart page
    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart.html");
    }

    // Get page header
    public String getPageHeader() {
        try {
            return getText(pageHeader);
        } catch (Exception e) {
            return "";
        }
    }

    // Check if cart is empty
    public boolean isCartEmpty() {
        try {
            String header = getPageHeader().toLowerCase();
            return header.contains("empty") || header.contains("no products");
        } catch (Exception e) {
            return true;
        }
    }

    // Click place order
    public void clickPlaceOrder() {
        try {
            click(placeOrderButton);
        } catch (Exception e) {
            System.out.println("Cannot click place order: " + e.getMessage());
        }
    }

    // Go to home
    public void goToHome() {
        try {
            click(homeButton);
        } catch (Exception e) {
            driver.get("https://www.demoblaze.com/");
        }
    }

    // Print cart status
    public void printCartStatus() {
        System.out.println("=== CART STATUS ===");
        System.out.println("On cart page: " + isOnCartPage());
        System.out.println("Page header: " + getPageHeader());
        System.out.println("Cart empty: " + isCartEmpty());
        System.out.println("URL: " + driver.getCurrentUrl());
    }
}