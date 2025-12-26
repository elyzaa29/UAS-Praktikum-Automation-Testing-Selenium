package com.praktikum.testing.otomation.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

/**
 * Wait Strategies Demo untuk website Demoblaze.com
 * URL: https://www.demoblaze.com/
 * Demonstrasi berbagai wait strategies di Selenium WebDriver
 */
public class WaitStrategiesDemo {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        System.out.println("=== TEST SETUP COMPLETE ===");
    }

    @Test(priority = 1)
    public void demonstrateImplicitWait() {
        System.out.println("\n=== IMPLICIT WAIT DEMO ===");

        // Set implicit wait - applies to all findElement calls
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("  Implicit wait set: 10 seconds");

        // Navigate to Demoblaze
        driver.get("https://www.demoblaze.com/");
        System.out.println("  Navigated to Demoblaze");

        // Test 1: Find element dengan implicit wait
        long startTime = System.currentTimeMillis();
        try {
            WebElement element = driver.findElement(By.id("nava"));
            long endTime = System.currentTimeMillis();
            System.out.println("  1. Found navbar in " + (endTime - startTime) + "ms");
            System.out.println("     Element text: " + element.getText());
        } catch (Exception e) {
            System.out.println("  1. Element not found: " + e.getMessage());
        }

        // Test 2: Find non-existent element (will wait 10 seconds)
        System.out.println("\n  2. Trying to find non-existent element...");
        startTime = System.currentTimeMillis();
        try {
            // Ini akan menunggu 10 detik sebelum throw exception
            driver.findElement(By.id("nonexistent-element-12345"));
            System.out.println("    ERROR: Should not find this element");
        } catch (NoSuchElementException e) {
            long endTime = System.currentTimeMillis();
            long waitTime = endTime - startTime;
            System.out.println("    Element not found after " + waitTime + "ms");
            System.out.println("    Exception: " + e.getClass().getSimpleName());
            Assert.assertTrue(waitTime >= 10000, "Should wait at least 10 seconds");
        }

        // Test 3: Multiple elements dengan implicit wait
        System.out.println("\n  3. Finding multiple product cards...");
        startTime = System.currentTimeMillis();
        List<WebElement> products = driver.findElements(By.className("card"));
        long endTime = System.currentTimeMillis();
        System.out.println("    Found " + products.size() + " products in " +
                (endTime - startTime) + "ms");

        // Reset implicit wait (best practice: set back to 0 setelah test)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        System.out.println("\n  Implicit wait reset to 0 seconds");

        System.out.println("\n✓ Implicit wait test PASSED");
    }

    @Test(priority = 2)
    public void demonstrateExplicitWait() {
        System.out.println("\n=== EXPLICIT WAIT DEMO ===");

        driver.get("https://www.demoblaze.com/");

        System.out.println("  WebDriverWait created with 15 seconds timeout");

        // Test 1: Wait for element to be present
        System.out.println("\n  1. Waiting for navbar to be present...");
        long startTime = System.currentTimeMillis();
        WebElement navbar = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("nava"))
        );
        long endTime = System.currentTimeMillis();
        System.out.println("    Navbar found in " + (endTime - startTime) + "ms");
        System.out.println("    Navbar text: " + navbar.getText());

        // Test 2: Wait for element to be visible
        System.out.println("\n  2. Waiting for login button to be visible...");
        WebElement loginButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("login2"))
        );
        System.out.println("    Login button visible: " + loginButton.getText());

        // Test 3: Wait for element to be clickable
        System.out.println("\n  3. Waiting for login button to be clickable...");
        loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login2"))
        );
        System.out.println("    Login button clickable: " + loginButton.isEnabled());

        // Test 4: Wait for title
        System.out.println("\n  4. Waiting for page title...");
        boolean titleContains = wait.until(
                ExpectedConditions.titleContains("STORE")
        );
        System.out.println("    Title contains 'STORE': " + titleContains);
        System.out.println("    Actual title: " + driver.getTitle());

        // Test 5: Wait for URL
        System.out.println("\n  5. Waiting for URL to contain 'demoblaze'...");
        boolean urlContains = wait.until(
                ExpectedConditions.urlContains("demoblaze")
        );
        System.out.println("    URL contains 'demoblaze': " + urlContains);
        System.out.println("    Actual URL: " + driver.getCurrentUrl());

        // Test 6: Wait for text to be present
        System.out.println("\n  6. Waiting for 'PRODUCT STORE' text...");
        boolean textPresent = wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.cssSelector("h5 a"), "PRODUCT STORE")
        );
        System.out.println("    Text 'PRODUCT STORE' present: " + textPresent);

        // Test 7: Wait for multiple elements
        System.out.println("\n  7. Waiting for all product cards...");
        List<WebElement> products = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card"))
        );
        System.out.println("    Found " + products.size() + " product cards");

        // Test 8: Wait for element attribute
        System.out.println("\n  8. Waiting for navbar to have 'navbar' class...");
        boolean hasClass = wait.until(
                ExpectedConditions.attributeContains(By.id("nava"), "class", "navbar")
        );
        System.out.println("    Navbar has 'navbar' class: " + hasClass);

        System.out.println("\n✓ Explicit wait test PASSED");
    }

    @Test(priority = 3)
    public void demonstrateExplicitWaitWithLoginModal() {
        System.out.println("\n=== EXPLICIT WAIT WITH LOGIN MODAL ===");

        driver.get("https://www.demoblaze.com/");

        // Click login button to open modal
        System.out.println("  1. Opening login modal...");
        driver.findElement(By.id("login2")).click();

        // Wait for modal to be visible
        System.out.println("  2. Waiting for login modal to be visible...");
        WebElement loginModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("logInModal"))
        );
        System.out.println("    Login modal visible: " + loginModal.isDisplayed());

        // Wait for username field
        System.out.println("  3. Waiting for username field...");
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))
        );
        System.out.println("    Username field visible: " + usernameField.isDisplayed());

        // Wait for password field
        System.out.println("  4. Waiting for password field...");
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword"))
        );
        System.out.println("    Password field visible: " + passwordField.isDisplayed());

        // Wait for login button in modal
        System.out.println("  5. Waiting for login button in modal...");
        WebElement modalLoginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Log in')]")
                )
        );
        System.out.println("    Login button clickable: " + modalLoginButton.isEnabled());

        // Fill form
        System.out.println("  6. Filling login form...");
        usernameField.sendKeys("testuser");
        passwordField.sendKeys("testpass");
        System.out.println("    Form filled");

        // Wait for close button
        System.out.println("  7. Waiting for close button...");
        WebElement closeButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Close')]")
                )
        );

        // Close modal
        System.out.println("  8. Closing modal...");
        closeButton.click();

        // Wait for modal to be invisible
        System.out.println("  9. Waiting for modal to close...");
        boolean modalClosed = wait.until(
                ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal"))
        );
        System.out.println("    Modal closed: " + modalClosed);

        System.out.println("\n✓ Explicit wait with login modal test PASSED");
    }

    @Test(priority = 4)
    public void demonstrateFluentWait() {
        System.out.println("\n=== FLUENT WAIT DEMO ===");

        driver.get("https://www.demoblaze.com/");

        // Create FluentWait dengan custom polling dan exceptions
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        System.out.println("  FluentWait created:");
        System.out.println("    - Timeout: 10 seconds");
        System.out.println("    - Polling interval: 500ms");
        System.out.println("    - Ignoring: NoSuchElementException, StaleElementReferenceException");

        // Test 1: FluentWait dengan custom condition
        System.out.println("\n  1. Custom condition: Wait for navbar with specific text...");
        WebElement navbar = fluentWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                System.out.println("    Polling...");
                WebElement element = driver.findElement(By.id("nava"));
                if (element.isDisplayed() && element.getText().contains("PRODUCT")) {
                    return element;
                }
                return null;
            }
        });
        System.out.println("    Navbar found: " + navbar.getText());

        // Test 2: FluentWait dengan lambda expression
        System.out.println("\n  2. Lambda expression: Wait for login button...");
        WebElement loginButton = fluentWait.until(d -> {
            WebElement btn = d.findElement(By.id("login2"));
            if (btn.isDisplayed() && btn.isEnabled()) {
                return btn;
            }
            return null;
        });
        System.out.println("    Login button found: " + loginButton.getText());

        // Test 3: FluentWait yang selalu return false (timeout test)
        System.out.println("\n  3. Testing timeout scenario...");
        long startTime = System.currentTimeMillis();
        try {
            fluentWait.until(d -> {
                // Always return false to trigger timeout
                return false;
            });
            System.out.println("    ERROR: Should timeout");
        } catch (TimeoutException e) {
            long endTime = System.currentTimeMillis();
            System.out.println("    Timeout after " + (endTime - startTime) + "ms");
            System.out.println("    Exception: " + e.getClass().getSimpleName());
        }

        // Test 4: FluentWait dengan ExpectedConditions
        System.out.println("\n  4. FluentWait with ExpectedConditions...");
        WebDriverWait fluentWaitWithEC = (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);

        WebElement cartButton = fluentWaitWithEC.until(
                ExpectedConditions.elementToBeClickable(By.id("cartur"))
        );
        System.out.println("    Cart button found: " + cartButton.getText());

        System.out.println("\n✓ Fluent wait test PASSED");
    }

    @Test(priority = 5)
    public void demonstrateFluentWaitWithDynamicContent() {
        System.out.println("\n=== FLUENT WAIT WITH DYNAMIC CONTENT ===");

        driver.get("https://www.demoblaze.com/");

        // Create FluentWait
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        // Test: Wait for product list to load
        System.out.println("  Waiting for product list to load...");
        List<WebElement> products = fluentWait.until(driver -> {
            System.out.println("    Polling for products...");
            List<WebElement> elements = driver.findElements(By.className("card"));
            if (elements.size() >= 9) { // Demoblaze biasanya punya 9 produk
                System.out.println("    Found " + elements.size() + " products");
                return elements;
            }
            return null;
        });

        System.out.println("  Final product count: " + products.size());
        Assert.assertTrue(products.size() >= 9, "Should have at least 9 products");

        // Test: Wait for specific product
        System.out.println("\n  Waiting for Samsung product...");
        WebElement samsungProduct = fluentWait.until(driver -> {
            List<WebElement> allProducts = driver.findElements(By.className("card-title"));
            for (WebElement product : allProducts) {
                if (product.getText().contains("Samsung")) {
                    System.out.println("    Found: " + product.getText());
                    return product;
                }
            }
            return null;
        });

        System.out.println("  Samsung product found: " + samsungProduct.getText());

        // Click product
        System.out.println("\n  Clicking Samsung product...");
        samsungProduct.click();

        // Wait for product page
        System.out.println("  Waiting for product page to load...");
        boolean productPageLoaded = fluentWait.until(driver -> {
            try {
                WebElement productName = driver.findElement(By.cssSelector("h2.name"));
                return productName.isDisplayed() &&
                        productName.getText().contains("Samsung");
            } catch (Exception e) {
                return false;
            }
        });

        System.out.println("  Product page loaded: " + productPageLoaded);

        // Navigate back
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("\n✓ Fluent wait with dynamic content test PASSED");
    }

    @Test(priority = 6)
    public void demonstrateAlertWaitStrategies() {
        System.out.println("\n=== ALERT WAIT STRATEGIES ===");

        driver.get("https://www.demoblaze.com/");

        // Go to product page
        driver.findElement(By.cssSelector(".card-title a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2.name")
        ));

        System.out.println("  On product page: " + driver.getTitle());

        // Test 1: Wait for alert after add to cart
        System.out.println("\n  1. Adding to cart and waiting for alert...");
        driver.findElement(By.linkText("Add to cart")).click();

        // Method 1: WebDriverWait dengan ExpectedConditions.alertIsPresent()
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("    Alert text: " + alertText);
            Assert.assertTrue(alertText.contains("Product added"));
            alert.accept();
            System.out.println("    Alert accepted");
        } catch (TimeoutException e) {
            System.out.println("    No alert appeared within timeout");
        }

        // Test 2: FluentWait for alert
        System.out.println("\n  2. Testing FluentWait for alert...");

        // Add another product
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("STORE"));
        driver.findElement(By.cssSelector(".card-title a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2.name")
        ));

        driver.findElement(By.linkText("Add to cart")).click();

        Wait<WebDriver> alertWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoAlertPresentException.class);

        Alert alert = alertWait.until(driver -> {
            try {
                return driver.switchTo().alert();
            } catch (NoAlertPresentException e) {
                return null;
            }
        });

        System.out.println("    Alert found: " + alert.getText());
        alert.accept();

        System.out.println("\n✓ Alert wait strategies test PASSED");
    }

    @Test(priority = 7)
    public void demonstrateFrameWaitStrategies() {
        System.out.println("\n=== FRAME/IFRAME WAIT STRATEGIES ===");

        // Note: Demoblaze tidak menggunakan iframe,
        // jadi kita akan demo dengan halaman yang punya iframe

        // Navigate ke halaman dengan iframe untuk demo
        System.out.println("  Navigating to page with iframe for demonstration...");
        driver.get("https://the-internet.herokuapp.com/iframe");

        // Test 1: Wait for iframe to be available
        System.out.println("\n  1. Waiting for iframe to be available...");
        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("mce_0_ifr"))
        );
        System.out.println("    Iframe found");

        // Test 2: Wait and switch to iframe
        System.out.println("  2. Switching to iframe...");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mce_0_ifr"));
        System.out.println("    Switched to iframe");

        // Test 3: Wait for element inside iframe
        System.out.println("  3. Waiting for editor inside iframe...");
        WebElement editor = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("tinymce"))
        );
        System.out.println("    Editor found, text: " + editor.getText());

        // Test 4: Switch back to main content
        System.out.println("  4. Switching back to main content...");
        driver.switchTo().defaultContent();

        // Verify back in main content
        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h3"))
        );
        System.out.println("    Back in main content, heading: " + heading.getText());

        // Kembali ke Demoblaze
        System.out.println("\n  Returning to Demoblaze...");
        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("\n✓ Frame/iframe wait strategies test PASSED");
    }

    @Test(priority = 8)
    public void demonstrateCustomWaitConditions() {
        System.out.println("\n=== CUSTOM WAIT CONDITIONS ===");

        driver.get("https://www.demoblaze.com/");

        // Custom Condition 1: Wait for minimum number of products
        System.out.println("\n  1. Custom condition: Wait for at least 9 products...");

        boolean productsLoaded = wait.until(driver -> {
            List<WebElement> products = driver.findElements(By.className("card"));
            System.out.println("    Current product count: " + products.size());
            return products.size() >= 9;
        });

        System.out.println("    Products loaded: " + productsLoaded);

        // Custom Condition 2: Wait for page to be fully loaded
        System.out.println("\n  2. Custom condition: Wait for page to be fully loaded...");

        boolean pageFullyLoaded = wait.until(driver -> {
            String readyState = (String) js.executeScript("return document.readyState");
            boolean domComplete = readyState.equals("complete");

            // Also check if main elements are visible
            boolean navbarVisible = driver.findElement(By.id("nava")).isDisplayed();
            boolean productsVisible = !driver.findElements(By.className("card")).isEmpty();

            System.out.println("    Ready state: " + readyState +
                    ", Navbar: " + navbarVisible +
                    ", Products: " + productsVisible);

            return domComplete && navbarVisible && productsVisible;
        });

        System.out.println("    Page fully loaded: " + pageFullyLoaded);

        // Custom Condition 3: Wait for element with specific attribute value
        System.out.println("\n  3. Custom condition: Wait for element with 'navbar' class...");

        WebElement navbar = wait.until(driver -> {
            WebElement element = driver.findElement(By.id("nava"));
            String className = element.getAttribute("class");
            if (className != null && className.contains("navbar")) {
                return element;
            }
            return null;
        });

        System.out.println("    Navbar found with class: " + navbar.getAttribute("class"));

        // Custom Condition 4: Wait for AJAX content (simulasi)
        System.out.println("\n  4. Custom condition: Simulating AJAX content wait...");

        // Simulate AJAX by clicking category
        driver.findElement(By.linkText("Phones")).click();

        boolean ajaxComplete = wait.until(driver -> {
            // Check if URL changed
            boolean urlChanged = driver.getCurrentUrl().contains("phones");

            // Check if specific phone elements are visible
            List<WebElement> phoneProducts = driver.findElements(
                    By.xpath("//a[contains(text(),'Samsung') or contains(text(),'Nokia')]")
            );

            boolean hasPhoneProducts = phoneProducts.size() > 0;

            System.out.println("    URL changed: " + urlChanged +
                    ", Phone products: " + hasPhoneProducts);

            return urlChanged && hasPhoneProducts;
        });

        System.out.println("    AJAX content loaded: " + ajaxComplete);

        System.out.println("\n✓ Custom wait conditions test PASSED");
    }

    @Test(priority = 9)
    public void compareWaitStrategies() {
        System.out.println("\n=== COMPARING WAIT STRATEGIES ===");

        System.out.println("\n  1. IMPLICIT WAIT:");
        System.out.println("     Pros:");
        System.out.println("       - Simple to implement");
        System.out.println("       - Applies globally to all findElement calls");
        System.out.println("       - Good for simple test cases");
        System.out.println("     Cons:");
        System.out.println("       - Less flexible");
        System.out.println("       - Can slow down tests (always waits)");
        System.out.println("       - Only waits for presence, not other conditions");
        System.out.println("     Best for: Quick prototyping, simple static pages");

        System.out.println("\n  2. EXPLICIT WAIT (WebDriverWait):");
        System.out.println("     Pros:");
        System.out.println("       - Highly flexible");
        System.out.println("       - Specific conditions (visibility, clickability, etc.)");
        System.out.println("       - Better performance (waits only when needed)");
        System.out.println("       - Rich set of ExpectedConditions");
        System.out.println("     Cons:");
        System.out.println("       - More verbose code");
        System.out.println("       - Need to create for each wait scenario");
        System.out.println("     Best for: Dynamic content, AJAX applications, complex scenarios");

        System.out.println("\n  3. FLUENT WAIT:");
        System.out.println("     Pros:");
        System.out.println("       - Most flexible");
        System.out.println("       - Custom polling intervals");
        System.out.println("       - Can ignore specific exceptions");
        System.out.println("       - Custom conditions with lambda expressions");
        System.out.println("     Cons:");
        System.out.println("       - Most complex to implement");
        System.out.println("       - Overkill for simple cases");
        System.out.println("     Best for: Complex conditions, custom polling needs, advanced scenarios");

        System.out.println("\n  RECOMMENDED PRACTICES FOR DEMOBLAZE:");
        System.out.println("    1. Use Explicit Wait (WebDriverWait) for 90% of cases");
        System.out.println("    2. Combine with ExpectedConditions for robustness");
        System.out.println("    3. Use FluentWait for:");
        System.out.println("       - Dynamic product loading");
        System.out.println("       - AJAX form submissions");
        System.out.println("       - Complex modal interactions");
        System.out.println("    4. Avoid mixing Implicit and Explicit waits");
        System.out.println("    5. Set reasonable timeouts (10-15 seconds for Demoblaze)");

        System.out.println("\n  EXAMPLE USAGE PATTERNS:");

        System.out.println("\n    Pattern 1: Basic element wait");
        System.out.println("      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));");
        System.out.println("      WebElement element = wait.until(");
        System.out.println("          ExpectedConditions.visibilityOfElementLocated(By.id(\"login2\"))");
        System.out.println("      );");

        System.out.println("\n    Pattern 2: Multiple conditions");
        System.out.println("      boolean ready = wait.until(driver -> {");
        System.out.println("          return driver.getTitle().contains(\"STORE\") &&");
        System.out.println("                 driver.findElement(By.id(\"nava\")).isDisplayed();");
        System.out.println("      });");

        System.out.println("\n    Pattern 3: FluentWait for dynamic content");
        System.out.println("      Wait<WebDriver> fluentWait = new FluentWait<>(driver)");
        System.out.println("          .withTimeout(Duration.ofSeconds(15))");
        System.out.println("          .pollingEvery(Duration.ofMillis(500))");
        System.out.println("          .ignoring(NoSuchElementException.class);");

        System.out.println("\n  PERFORMANCE COMPARISON:");
        System.out.println("    - Implicit Wait: Always adds delay, even if element is present");
        System.out.println("    - Explicit Wait: Waits only as needed, better performance");
        System.out.println("    - Fluent Wait: Most control, can optimize polling intervals");

        System.out.println("\n  DEBUGGING TIPS:");
        System.out.println("    1. Add logging in custom conditions");
        System.out.println("    2. Use shorter timeouts during development");
        System.out.println("    3. Check browser console for JavaScript errors");
        System.out.println("    4. Use try-catch for timeout exceptions");

        System.out.println("\n✓ Wait strategies comparison completed");
    }

    @Test(priority = 10)
    public void demonstrateWaitBestPractices() {
        System.out.println("\n=== WAIT BEST PRACTICES DEMONSTRATION ===");

        driver.get("https://www.demoblaze.com/");

        // Best Practice 1: Don't mix implicit and explicit waits
        System.out.println("\n  1. Avoid mixing implicit and explicit waits");
        System.out.println("     - Set implicit wait to 0 when using explicit waits");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        System.out.println("     ✓ Implicit wait set to 0");

        // Create explicit wait
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Best Practice 2: Use appropriate ExpectedConditions
        System.out.println("\n  2. Use appropriate ExpectedConditions");

        System.out.println("     a. presenceOfElementLocated - Element exists in DOM");
        WebElement navbar = explicitWait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("nava"))
        );
        System.out.println("       ✓ Navbar present in DOM");

        System.out.println("     b. visibilityOfElementLocated - Element is visible");
        navbar = explicitWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nava"))
        );
        System.out.println("       ✓ Navbar visible");

        System.out.println("     c. elementToBeClickable - Element is clickable");
        WebElement loginButton = explicitWait.until(
                ExpectedConditions.elementToBeClickable(By.id("login2"))
        );
        System.out.println("       ✓ Login button clickable");

        // Best Practice 3: Use reasonable timeout values
        System.out.println("\n  3. Use reasonable timeout values");
        System.out.println("     - Local testing: 10-15 seconds");
        System.out.println("     - CI/CD pipeline: 20-30 seconds");
        System.out.println("     - Slow networks: 30-45 seconds");

        // Best Practice 4: Handle StaleElementReferenceException
        System.out.println("\n  4. Handle StaleElementReferenceException");

        try {
            // Click login to open modal
            loginButton.click();

            // Wait for modal
            explicitWait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("logInModal"))
            );

            // Find element in modal
            WebElement usernameField = driver.findElement(By.id("loginusername"));

            // Simulate page refresh (causing stale element)
            driver.navigate().refresh();

            // Try to use stale element - will throw exception
            try {
                usernameField.sendKeys("test");
                System.out.println("       ✗ Should not reach here");
            } catch (StaleElementReferenceException e) {
                System.out.println("       ✓ Caught StaleElementReferenceException");

                // Re-find the element
                explicitWait.until(ExpectedConditions.titleContains("STORE"));
                driver.findElement(By.id("login2")).click();
                usernameField = explicitWait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))
                );
                usernameField.sendKeys("test");
                System.out.println("       ✓ Re-found element and used it");
            }

            // Close modal
            driver.findElement(
                    By.xpath("//button[contains(text(),'Close')]")
            ).click();

        } catch (Exception e) {
            System.out.println("       Error: " + e.getMessage());
        }

        // Best Practice 5: Use custom wait conditions for complex scenarios
        System.out.println("\n  5. Use custom conditions for complex scenarios");

        boolean pageReady = explicitWait.until(driver -> {
            // Check multiple conditions
            boolean titleOk = driver.getTitle().contains("STORE");
            boolean navbarReady = driver.findElement(By.id("nava")).isDisplayed();
            boolean hasProducts = driver.findElements(By.className("card")).size() >= 5;
            boolean noLoading = !driver.findElements(
                    By.xpath("//*[contains(text(),'Loading')]")
            ).isEmpty();

            return titleOk && navbarReady && hasProducts && !noLoading;
        });

        System.out.println("     ✓ Page ready check passed: " + pageReady);

        // Best Practice 6: Log wait activities for debugging
        System.out.println("\n  6. Log wait activities for debugging");

        Wait<WebDriver> loggingWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(1000))
                .ignoring(NoSuchElementException.class);

        WebElement element = loggingWait.until(d -> {
            System.out.println("       [DEBUG] Polling for cart button...");
            WebElement el = d.findElement(By.id("cartur"));
            if (el.isDisplayed()) {
                System.out.println("       [DEBUG] Cart button found and displayed");
                return el;
            }
            System.out.println("       [DEBUG] Cart button found but not displayed");
            return null;
        });

        System.out.println("     ✓ Cart button found with logging: " + element.getText());

        System.out.println("\n✓ Wait best practices demonstration completed");
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
            System.out.println("\nBrowser closed");
        }
    }
}