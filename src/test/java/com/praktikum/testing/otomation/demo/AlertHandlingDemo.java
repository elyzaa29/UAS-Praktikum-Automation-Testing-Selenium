package com.praktikum.testing.otomation.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Alert Handling Demo untuk website Demoblaze.com
 * URL: https://www.demoblaze.com/
 * Test berbagai skenario alert/popup yang muncul di Demoblaze
 */
public class AlertHandlingDemo {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Demoblaze kadang lambat, jadi timeout lebih panjang
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void demonstrateAddToCartAlert() {
        System.out.println("\n=== TEST: Add to Cart Alert di Demoblaze ===");

        // 1. Navigasi ke homepage Demoblaze
        driver.get("https://www.demoblaze.com/");
        System.out.println("  Navigated to Demoblaze homepage");

        // 2. Tunggu homepage fully loaded
        wait.until(ExpectedConditions.titleContains("STORE"));
        System.out.println("  Homepage fully loaded");

        // 3. Pilih produk pertama (Samsung galaxy s6)
        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s6"))
        );
        firstProduct.click();
        System.out.println("  Clicked on product: Samsung galaxy s6");

        // 4. Tunggu halaman detail produk
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2.name")
        ));
        System.out.println("  Product detail page loaded");

        // 5. Klik Add to Cart
        WebElement addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))
        );
        addToCartButton.click();
        System.out.println("  Clicked Add to Cart button");

        // 6. Tunggu alert muncul dan ambil textnya
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println("  Alert appeared with text: " + alertText);

        // 7. Verifikasi alert text
        Assert.assertTrue(alertText.contains("Product added"),
                "Alert should contain 'Product added' message");

        // 8. Accept alert (klik OK)
        alert.accept();
        System.out.println("  Alert accepted (clicked OK)");

        System.out.println("\n  Add to Cart Alert test PASSED");
    }

    @Test(priority = 2)
    public void demonstrateLoginAlert() {
        System.out.println("\n=== TEST: Login Alert dengan Invalid Credentials ===");

        // 1. Navigasi ke Demoblaze
        driver.get("https://www.demoblaze.com/");

        // 2. Klik Login button di navbar
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login2"))
        );
        loginButton.click();
        System.out.println("  Clicked Login button");

        // 3. Tunggu modal login muncul
        WebElement loginModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("logInModal"))
        );
        System.out.println("  Login modal appeared");

        // 4. Isi username invalid
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))
        );
        usernameField.clear();
        usernameField.sendKeys("invalid_user_12345");
        System.out.println("  Entered invalid username");

        // 5. Isi password invalid
        WebElement passwordField = driver.findElement(By.id("loginpassword"));
        passwordField.clear();
        passwordField.sendKeys("wrongpassword");
        System.out.println("  Entered wrong password");

        // 6. Klik Login button di modal
        WebElement modalLoginBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Log in')]")
        );
        modalLoginBtn.click();
        System.out.println("  Clicked Log in button in modal");

        // 7. Tunggu alert error muncul
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println("  Alert appeared with text: " + alertText);

        // 8. Verifikasi error message
        Assert.assertTrue(alertText.contains("User does not exist"),
                "Alert should contain 'User does not exist' for invalid login");

        // 9. Accept alert
        alert.accept();
        System.out.println("  Alert accepted");

        System.out.println("\n  Invalid Login Alert test PASSED");
    }

    @Test(priority = 3)
    public void demonstratePlaceOrderAlert() {
        System.out.println("\n=== TEST: Place Order Process dengan Alert ===");

        // 1. Login terlebih dahulu dengan user valid
        loginWithValidCredentials();

        // 2. Tambah produk ke cart
        addProductToCart();

        // 3. Navigasi ke Cart page
        WebElement cartLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Cart"))
        );
        cartLink.click();
        System.out.println("  Navigated to Cart page");

        // 4. Tunggu cart page loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(),'Place Order')]")
        ));

        // 5. Klik Place Order
        WebElement placeOrderBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Place Order')]")
        );
        placeOrderBtn.click();
        System.out.println("  Clicked Place Order button");

        // 6. Tunggu order modal muncul
        WebElement orderModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("orderModal"))
        );
        System.out.println("  Place Order modal appeared");

        // 7. Isi form order (required fields saja)
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("Test Customer");

        WebElement countryField = driver.findElement(By.id("country"));
        countryField.sendKeys("Indonesia");

        WebElement cityField = driver.findElement(By.id("city"));
        cityField.sendKeys("Jakarta");

        WebElement cardField = driver.findElement(By.id("card"));
        cardField.sendKeys("1234567890123456");

        WebElement monthField = driver.findElement(By.id("month"));
        monthField.sendKeys("12");

        WebElement yearField = driver.findElement(By.id("year"));
        yearField.sendKeys("2025");

        System.out.println("  Filled order form");

        // 8. Klik Purchase
        WebElement purchaseBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Purchase')]")
        );
        purchaseBtn.click();
        System.out.println("  Clicked Purchase button");

        // 9. Tunggu konfirmasi success muncul
        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(text(),'Thank you for your purchase!')]")
                )
        );
        String successText = successMessage.getText();
        System.out.println("  Success message: " + successText);

        // 10. Klik OK pada konfirmasi
        WebElement okButton = driver.findElement(
                By.xpath("//button[contains(text(),'OK')]")
        );
        okButton.click();
        System.out.println("  Clicked OK button");

        System.out.println("\n  Place Order Alert test PASSED");
    }

    @Test(priority = 4)
    public void demonstrateSignUpAlert() {
        System.out.println("\n=== TEST: Sign Up Alert ===");

        driver.get("https://www.demoblaze.com/");

        // 1. Klik Sign Up button
        WebElement signUpButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("signin2"))
        );
        signUpButton.click();
        System.out.println("  Clicked Sign Up button");

        // 2. Tunggu modal sign up muncul
        WebElement signUpModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("signInModal"))
        );
        System.out.println("  Sign Up modal appeared");

        // 3. Isi form dengan random username (agar tidak duplicate)
        String randomUsername = "testuser_" + System.currentTimeMillis();

        WebElement signupUsername = driver.findElement(By.id("sign-username"));
        signupUsername.clear();
        signupUsername.sendKeys(randomUsername);
        System.out.println("  Entered username: " + randomUsername);

        WebElement signupPassword = driver.findElement(By.id("sign-password"));
        signupPassword.clear();
        signupPassword.sendKeys("Test@123");
        System.out.println("  Entered password");

        // 4. Klik Sign Up button di modal
        WebElement modalSignUpBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Sign up')]")
        );
        modalSignUpBtn.click();
        System.out.println("  Clicked Sign Up button in modal");

        // 5. Tunggu alert muncul
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println("  Alert appeared with text: " + alertText);

        // 6. Verifikasi alert (bisa success atau user exists)
        Assert.assertTrue(
                alertText.contains("Sign up successful") ||
                        alertText.contains("This user already exist"),
                "Alert should be about signup result"
        );

        // 7. Accept alert
        alert.accept();
        System.out.println("  Alert accepted");

        System.out.println("\n  Sign Up Alert test PASSED");
    }

    @Test(priority = 5)
    public void demonstrateDeleteFromCartAlert() {
        System.out.println("\n=== TEST: Delete Item from Cart (tanpa alert) ===");

        // 1. Login terlebih dahulu
        loginWithValidCredentials();

        // 2. Tambah produk ke cart
        addProductToCart();

        // 3. Buka cart page
        WebElement cartLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Cart"))
        );
        cartLink.click();
        System.out.println("  Navigated to Cart page");

        // 4. Tunggu cart items muncul
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr[@class='success']")
        ));

        // 5. Klik Delete link (tidak ada alert di Demoblaze untuk delete)
        WebElement deleteLink = driver.findElement(By.linkText("Delete"));
        deleteLink.click();
        System.out.println("  Clicked Delete link");

        // 6. Tunggu item hilang dari cart (tanpa alert)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//tr[@class='success']")
        ));

        // 7. Verifikasi cart kosong atau berkurang
        System.out.println("  Item deleted from cart (no alert)");

        System.out.println("\n  Delete from Cart test PASSED");
    }

    // ===== HELPER METHODS =====

    private void loginWithValidCredentials() {
        System.out.println("  [HELPER] Logging in with valid credentials...");

        driver.get("https://www.demoblaze.com/");

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login2"))
        );
        loginButton.click();

        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))
        );
        usernameField.clear();
        usernameField.sendKeys("testuser123");

        WebElement passwordField = driver.findElement(By.id("loginpassword"));
        passwordField.clear();
        passwordField.sendKeys("test123");

        WebElement modalLoginBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Log in')]")
        );
        modalLoginBtn.click();

        // Tunggu login success (nama user muncul di navbar)
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("nameofuser"), "Welcome"
        ));

        System.out.println("  [HELPER] Login successful");
    }

    private void addProductToCart() {
        System.out.println("  [HELPER] Adding product to cart...");

        // Navigasi ke produk dan add to cart
        driver.get("https://www.demoblaze.com/prod.html?idp_=1");

        WebElement addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))
        );
        addToCartButton.click();

        // Handle alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        System.out.println("  [HELPER] Product added to cart");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                // Tunggu sebentar sebelum close untuk melihat hasil
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("  Browser closed");
        }
    }
}