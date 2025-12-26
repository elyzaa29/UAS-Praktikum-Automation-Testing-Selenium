package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginModal extends BasePage {

    // Locators untuk login modal
    @FindBy(id = "loginusername")
    private WebElement usernameInput;

    @FindBy(id = "loginpassword")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement loginButton;

    @FindBy(xpath = "//button[text()='Close']")
    private WebElement closeButton;

    @FindBy(xpath = "//div[@id='logInModal']//button[@class='btn btn-secondary']")
    private WebElement closeButton2; // Alternatif

    @FindBy(id = "logInModalLabel")
    private WebElement modalTitle;

    // Constructor
    public LoginModal(WebDriver driver) {
        super(driver);
    }

    // Wait for modal to appear
    public void waitForModal() {
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
    }

    // Enter username
    public void enterUsername(String username) {
        enterText(usernameInput, username);
    }

    // Enter password
    public void enterPassword(String password) {
        enterText(passwordInput, password);
    }

    // Click login button
    public void clickLogin() {
        click(loginButton);
    }

    // Di LoginModal.java
    public void clickClose() {
        try {
            // Coba close button pertama
            click(closeButton);
        } catch (Exception e) {
            try {
                // Coba close button alternatif
                click(closeButton2);
            } catch (Exception e2) {
                // Coba dengan XPath
                driver.findElement(By.xpath("//button[text()='Close']")).click();
            }
        }

        // Tunggu modal tertutup
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Get modal title
    public String getModalTitle() {
        return getText(modalTitle);
    }

    // Complete login flow
    public void login(String username, String password) {
        waitForModal();
        enterUsername(username);
        enterPassword(password);
        clickLogin();

        // Wait for modal to close or alert to appear
        try {
            Thread.sleep(1000); // Wait a bit
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Check if modal is displayed
    public boolean isModalDisplayed() {
        return isDisplayed(usernameInput);
    }

    // Clear form
    public void clearForm() {
        usernameInput.clear();
        passwordInput.clear();
    }
}