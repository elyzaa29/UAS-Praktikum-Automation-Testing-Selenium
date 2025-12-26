package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {

    // Locators berdasarkan data aktual
    @FindBy(id = "login2")
    private WebElement loginLink;

    @FindBy(id = "signin2")
    private WebElement signupLink;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    @FindBy(id = "nameofuser")
    private WebElement welcomeUser;

    // Categories mungkin tidak sebagai link terpisah
    // Mari kita cari dengan text
    @FindBy(xpath = "//a[contains(text(), 'Phones')]")
    private WebElement phonesCategory;

    @FindBy(xpath = "//a[contains(text(), 'Laptops')]")
    private WebElement laptopsCategory;

    @FindBy(xpath = "//a[contains(text(), 'Monitors')]")
    private WebElement monitorsCategory;

    // Product elements
    @FindBy(className = "card-title")
    private List<WebElement> productTitles;

    @FindBy(className = "card")
    private List<WebElement> productCards;

    @FindBy(xpath = "//a[contains(text(), 'Samsung')]")
    private List<WebElement> samsungProducts;

    @FindBy(xpath = "//a[contains(text(), 'Nokia')]")
    private List<WebElement> nokiaProducts;

    @FindBy(id = "logout2")
    private WebElement logoutLink;

    @FindBy(id = "next2")
    private WebElement nextButton;

    @FindBy(id = "prev2")
    private WebElement prevButton;

    // Navigation
    @FindBy(id = "nava")
    private WebElement homeLogo;

    @FindBy(linkText = "Contact")
    private WebElement contactLink;

    @FindBy(linkText = "About us")
    private WebElement aboutLink;

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Navigation methods
    public void navigateToHome() {
        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.visibilityOf(homeLogo));
    }

    // Click methods
    public void clickLogin() {
        click(loginLink);
        // Wait for modal - cari dengan ID modal
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.id("logInModal")));
    }

    public void clickSignup() {
        try {
            click(signupLink);
            // Wait for modal
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error clicking signup: " + e.getMessage());
        }
    }

    public void goToCart() {
        click(cartLink);
    }

    public void goToContact() {
        click(contactLink);
    }

    public void goToAbout() {
        click(aboutLink);
    }

    // Category methods - berdasarkan data, categories mungkin section
    public void selectPhonesCategory() {
        try {
            click(phonesCategory);
        } catch (Exception e) {
            // Jika tidak ditemukan sebagai link, scroll ke section
            System.out.println("Phones category not found as link");
        }
    }

    public void selectLaptopsCategory() {
        try {
            click(laptopsCategory);
        } catch (Exception e) {
            System.out.println("Laptops category not found as link");
        }
    }

    public void selectMonitorsCategory() {
        try {
            click(monitorsCategory);
        } catch (Exception e) {
            System.out.println("Monitors category not found as link");
        }
    }

    // Product methods
    public void selectProduct(int index) {
        if (index >= 0 && index < productCards.size()) {
            click(productCards.get(index));
        }
    }

    public void selectProductByName(String name) {
        // Cari product dengan nama tertentu
        for (WebElement product : productTitles) {
            if (product.getText().contains(name)) {
                click(product);
                break;
            }
        }
    }

    public String getProductTitle(int index) {
        if (index >= 0 && index < productTitles.size()) {
            return getText(productTitles.get(index));
        }
        return "";
    }

    public int getProductCount() {
        return productCards.size();
    }

    public List<String> getAllProductTitles() {
        return productTitles.stream()
                .map(WebElement::getText)
                .toList();
    }

    // User status methods
    public boolean isUserLoggedIn() {
        try {
            // Cek apakah welcome user muncul
            wait.until(ExpectedConditions.visibilityOf(welcomeUser));
            String text = welcomeUser.getText();
            return text.contains("Welcome");
        } catch (Exception e) {
            return false;
        }
    }

    public String getWelcomeMessage() {
        try {
            return getText(welcomeUser);
        } catch (Exception e) {
            return "";
        }
    }

    public void logout() {
        if (isUserLoggedIn()) {
            click(logoutLink);
            wait.until(ExpectedConditions.visibilityOf(loginLink));
        }
    }

    // Pagination
    public void clickNext() {
        if (isDisplayed(nextButton)) {
            click(nextButton);
            wait.until(ExpectedConditions.visibilityOfAllElements(productCards));
        }
    }

    public void clickPrevious() {
        if (isDisplayed(prevButton)) {
            click(prevButton);
            wait.until(ExpectedConditions.visibilityOfAllElements(productCards));
        }
    }

    // Verification methods
    public boolean isOnHomePage() {
        return driver.getCurrentUrl().equals("https://www.demoblaze.com/");
    }

    public boolean isLoginLinkDisplayed() {
        return isDisplayed(loginLink);
    }

    public boolean isSignupLinkDisplayed() {
        return isDisplayed(signupLink);
    }

    public boolean isCartLinkDisplayed() {
        return isDisplayed(cartLink);
    }

    // Product search
    public boolean hasSamsungProducts() {
        return !samsungProducts.isEmpty();
    }

    public boolean hasNokiaProducts() {
        return !nokiaProducts.isEmpty();
    }

    // Get page info
    public void printPageInfo() {
        System.out.println("=== PAGE INFO ===");
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("Title: " + driver.getTitle());
        System.out.println("Products found: " + getProductCount());
        System.out.println("User logged in: " + isUserLoggedIn());
        if (isUserLoggedIn()) {
            System.out.println("Welcome message: " + getWelcomeMessage());
        }
    }

    // Di HomePage.java, tambahkan method untuk cek modal
    public boolean isLoginModalOpen() {
        try {
            // Cari element modal
            WebElement modal = driver.findElement(By.id("logInModal"));
            return modal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}