package com.praktikum.testing.otomation.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
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
 * Form Interaction Demo untuk website Demoblaze.com
 * URL: https://www.demoblaze.com/
 * Test berbagai skenario form interaction: login, signup, contact, cart, checkout
 */
public class FormInteractionDemo {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        System.out.println("\n=== TEST: SUCCESSFUL LOGIN FORM ===");

        // 1. Navigasi ke Demoblaze
        driver.get("https://www.demoblaze.com/");
        System.out.println("  Navigated to Demoblaze homepage");

        // 2. Klik Login button
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

        // 4. Isi username field
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))
        );
        usernameField.clear();
        usernameField.sendKeys("testuser123");
        System.out.println("  Entered username: testuser123");

        // 5. Isi password field
        WebElement passwordField = driver.findElement(By.id("loginpassword"));
        passwordField.clear();
        passwordField.sendKeys("test123");
        System.out.println("  Entered password");

        // 6. Klik Login button di modal
        WebElement modalLoginBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Log in')]")
        );
        modalLoginBtn.click();
        System.out.println("  Clicked Log in button in modal");

        // 7. Tunggu login success (nama user muncul di navbar)
        WebElement welcomeMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser"))
        );
        String welcomeText = welcomeMessage.getText();
        System.out.println("  Welcome message: " + welcomeText);

        // 8. Verifikasi login berhasil
        Assert.assertTrue(welcomeText.contains("Welcome"),
                "Should show welcome message after successful login");
        Assert.assertTrue(driver.findElement(By.id("logout2")).isDisplayed(),
                "Logout button should be visible");

        System.out.println("\n  Login test PASSED - User successfully logged in");
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        System.out.println("\n=== TEST: INVALID LOGIN FORM ===");

        driver.get("https://www.demoblaze.com/");

        // Klik Login button
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login2"))
        );
        loginButton.click();

        // Tunggu modal
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));

        // Isi credentials invalid
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))
        );
        usernameField.clear();
        usernameField.sendKeys("invalid_user_xyz");

        WebElement passwordField = driver.findElement(By.id("loginpassword"));
        passwordField.clear();
        passwordField.sendKeys("wrongpassword123");
        System.out.println("  Entered invalid credentials");

        // Klik Login
        WebElement modalLoginBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Log in')]")
        );
        modalLoginBtn.click();

        // Tunggu alert error muncul
        wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("  Alert appeared for invalid login");

        // Verify we're still on same page (login failed)
        Assert.assertTrue(driver.findElement(By.id("login2")).isDisplayed(),
                "Login button should still be visible (login failed)");

        System.out.println("\n  Invalid login test PASSED");
    }

    @Test(priority = 3)
    public void testSignUpForm() {
        System.out.println("\n=== TEST: SIGN UP FORM ===");

        driver.get("https://www.demoblaze.com/");

        // Klik Sign Up button
        WebElement signUpButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("signin2"))
        );
        signUpButton.click();

        // Tunggu modal sign up
        WebElement signUpModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("signInModal"))
        );
        System.out.println("  Sign Up modal appeared");

        // Generate unique username
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomUsername = "newuser_" + timestamp.substring(timestamp.length() - 6);

        // Isi form sign up
        WebElement signupUsername = driver.findElement(By.id("sign-username"));
        signupUsername.clear();
        signupUsername.sendKeys(randomUsername);
        System.out.println("  Entered username: " + randomUsername);

        WebElement signupPassword = driver.findElement(By.id("sign-password"));
        signupPassword.clear();
        signupPassword.sendKeys("Password@123");
        System.out.println("  Entered password");

        // Klik Sign Up button
        WebElement modalSignUpBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Sign up')]")
        );
        modalSignUpBtn.click();
        System.out.println("  Clicked Sign Up button");

        // Tunggu alert response
        wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("  Alert appeared for sign up");

        // Accept alert
        driver.switchTo().alert().accept();

        // Verifikasi modal tertutup
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("signInModal")));
        System.out.println("  Sign Up modal closed");

        System.out.println("\n  Sign Up form test PASSED");
    }

    @Test(priority = 4)
    public void testContactForm() {
        System.out.println("\n=== TEST: CONTACT FORM ===");

        driver.get("https://www.demoblaze.com/");

        // Klik Contact menu
        WebElement contactLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Contact"))
        );
        contactLink.click();
        System.out.println("  Clicked Contact link");

        // Tunggu modal contact muncul
        WebElement contactModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal"))
        );
        System.out.println("  Contact modal appeared");

        // Isi form contact
        WebElement contactEmail = driver.findElement(By.id("recipient-email"));
        contactEmail.clear();
        contactEmail.sendKeys("test@example.com");
        System.out.println("  Entered email: test@example.com");

        WebElement contactName = driver.findElement(By.id("recipient-name"));
        contactName.clear();
        contactName.sendKeys("John Doe");
        System.out.println("  Entered name: John Doe");

        WebElement messageField = driver.findElement(By.id("message-text"));
        messageField.clear();
        messageField.sendKeys("This is a test message for contact form.");
        System.out.println("  Entered message");

        // Klik Send Message button
        WebElement sendMessageBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Send message')]")
        );
        sendMessageBtn.click();
        System.out.println("  Clicked Send message button");

        // Tunggu alert konfirmasi
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        System.out.println("  Alert text: " + alertText);

        // Verifikasi alert
        Assert.assertTrue(alertText.contains("Thanks"),
                "Alert should contain thanks message");

        // Accept alert
        driver.switchTo().alert().accept();
        System.out.println("  Alert accepted");

        // Verifikasi modal tertutup
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("exampleModal")));

        System.out.println("\n  Contact form test PASSED");
    }

    @Test(priority = 5)
    public void testAboutUsForm() {
        System.out.println("\n=== TEST: ABOUT US VIDEO PLAYER ===");

        driver.get("https://www.demoblaze.com/");

        // Klik About Us menu
        WebElement aboutLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("About us"))
        );
        aboutLink.click();
        System.out.println("  Clicked About Us link");

        // Tunggu modal about us muncul
        WebElement aboutModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("videoModal"))
        );
        System.out.println("  About Us modal appeared");

        // Verifikasi video player ada
        WebElement videoPlayer = driver.findElement(
                By.xpath("//video[@id='example-video']")
        );
        Assert.assertTrue(videoPlayer.isDisplayed(),
                "Video player should be displayed");
        System.out.println("  Video player is displayed");

        // Klik Play button (jika ada)
        try {
            WebElement playButton = driver.findElement(
                    By.cssSelector("button.vjs-big-play-button")
            );
            if (playButton.isDisplayed()) {
                playButton.click();
                System.out.println("  Clicked Play button on video");
                Thread.sleep(2000); // Tunggu video mulai
            }
        } catch (Exception e) {
            System.out.println("  Play button not found or not clickable");
        }

        // Klik Close button
        WebElement closeButton = driver.findElement(
                By.xpath("//button[contains(text(),'Close')]")
        );
        closeButton.click();
        System.out.println("  Clicked Close button");

        // Tunggu modal tertutup
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("videoModal")));
        System.out.println("  About Us modal closed");

        System.out.println("\n  About Us form test PASSED");
    }

    @Test(priority = 6)
    public void testProductPurchaseFlow() {
        System.out.println("\n=== TEST: COMPLETE PRODUCT PURCHASE FLOW ===");

        // Step 1: Login
        testSuccessfulLogin();

        // Step 2: Navigasi ke kategori
        WebElement phonesCategory = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Phones"))
        );
        phonesCategory.click();
        System.out.println("\n  Selected Phones category");

        // Tunggu produk muncul
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".card-title a")
        ));

        // Step 3: Pilih produk pertama
        WebElement firstProduct = driver.findElement(
                By.cssSelector(".card-title a")
        );
        String productName = firstProduct.getText();
        firstProduct.click();
        System.out.println("  Selected product: " + productName);

        // Tunggu halaman detail
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2.name")
        ));

        // Step 4: Add to cart
        WebElement addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))
        );
        addToCartButton.click();
        System.out.println("  Clicked Add to cart");

        // Handle alert
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        System.out.println("  Accepted 'Product added' alert");

        // Step 5: Buka cart
        WebElement cartLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Cart"))
        );
        cartLink.click();
        System.out.println("  Navigated to Cart page");

        // Tunggu cart page
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'Products')]")
        ));

        // Verifikasi produk ada di cart
        WebElement cartProduct = driver.findElement(
                By.xpath("//td[contains(text(),'" + productName + "')]")
        );
        Assert.assertTrue(cartProduct.isDisplayed(),
                "Product should be in cart");
        System.out.println("  Verified product in cart: " + productName);

        // Step 6: Place Order
        WebElement placeOrderBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Place Order')]")
        );
        placeOrderBtn.click();
        System.out.println("  Clicked Place Order button");

        // Tunggu order modal
        WebElement orderModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("orderModal"))
        );
        System.out.println("  Order modal appeared");

        // Step 7: Isi form order
        fillOrderForm();

        // Step 8: Klik Purchase
        WebElement purchaseBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Purchase')]")
        );
        purchaseBtn.click();
        System.out.println("  Clicked Purchase button");

        // Tunggu konfirmasi
        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(text(),'Thank you for your purchase!')]")
                )
        );
        String successText = successMessage.getText();
        System.out.println("  Success message: " + successText);

        // Ambil order details
        WebElement orderDetails = driver.findElement(
                By.cssSelector("p.lead.text-muted")
        );
        System.out.println("  Order details: " + orderDetails.getText());

        // Step 9: Klik OK
        WebElement okButton = driver.findElement(
                By.xpath("//button[contains(text(),'OK')]")
        );
        okButton.click();
        System.out.println("  Clicked OK button");

        // Verifikasi kembali ke homepage
        wait.until(ExpectedConditions.titleContains("STORE"));
        System.out.println("  Returned to homepage");

        System.out.println("\n  Complete purchase flow test PASSED");
    }

    @Test(priority = 7)
    public void testEmptyFormSubmission() {
        System.out.println("\n=== TEST: EMPTY FORM SUBMISSION ===");

        // Test 1: Empty login form
        System.out.println("\n  Sub-test: Empty Login Form");
        driver.get("https://www.demoblaze.com/");

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login2"))
        );
        loginButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));

        // Klik login tanpa isi apa-apa
        WebElement modalLoginBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Log in')]")
        );
        modalLoginBtn.click();

        // Tunggu alert
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        System.out.println("    Alert for empty login: " + alertText);
        driver.switchTo().alert().accept();

        // Test 2: Empty sign up form
        System.out.println("\n  Sub-test: Empty Sign Up Form");
        driver.navigate().refresh();

        WebElement signUpButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("signin2"))
        );
        signUpButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModal")));

        // Klik sign up tanpa isi apa-apa
        WebElement modalSignUpBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Sign up')]")
        );
        modalSignUpBtn.click();

        wait.until(ExpectedConditions.alertIsPresent());
        alertText = driver.switchTo().alert().getText();
        System.out.println("    Alert for empty signup: " + alertText);
        driver.switchTo().alert().accept();

        System.out.println("\n  Empty form submission test PASSED");
    }

    @Test(priority = 8)
    public void testFormFieldValidation() {
        System.out.println("\n=== TEST: FORM FIELD VALIDATION ===");

        driver.get("https://www.demoblaze.com/");

        // Buka contact form
        WebElement contactLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Contact"))
        );
        contactLink.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal")));

        // Test 1: Email format validation
        System.out.println("\n  Test 1: Invalid email format");
        WebElement contactEmail = driver.findElement(By.id("recipient-email"));
        contactEmail.clear();
        contactEmail.sendKeys("invalid-email"); // Email tidak valid

        WebElement messageField = driver.findElement(By.id("message-text"));
        messageField.clear();
        messageField.sendKeys("Test message");

        WebElement sendMessageBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Send message')]")
        );
        sendMessageBtn.click();

        // Meski email invalid, Demoblaze tetap menerima
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        System.out.println("    Alert: " + alertText);
        driver.switchTo().alert().accept();

        // Test 2: Long input text
        System.out.println("\n  Test 2: Long text input");
        contactLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Contact"))
        );
        contactLink.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal")));

        messageField = driver.findElement(By.id("message-text"));
        messageField.clear();

        // Generate long message
        String longMessage = "A".repeat(500);
        messageField.sendKeys(longMessage);
        System.out.println("    Entered 500-character message");

        // Test masih bisa dikirim
        contactEmail = driver.findElement(By.id("recipient-email"));
        contactEmail.clear();
        contactEmail.sendKeys("test@example.com");

        sendMessageBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Send message')]")
        );
        sendMessageBtn.click();

        wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("    Form accepted long message");
        driver.switchTo().alert().accept();

        System.out.println("\n  Form field validation test PASSED");
    }

    // ===== HELPER METHOD =====

    private void fillOrderForm() {
        System.out.println("    Filling order form...");

        WebElement nameField = driver.findElement(By.id("name"));
        nameField.clear();
        nameField.sendKeys("John Customer");

        WebElement countryField = driver.findElement(By.id("country"));
        countryField.clear();
        countryField.sendKeys("United States");

        WebElement cityField = driver.findElement(By.id("city"));
        cityField.clear();
        cityField.sendKeys("New York");

        WebElement cardField = driver.findElement(By.id("card"));
        cardField.clear();
        cardField.sendKeys("4111111111111111");

        WebElement monthField = driver.findElement(By.id("month"));
        monthField.clear();
        monthField.sendKeys("12");

        WebElement yearField = driver.findElement(By.id("year"));
        yearField.clear();
        yearField.sendKeys("2025");

        System.out.println("    Order form filled");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("  Browser closed");
        }
    }
}