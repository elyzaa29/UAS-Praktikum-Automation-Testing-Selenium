package com.praktikum.testing.otomation.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Login Test dengan Page Object Model untuk Demoblaze.com
 * Menggunakan POM pattern untuk separation of concerns
 */
public class LoginTestWithPOM {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductPage productPage;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Initialize page objects
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);

        System.out.println("=== TEST SETUP COMPLETE ===");
        System.out.println("Browser: Chrome");
        System.out.println("URL: https://www.demoblaze.com/");
        System.out.println("Page Objects Initialized: LoginPage, HomePage, ProductPage\n");
    }

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        System.out.println("\n=== TEST 1: SUCCESSFUL LOGIN WITH POM ===");

        // Navigate to homepage
        homePage.navigateToHome();
        System.out.println("  Step 1: Navigated to homepage");

        // Verify homepage is loaded
        Assert.assertTrue(homePage.isHomePageLoaded(), "Homepage should be loaded");
        System.out.println("  Step 2: Homepage verified");

        // Click login button
        homePage.clickLoginButton();
        System.out.println("  Step 3: Clicked login button");

        // Wait for login modal
        Assert.assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        System.out.println("  Step 4: Login modal displayed");

        // Enter credentials (gunakan credentials yang valid)
        String username = "testuser123";
        String password = "test123";

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        System.out.println("  Step 5: Entered credentials - Username: " + username);

        // Click login in modal
        loginPage.clickLoginInModal();
        System.out.println("  Step 6: Clicked login in modal");

        // Wait for login success
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in successfully");
        System.out.println("  Step 7: Login successful - Welcome message displayed");

        // Verify welcome message
        String welcomeMessage = homePage.getWelcomeMessage();
        Assert.assertTrue(welcomeMessage.contains("Welcome"), "Welcome message should contain 'Welcome'");
        System.out.println("  Step 8: Welcome message verified: " + welcomeMessage);

        // Verify logout button is displayed
        Assert.assertTrue(homePage.isLogoutButtonDisplayed(), "Logout button should be displayed");
        System.out.println("  Step 9: Logout button displayed");

        System.out.println("\n✓ TEST 1 PASSED: Successful login with valid credentials\n");
    }

    @Test(priority = 2)
    public void testInvalidUsername() {
        System.out.println("\n=== TEST 2: INVALID USERNAME LOGIN ===");

        homePage.navigateToHome();
        homePage.clickLoginButton();

        Assert.assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        System.out.println("  Step 1: Login modal opened");

        // Enter invalid username
        String invalidUsername = "invalid_user_nonexistent";
        String validPassword = "test123";

        loginPage.enterUsername(invalidUsername);
        loginPage.enterPassword(validPassword);
        System.out.println("  Step 2: Entered invalid username: " + invalidUsername);

        loginPage.clickLoginInModal();
        System.out.println("  Step 3: Clicked login in modal");

        // Wait for alert and get text
        String alertText = loginPage.getAlertText();
        System.out.println("  Step 4: Alert appeared: " + alertText);

        // Verify error message
        Assert.assertTrue(alertText.contains("User does not exist") || alertText.contains("Wrong password"),
                "Alert should indicate login failure");

        // Accept alert
        loginPage.acceptAlert();
        System.out.println("  Step 5: Alert accepted");

        // Verify user is not logged in
        Assert.assertFalse(homePage.isUserLoggedIn(), "User should not be logged in");
        System.out.println("  Step 6: User not logged in - verified");

        System.out.println("\n✓ TEST 2 PASSED: Invalid username handled correctly\n");
    }

    @Test(priority = 3)
    public void testInvalidPassword() {
        System.out.println("\n=== TEST 3: INVALID PASSWORD LOGIN ===");

        homePage.navigateToHome();
        homePage.clickLoginButton();

        Assert.assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        System.out.println("  Step 1: Login modal opened");

        // Enter valid username but invalid password
        String validUsername = "testuser123";
        String invalidPassword = "wrongpassword123";

        loginPage.enterUsername(validUsername);
        loginPage.enterPassword(invalidPassword);
        System.out.println("  Step 2: Entered valid username but invalid password");

        loginPage.clickLoginInModal();
        System.out.println("  Step 3: Clicked login in modal");

        // Wait for alert
        String alertText = loginPage.getAlertText();
        System.out.println("  Step 4: Alert appeared: " + alertText);

        // Verify error message
        Assert.assertTrue(alertText.contains("Wrong password") || alertText.contains("User does not exist"),
                "Alert should indicate wrong password");

        loginPage.acceptAlert();
        System.out.println("  Step 5: Alert accepted");

        // Verify user is not logged in
        Assert.assertFalse(homePage.isUserLoggedIn(), "User should not be logged in");
        System.out.println("  Step 6: User not logged in - verified");

        System.out.println("\n✓ TEST 3 PASSED: Invalid password handled correctly\n");
    }

    @Test(priority = 4)
    public void testEmptyCredentials() {
        System.out.println("\n=== TEST 4: EMPTY CREDENTIALS LOGIN ===");

        homePage.navigateToHome();
        homePage.clickLoginButton();

        Assert.assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        System.out.println("  Step 1: Login modal opened");

        // Click login without entering credentials
        loginPage.clickLoginInModal();
        System.out.println("  Step 2: Clicked login without credentials");

        // Wait for alert
        String alertText = loginPage.getAlertText();
        System.out.println("  Step 3: Alert appeared: " + alertText);

        // Accept alert
        loginPage.acceptAlert();
        System.out.println("  Step 4: Alert accepted");

        // Verify user is not logged in
        Assert.assertFalse(homePage.isUserLoggedIn(), "User should not be logged in");
        System.out.println("  Step 5: User not logged in - verified");

        System.out.println("\n✓ TEST 4 PASSED: Empty credentials handled correctly\n");
    }

    @Test(priority = 5)
    public void testLoginLogoutFlow() {
        System.out.println("\n=== TEST 5: COMPLETE LOGIN-LOGOUT FLOW ===");

        // Step 1: Login
        homePage.navigateToHome();
        homePage.clickLoginButton();

        Assert.assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        System.out.println("  Step 1: Login modal opened");

        String username = "testuser123";
        String password = "test123";

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginInModal();
        System.out.println("  Step 2: Logged in with username: " + username);

        // Verify login successful
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
        System.out.println("  Step 3: Login successful");

        // Step 2: Navigate to product page while logged in
        homePage.clickFirstProduct();
        System.out.println("  Step 4: Navigated to product page");

        // Verify product page loaded
        Assert.assertTrue(productPage.isProductPageLoaded(), "Product page should be loaded");
        System.out.println("  Step 5: Product page verified");

        // Step 3: Go back to homepage
        homePage.navigateToHome();
        System.out.println("  Step 6: Returned to homepage");

        // Verify still logged in
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should remain logged in");
        System.out.println("  Step 7: Session persisted - user still logged in");

        // Step 4: Logout
        homePage.clickLogoutButton();
        System.out.println("  Step 8: Clicked logout button");

        // Verify logout successful
        Assert.assertFalse(homePage.isUserLoggedIn(), "User should be logged out");
        Assert.assertTrue(homePage.isLoginButtonDisplayed(), "Login button should be displayed");
        System.out.println("  Step 9: Logout successful - login button displayed");

        System.out.println("\n✓ TEST 5 PASSED: Complete login-logout flow successful\n");
    }

    @Test(priority = 6)
    public void testLoginModalElements() {
        System.out.println("\n=== TEST 6: LOGIN MODAL ELEMENTS VERIFICATION ===");

        homePage.navigateToHome();
        homePage.clickLoginButton();

        // Verify all modal elements
        Assert.assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        System.out.println("  Step 1: Login modal displayed");

        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be displayed");
        System.out.println("  Step 2: Username field displayed");

        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be displayed");
        System.out.println("  Step 3: Password field displayed");

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be displayed");
        System.out.println("  Step 4: Login button displayed");

        Assert.assertTrue(loginPage.isCloseButtonDisplayed(), "Close button should be displayed");
        System.out.println("  Step 5: Close button displayed");

        // Get modal title
        String modalTitle = loginPage.getModalTitle();
        Assert.assertTrue(modalTitle.contains("Log in"), "Modal title should contain 'Log in'");
        System.out.println("  Step 6: Modal title verified: " + modalTitle);

        // Close modal
        loginPage.clickCloseButton();
        System.out.println("  Step 7: Closed modal");

        // Verify modal is closed
        Assert.assertFalse(loginPage.isLoginModalDisplayed(), "Login modal should be closed");
        System.out.println("  Step 8: Modal closed - verified");

        System.out.println("\n✓ TEST 6 PASSED: All login modal elements verified\n");
    }

    @Test(priority = 7)
    public void testLoginWithSpecialCharacters() {
        System.out.println("\n=== TEST 7: LOGIN WITH SPECIAL CHARACTERS ===");

        homePage.navigateToHome();
        homePage.clickLoginButton();

        Assert.assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        System.out.println("  Step 1: Login modal opened");

        // Enter credentials with special characters
        String specialUsername = "test@user#123";
        String specialPassword = "P@ssw0rd!@#$";

        loginPage.enterUsername(specialUsername);
        loginPage.enterPassword(specialPassword);
        System.out.println("  Step 2: Entered credentials with special characters");

        loginPage.clickLoginInModal();
        System.out.println("  Step 3: Clicked login in modal");

        // Check for alert
        String alertText = loginPage.getAlertText();
        System.out.println("  Step 4: Alert appeared: " + alertText);

        // Accept alert
        loginPage.acceptAlert();
        System.out.println("  Step 5: Alert accepted");

        // Verify login failed (as expected with special chars)
        Assert.assertFalse(homePage.isUserLoggedIn(), "Login should fail with special characters");
        System.out.println("  Step 6: Login failed - as expected");

        System.out.println("\n✓ TEST 7 PASSED: Special characters handled\n");
    }

    @Test(priority = 8)
    public void testMultipleLoginAttempts() {
        System.out.println("\n=== TEST 8: MULTIPLE LOGIN ATTEMPTS ===");

        homePage.navigateToHome();

        // First attempt: Valid login
        System.out.println("\n  Attempt 1: Valid login");
        homePage.clickLoginButton();
        loginPage.enterUsername("testuser123");
        loginPage.enterPassword("test123");
        loginPage.clickLoginInModal();

        if (homePage.isUserLoggedIn()) {
            System.out.println("    ✓ First attempt: Login successful");

            // Logout for next attempt
            homePage.clickLogoutButton();
            System.out.println("    ✓ Logged out for next attempt");
        } else {
            System.out.println("    ✗ First attempt: Login failed");
            loginPage.acceptAlert();
        }

        // Second attempt: Invalid credentials
        System.out.println("\n  Attempt 2: Invalid credentials");
        homePage.clickLoginButton();
        loginPage.enterUsername("wronguser");
        loginPage.enterPassword("wrongpass");
        loginPage.clickLoginInModal();

        String alertText = loginPage.getAlertText();
        System.out.println("    ✓ Second attempt: Alert received: " + alertText);
        loginPage.acceptAlert();

        // Third attempt: Empty credentials
        System.out.println("\n  Attempt 3: Empty credentials");
        homePage.clickLoginButton();
        loginPage.clickLoginInModal();

        alertText = loginPage.getAlertText();
        System.out.println("    ✓ Third attempt: Alert received: " + alertText);
        loginPage.acceptAlert();

        // Verify final state
        Assert.assertFalse(homePage.isUserLoggedIn(), "Should not be logged in after multiple attempts");
        System.out.println("\n  Final state: User not logged in - correct");

        System.out.println("\n✓ TEST 8 PASSED: Multiple login attempts handled\n");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("Browser closed\n");
        }
    }

    // =================================================================
    // INNER PAGE OBJECT CLASSES
    // =================================================================

    /**
     * Login Page Object untuk halaman login modal di Demoblaze
     */
    private class LoginPage {
        private WebDriver driver;
        private org.openqa.selenium.support.ui.WebDriverWait wait;

        // Locators menggunakan By (Page Object Pattern)
        private final org.openqa.selenium.By loginModal = org.openqa.selenium.By.id("logInModal");
        private final org.openqa.selenium.By usernameField = org.openqa.selenium.By.id("loginusername");
        private final org.openqa.selenium.By passwordField = org.openqa.selenium.By.id("loginpassword");
        private final org.openqa.selenium.By loginButton = org.openqa.selenium.By.xpath("//button[contains(text(),'Log in')]");
        private final org.openqa.selenium.By closeButton = org.openqa.selenium.By.xpath("//button[contains(text(),'Close')]");
        private final org.openqa.selenium.By modalTitle = org.openqa.selenium.By.id("logInModalLabel");

        public LoginPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(15));
        }

        public boolean isLoginModalDisplayed() {
            try {
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                        .visibilityOfElementLocated(loginModal));
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public void enterUsername(String username) {
            WebElement element = wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                    .visibilityOfElementLocated(usernameField));
            element.clear();
            element.sendKeys(username);
        }

        public void enterPassword(String password) {
            WebElement element = driver.findElement(passwordField);
            element.clear();
            element.sendKeys(password);
        }

        public void clickLoginInModal() {
            WebElement element = wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(loginButton));
            element.click();
        }

        public String getAlertText() {
            try {
                org.openqa.selenium.support.ui.WebDriverWait alertWait =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver,
                                java.time.Duration.ofSeconds(10));
                alertWait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
                org.openqa.selenium.Alert alert = driver.switchTo().alert();
                return alert.getText();
            } catch (Exception e) {
                return "No alert found";
            }
        }

        public void acceptAlert() {
            try {
                org.openqa.selenium.Alert alert = driver.switchTo().alert();
                alert.accept();
            } catch (Exception e) {
                // No alert to accept
            }
        }

        public boolean isUsernameFieldDisplayed() {
            try {
                return driver.findElement(usernameField).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public boolean isPasswordFieldDisplayed() {
            try {
                return driver.findElement(passwordField).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public boolean isLoginButtonDisplayed() {
            try {
                return driver.findElement(loginButton).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public boolean isCloseButtonDisplayed() {
            try {
                return driver.findElement(closeButton).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public String getModalTitle() {
            try {
                return driver.findElement(modalTitle).getText();
            } catch (Exception e) {
                return "";
            }
        }

        public void clickCloseButton() {
            try {
                driver.findElement(closeButton).click();
            } catch (Exception e) {
                // Ignore if not found
            }
        }
    }

    /**
     * Home Page Object untuk homepage Demoblaze
     */
    private class HomePage {
        private WebDriver driver;
        private org.openqa.selenium.support.ui.WebDriverWait wait;

        // Locators
        private final org.openqa.selenium.By loginButton = org.openqa.selenium.By.id("login2");
        private final org.openqa.selenium.By logoutButton = org.openqa.selenium.By.id("logout2");
        private final org.openqa.selenium.By welcomeMessage = org.openqa.selenium.By.id("nameofuser");
        private final org.openqa.selenium.By firstProduct = org.openqa.selenium.By.cssSelector(".card-title a");
        private final org.openqa.selenium.By navbar = org.openqa.selenium.By.id("nava");

        public HomePage(WebDriver driver) {
            this.driver = driver;
            this.wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(15));
        }

        public void navigateToHome() {
            driver.get("https://www.demoblaze.com/");
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                    .titleContains("STORE"));
        }

        public boolean isHomePageLoaded() {
            try {
                return driver.getTitle().contains("STORE") &&
                        driver.findElement(navbar).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public void clickLoginButton() {
            WebElement element = wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(loginButton));
            element.click();
        }

        public boolean isUserLoggedIn() {
            try {
                // Wait for welcome message to appear
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                        .visibilityOfElementLocated(welcomeMessage));
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public String getWelcomeMessage() {
            try {
                WebElement element = driver.findElement(welcomeMessage);
                return element.getText();
            } catch (Exception e) {
                return "";
            }
        }

        public boolean isLogoutButtonDisplayed() {
            try {
                return driver.findElement(logoutButton).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public void clickLogoutButton() {
            try {
                WebElement element = wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(logoutButton));
                element.click();

                // Wait for login button to reappear
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(loginButton));
            } catch (Exception e) {
                // Ignore if already logged out
            }
        }

        public boolean isLoginButtonDisplayed() {
            try {
                return driver.findElement(loginButton).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        public void clickFirstProduct() {
            try {
                WebElement element = wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                        .elementToBeClickable(firstProduct));
                element.click();
            } catch (Exception e) {
                // Handle exception
            }
        }
    }

    /**
     * Product Page Object untuk halaman detail produk
     */
    private class ProductPage {
        private WebDriver driver;
        private org.openqa.selenium.support.ui.WebDriverWait wait;

        // Locators
        private final org.openqa.selenium.By productName = org.openqa.selenium.By.cssSelector("h2.name");
        private final org.openqa.selenium.By addToCartButton = org.openqa.selenium.By.linkText("Add to cart");

        public ProductPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(15));
        }

        public boolean isProductPageLoaded() {
            try {
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                        .visibilityOfElementLocated(productName));
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public String getProductName() {
            try {
                return driver.findElement(productName).getText();
            } catch (Exception e) {
                return "";
            }
        }

        public void clickAddToCart() {
            try {
                driver.findElement(addToCartButton).click();
            } catch (Exception e) {
                // Handle exception
            }
        }
    }
}