package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CheckoutModal extends BasePage {

    // Locators untuk checkout modal (Place Order)
    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "country")
    private WebElement countryInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "card")
    private WebElement creditCardInput;

    @FindBy(id = "month")
    private WebElement monthInput;

    @FindBy(id = "year")
    private WebElement yearInput;

    @FindBy(xpath = "//button[text()='Purchase']")
    private WebElement purchaseButton;

    @FindBy(xpath = "//button[text()='Close']")
    private WebElement closeButton;

    @FindBy(className = "sweet-alert")
    private WebElement successModal;

    @FindBy(xpath = "//p[@class='lead text-muted ']")
    private WebElement orderDetails;

    @FindBy(xpath = "//button[text()='OK']")
    private WebElement okButton;

    // Constructor
    public CheckoutModal(WebDriver driver) {
        super(driver);
    }

    // Wait for checkout modal to appear
    public void waitForModal() {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        System.out.println("Checkout modal is displayed");
    }

    // Fill checkout form
    public void fillCheckoutForm(String name, String country, String city,
                                 String creditCard, String month, String year) {
        System.out.println("Filling checkout form...");

        enterText(nameInput, name);
        enterText(countryInput, country);
        enterText(cityInput, city);
        enterText(creditCardInput, creditCard);
        enterText(monthInput, month);
        enterText(yearInput, year);

        System.out.println("✓ Checkout form filled");
    }

    // Click purchase button
    public void clickPurchase() {
        click(purchaseButton);
        System.out.println("Clicked Purchase button");
    }

    // Click close button
    public void clickClose() {
        click(closeButton);
        System.out.println("Clicked Close button");
    }

    // Click OK button on success modal
    public void clickOk() {
        try {
            click(okButton);
            System.out.println("Clicked OK button");
        } catch (Exception e) {
            System.out.println("OK button not found: " + e.getMessage());
        }
    }

    // Complete checkout process
    public void completeCheckout(String name, String country, String city,
                                 String creditCard, String month, String year) {
        waitForModal();
        fillCheckoutForm(name, country, city, creditCard, month, year);
        clickPurchase();

        // Wait for success modal
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Check if success modal is displayed
    public boolean isSuccessModalDisplayed() {
        try {
            return successModal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Get success message
    public String getSuccessMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successModal));
            return successModal.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Get order details (ID, amount, etc.)
    public String getOrderDetails() {
        try {
            return getText(orderDetails);
        } catch (Exception e) {
            return "";
        }
    }

    // Extract order ID from details
    public String extractOrderId() {
        String details = getOrderDetails();
        if (details.contains("Id:")) {
            String[] parts = details.split("Id:");
            if (parts.length > 1) {
                return parts[1].split("\n")[0].trim();
            }
        }
        return "No ID found";
    }

    // Extract amount from details
    public String extractAmount() {
        String details = getOrderDetails();
        if (details.contains("Amount:")) {
            String[] parts = details.split("Amount:");
            if (parts.length > 1) {
                return parts[1].split("USD")[0].trim();
            }
        }
        return "No amount found";
    }

    // Verify checkout was successful
    public boolean isCheckoutSuccessful() {
        try {
            String message = getSuccessMessage();
            return message.contains("Thank you") ||
                    message.contains("success") ||
                    message.contains("purchase");
        } catch (Exception e) {
            return false;
        }
    }

    // Print order summary
    public void printOrderSummary() {
        System.out.println("=== ORDER SUMMARY ===");
        System.out.println("Success: " + isCheckoutSuccessful());
        System.out.println("Message: " + getSuccessMessage());
        System.out.println("Details: " + getOrderDetails());
        System.out.println("Order ID: " + extractOrderId());
        System.out.println("Amount: " + extractAmount() + " USD");
    }

    // Clear form
    public void clearForm() {
        nameInput.clear();
        countryInput.clear();
        cityInput.clear();
        creditCardInput.clear();
        monthInput.clear();
        yearInput.clear();
    }

    // Check if modal is displayed
    public boolean isModalDisplayed() {
        try {
            return nameInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}