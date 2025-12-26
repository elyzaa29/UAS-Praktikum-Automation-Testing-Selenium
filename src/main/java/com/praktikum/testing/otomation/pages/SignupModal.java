package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignupModal extends BasePage {

    // Locators untuk signup modal
    @FindBy(id = "sign-username")
    private WebElement usernameInput;

    @FindBy(id = "sign-password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Sign up']")
    private WebElement signupButton;

    @FindBy(xpath = "//div[@id='signInModal']//button[text()='Close']")
    private WebElement closeButton;

    @FindBy(id = "signInModalLabel")
    private WebElement modalTitle;

    // Constructor
    public SignupModal(WebDriver driver) {
        super(driver);
    }

    // Wait for modal to appear
    public void waitForModal() {
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
    }

    // Enter username for signup
    public void enterUsername(String username) {
        enterText(usernameInput, username);
    }

    // Enter password for signup
    public void enterPassword(String password) {
        enterText(passwordInput, password);
    }

    // Click signup button
    public void clickSignup() {
        click(signupButton);
    }

    // Click close button
    public void clickClose() {
        click(closeButton);
    }

    // Get modal title
    public String getModalTitle() {
        return getText(modalTitle);
    }

    // Complete signup flow
    public void signup(String username, String password) {
        waitForModal();
        enterUsername(username);
        enterPassword(password);
        clickSignup();

        // Handle alert if present
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Check if modal is displayed
    public boolean isModalDisplayed() {
        try {
            return usernameInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Clear form
    public void clearForm() {
        usernameInput.clear();
        passwordInput.clear();
    }
}