package com.praktikum.testing.otomation.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * JavaScript Executor Demo untuk website Demoblaze.com
 * URL: https://www.demoblaze.com/
 * Demonstrasi penggunaan JavaScriptExecutor untuk advanced interactions
 */
public class JavaScriptExecutorDemo {
    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void demonstrateBasicJavaScriptOperations() {
        System.out.println("\n=== BASIC JAVASCRIPT OPERATIONS ON DEMOBLAZE ===");

        // 1. Navigate to Demoblaze
        driver.get("https://www.demoblaze.com/");
        System.out.println("  Navigated to Demoblaze homepage");

        // 2. Get page title via JavaScript
        String title = (String) js.executeScript("return document.title;");
        System.out.println("  1. Page title via JS: " + title);
        Assert.assertEquals(title, "STORE", "Page title should be 'STORE'");

        // 3. Get current URL via JavaScript
        String currentUrl = (String) js.executeScript("return window.location.href;");
        System.out.println("  2. Current URL via JS: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("demoblaze"), "URL should contain 'demoblaze'");

        // 4. Get viewport dimensions
        long viewportWidth = (Long) js.executeScript("return window.innerWidth;");
        long viewportHeight = (Long) js.executeScript("return window.innerHeight;");
        System.out.println("  3. Viewport size: " + viewportWidth + "x" + viewportHeight);
        Assert.assertTrue(viewportWidth > 0, "Viewport width should be positive");

        // 5. Change page title temporarily
        js.executeScript("document.title = 'Modified by Selenium JS - Demoblaze';");
        String modifiedTitle = (String) js.executeScript("return document.title;");
        System.out.println("  4. Modified title: " + modifiedTitle);

        // 6. Change it back
        js.executeScript("document.title = 'STORE';");

        // 7. Get page source length
        long pageSourceLength = (Long) js.executeScript("return document.documentElement.innerHTML.length;");
        System.out.println("  5. Page source length: " + pageSourceLength + " characters");

        // 8. Get number of images on page
        long imageCount = (Long) js.executeScript("return document.images.length;");
        System.out.println("  6. Number of images on page: " + imageCount);

        // 9. Get number of links
        long linkCount = (Long) js.executeScript("return document.links.length;");
        System.out.println("  7. Number of links on page: " + linkCount);

        System.out.println("\n  Basic JavaScript operations test PASSED");
    }

    @Test(priority = 2)
    public void demonstrateScrollOperations() {
        System.out.println("\n=== SCROLL OPERATIONS ON DEMOBLAZE ===");

        driver.get("https://www.demoblaze.com/");

        // Wait for page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));

        // 1. Scroll to bottom of page
        System.out.println("  1. Scrolling to bottom of page...");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        // 2. Scroll back to top
        System.out.println("  2. Scrolling back to top...");
        js.executeScript("window.scrollTo(0, 0);");

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        // 3. Scroll by specific pixels
        System.out.println("  3. Scrolling down 500 pixels...");
        js.executeScript("window.scrollBy(0, 500);");

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        // 4. Scroll to specific element (footer)
        System.out.println("  4. Scrolling to footer...");
        WebElement footer = driver.findElement(By.cssSelector("footer"));
        js.executeScript("arguments[0].scrollIntoView(true);", footer);

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        // 5. Scroll to navbar
        System.out.println("  5. Scrolling to navbar...");
        WebElement navbar = driver.findElement(By.id("nava"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", navbar);

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        System.out.println("\n  Scroll operations test PASSED");
    }

    @Test(priority = 3)
    public void demonstrateElementHighlighting() {
        System.out.println("\n=== ELEMENT HIGHLIGHTING ON DEMOBLAZE ===");

        driver.get("https://www.demoblaze.com/");

        // Wait for page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));

        // 1. Highlight navbar
        System.out.println("  1. Highlighting navbar...");
        WebElement navbar = driver.findElement(By.id("nava"));
        js.executeScript("arguments[0].style.border='3px solid red'; arguments[0].style.backgroundColor='yellow';", navbar);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // 2. Highlight login button
        System.out.println("  2. Highlighting login button...");
        WebElement loginButton = driver.findElement(By.id("login2"));
        js.executeScript("arguments[0].style.border='3px solid blue'; arguments[0].style.backgroundColor='lightblue';", loginButton);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // 3. Highlight cart button
        System.out.println("  3. Highlighting cart button...");
        WebElement cartButton = driver.findElement(By.id("cartur"));
        js.executeScript("arguments[0].style.border='3px solid green'; arguments[0].style.backgroundColor='lightgreen';", cartButton);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // 4. Highlight product card
        System.out.println("  4. Highlighting first product card...");
        WebElement firstProduct = driver.findElement(By.cssSelector(".card-block"));
        js.executeScript("arguments[0].style.border='3px solid orange'; arguments[0].style.backgroundColor='#FFF3CD';", firstProduct);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // 5. Remove all highlights
        System.out.println("  5. Removing all highlights...");
        js.executeScript("""
            var elements = document.querySelectorAll('[style*="border"]');
            for(var i = 0; i < elements.length; i++) {
                elements[i].style.border = '';
                elements[i].style.backgroundColor = '';
            }
        """);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        System.out.println("\n  Element highlighting test PASSED");
    }

    @Test(priority = 4)
    public void demonstrateClickViaJavaScript() {
        System.out.println("\n=== CLICK VIA JAVASCRIPT ON DEMOBLAZE ===");

        driver.get("https://www.demoblaze.com/");

        // Wait for page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));

        // 1. Click login button via JavaScript (without opening modal)
        System.out.println("  1. Getting login button innerHTML before click...");
        WebElement loginButton = driver.findElement(By.id("login2"));
        String loginInnerHTML = (String) js.executeScript(
                "return arguments[0].innerHTML;", loginButton
        );
        System.out.println("    Login button innerHTML: " + loginInnerHTML);

        // 2. Click via JavaScript
        System.out.println("  2. Clicking login button via JavaScript...");
        js.executeScript("arguments[0].click();", loginButton);

        // Wait for modal to appear
        WebElement loginModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("logInModal"))
        );
        System.out.println("    Login modal appeared");

        // 3. Close modal via JavaScript
        System.out.println("  3. Closing modal via JavaScript...");
        WebElement closeButton = driver.findElement(
                By.xpath("//button[contains(text(),'Close')]")
        );
        js.executeScript("arguments[0].click();", closeButton);

        // Wait for modal to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));
        System.out.println("    Login modal closed");

        // 4. Click product via JavaScript
        System.out.println("  4. Clicking first product via JavaScript...");
        WebElement firstProductLink = driver.findElement(
                By.cssSelector(".card-title a")
        );
        String productName = firstProductLink.getText();
        System.out.println("    Product name: " + productName);

        js.executeScript("arguments[0].click();", firstProductLink);

        // Verify we're on product page
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2.name")
        ));
        System.out.println("    Navigated to product page");

        System.out.println("\n  Click via JavaScript test PASSED");
    }

    @Test(priority = 5)
    public void demonstrateInputViaJavaScript() {
        System.out.println("\n=== INPUT VIA JAVASCRIPT ON DEMOBLAZE ===");

        driver.get("https://www.demoblaze.com/");

        // 1. Open login modal
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login2"))
        );
        loginButton.click();

        WebElement loginModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("logInModal"))
        );
        System.out.println("  Login modal opened");

        // 2. Fill username via JavaScript
        System.out.println("  1. Filling username via JavaScript...");
        js.executeScript("document.getElementById('loginusername').value='js_test_user';");

        // Verify value was set
        String usernameValue = (String) js.executeScript(
                "return document.getElementById('loginusername').value;"
        );
        System.out.println("    Username value: " + usernameValue);
        Assert.assertEquals(usernameValue, "js_test_user");

        // 3. Fill password via JavaScript
        System.out.println("  2. Filling password via JavaScript...");
        js.executeScript("document.getElementById('loginpassword').value='js_test_pass';");

        String passwordValue = (String) js.executeScript(
                "return document.getElementById('loginpassword').value;"
        );
        System.out.println("    Password value: " + passwordValue);
        Assert.assertEquals(passwordValue, "js_test_pass");

        // 4. Clear fields via JavaScript
        System.out.println("  3. Clearing fields via JavaScript...");
        js.executeScript("document.getElementById('loginusername').value='';");
        js.executeScript("document.getElementById('loginpassword').value='';");

        // 5. Fill with different values
        System.out.println("  4. Filling with new values...");
        js.executeScript("""
            document.getElementById('loginusername').value = 'new_user_js';
            document.getElementById('loginpassword').value = 'new_pass_js';
        """);

        // Close modal
        WebElement closeButton = driver.findElement(
                By.xpath("//button[contains(text(),'Close')]")
        );
        closeButton.click();

        System.out.println("\n  Input via JavaScript test PASSED");
    }

    @Test(priority = 6)
    public void demonstrateElementManipulation() {
        System.out.println("\n=== ELEMENT MANIPULATION ON DEMOBLAZE ===");

        driver.get("https://www.demoblaze.com/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));

        // 1. Add custom element to page
        System.out.println("  1. Adding custom banner via JavaScript...");
        js.executeScript("""
            var banner = document.createElement('div');
            banner.id = 'selenium-banner';
            banner.innerHTML = '<h3 style="color: white; background-color: #007BFF; padding: 10px; text-align: center; margin: 0;"> Added by Selenium JavaScriptExecutor</h3>';
            banner.style.position = 'fixed';
            banner.style.top = '0';
            banner.style.left = '0';
            banner.style.width = '100%';
            banner.style.zIndex = '9999';
            document.body.prepend(banner);
        """);

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        // 2. Change navbar color
        System.out.println("  2. Changing navbar color via JavaScript...");
        js.executeScript("""
            var navbar = document.getElementById('nava');
            navbar.style.backgroundColor = '#343A40';
            navbar.style.color = 'white';
            navbar.style.padding = '10px';
        """);

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        // 3. Modify footer text
        System.out.println("  3. Modifying footer text via JavaScript...");
        js.executeScript("""
            var footer = document.querySelector('footer');
            if(footer) {
                var originalText = footer.innerHTML;
                footer.innerHTML = '<p>🚀 Modified by Selenium JS - ' + new Date().toLocaleTimeString() + '</p>' + originalText;
                footer.style.backgroundColor = '#17A2B8';
                footer.style.color = 'white';
                footer.style.padding = '20px';
                footer.style.textAlign = 'center';
            }
        """);

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        // 4. Add border to all product cards
        System.out.println("  4. Adding borders to product cards...");
        js.executeScript("""
            var cards = document.querySelectorAll('.card');
            for(var i = 0; i < cards.length; i++) {
                cards[i].style.border = '2px dashed #28A745';
                cards[i].style.boxShadow = '0 4px 8px rgba(0,0,0,0.1)';
            }
        """);

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        // 5. Remove custom banner
        System.out.println("  5. Removing custom banner...");
        js.executeScript("""
            var banner = document.getElementById('selenium-banner');
            if(banner) {
                banner.remove();
            }
        """);

        try { Thread.sleep(1500); } catch (InterruptedException e) { }

        System.out.println("\n  Element manipulation test PASSED");
    }

    @Test(priority = 7)
    public void demonstrateVisibilityAndDisplay() {
        System.out.println("\n=== ELEMENT VISIBILITY AND DISPLAY ON DEMOBLAZE ===");

        driver.get("https://www.demoblaze.com/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));

        // 1. Hide navbar temporarily
        System.out.println("  1. Hiding navbar...");
        WebElement navbar = driver.findElement(By.id("nava"));
        boolean navbarVisibleBefore = navbar.isDisplayed();
        System.out.println("    Navbar visible before: " + navbarVisibleBefore);

        js.executeScript("arguments[0].style.display = 'none';", navbar);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        boolean navbarVisibleAfter = navbar.isDisplayed();
        System.out.println("    Navbar visible after: " + navbarVisibleAfter);
        Assert.assertFalse(navbarVisibleAfter, "Navbar should be hidden");

        // 2. Show navbar again
        System.out.println("  2. Showing navbar again...");
        js.executeScript("arguments[0].style.display = 'block';", navbar);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // 3. Make element invisible but still in DOM
        System.out.println("  3. Making cart button invisible...");
        WebElement cartButton = driver.findElement(By.id("cartur"));
        js.executeScript("arguments[0].style.visibility = 'hidden';", cartButton);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // 4. Check if element exists in DOM even when invisible
        boolean existsInDOM = (Boolean) js.executeScript("""
            return document.getElementById('cartur') !== null;
        """);
        System.out.println("    Cart button exists in DOM: " + existsInDOM);
        Assert.assertTrue(existsInDOM, "Element should exist in DOM even when invisible");

        // 5. Make it visible again
        System.out.println("  4. Making cart button visible again...");
        js.executeScript("arguments[0].style.visibility = 'visible';", cartButton);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // 6. Check element opacity
        System.out.println("  5. Changing product card opacity...");
        WebElement firstProductCard = driver.findElement(By.cssSelector(".card"));
        js.executeScript("arguments[0].style.opacity = '0.5';", firstProductCard);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        js.executeScript("arguments[0].style.opacity = '1';", firstProductCard);

        System.out.println("\n  Visibility and display test PASSED");
    }

    @Test(priority = 8)
    public void demonstrateAttributeManipulation() {
        System.out.println("\n=== ATTRIBUTE MANIPULATION ON DEMOBLAZE ===");

        driver.get("https://www.demoblaze.com/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));

        // 1. Get and set attributes
        System.out.println("  1. Getting and setting attributes...");
        WebElement loginButton = driver.findElement(By.id("login2"));

        // Get original attributes
        String originalId = (String) js.executeScript(
                "return arguments[0].getAttribute('id');", loginButton
        );
        String originalClass = (String) js.executeScript(
                "return arguments[0].getAttribute('class');", loginButton
        );
        String originalText = (String) js.executeScript(
                "return arguments[0].innerText;", loginButton
        );

        System.out.println("    Original ID: " + originalId);
        System.out.println("    Original class: " + originalClass);
        System.out.println("    Original text: " + originalText);

        // 2. Set new attributes
        System.out.println("  2. Setting new attributes...");
        js.executeScript("""
            arguments[0].setAttribute('data-test', 'login-button-js');
            arguments[0].setAttribute('title', 'Click to login via JS');
            arguments[0].setAttribute('custom-attr', 'selenium-demo');
        """, loginButton);

        // 3. Get new attributes
        String testAttr = (String) js.executeScript(
                "return arguments[0].getAttribute('data-test');", loginButton
        );
        String titleAttr = (String) js.executeScript(
                "return arguments[0].getAttribute('title');", loginButton
        );
        String customAttr = (String) js.executeScript(
                "return arguments[0].getAttribute('custom-attr');", loginButton
        );

        System.out.println("    data-test: " + testAttr);
        System.out.println("    title: " + titleAttr);
        System.out.println("    custom-attr: " + customAttr);

        // 4. Remove attributes
        System.out.println("  3. Removing custom attributes...");
        js.executeScript("""
            arguments[0].removeAttribute('data-test');
            arguments[0].removeAttribute('custom-attr');
        """, loginButton);

        // 5. Check if removed
        testAttr = (String) js.executeScript(
                "return arguments[0].getAttribute('data-test');", loginButton
        );
        System.out.println("    data-test after removal: " + (testAttr == null ? "null" : testAttr));

        // 6. Change button text via innerHTML
        System.out.println("  4. Changing button text...");
        String originalHTML = (String) js.executeScript(
                "return arguments[0].innerHTML;", loginButton
        );
        System.out.println("    Original HTML: " + originalHTML);

        js.executeScript("arguments[0].innerHTML = '<strong>🔓 LOGIN JS</strong>';", loginButton);

        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // Change back
        js.executeScript("arguments[0].innerHTML = arguments[1];", loginButton, originalHTML);

        System.out.println("\n  Attribute manipulation test PASSED");
    }

    @Test(priority = 9)
    public void demonstrateAsyncJavaScript() {
        System.out.println("\n=== ASYNC JAVASCRIPT OPERATIONS ===");

        driver.get("https://www.demoblaze.com/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));

        // 1. Execute async JavaScript with callback
        System.out.println("  1. Executing async JavaScript with 2 second delay...");
        long startTime = System.currentTimeMillis();

        String result = (String) js.executeAsyncScript(
                "var callback = arguments[arguments.length - 1];" +
                        "setTimeout(function() {" +
                        "    var title = document.title;" +
                        "    callback('Async operation completed. Title: ' + title);" +
                        "}, 2000);"
        );

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("    Result: " + result);
        System.out.println("    Duration: " + duration + "ms (should be ~2000ms)");
        Assert.assertTrue(duration >= 2000, "Should wait at least 2000ms");

        // 2. Multiple async operations
        System.out.println("  2. Multiple async operations...");
        startTime = System.currentTimeMillis();

        result = (String) js.executeAsyncScript(
                "var callback = arguments[arguments.length - 1];" +
                        "var count = 0;" +
                        "var interval = setInterval(function() {" +
                        "    count++;" +
                        "    if(count >= 3) {" +
                        "        clearInterval(interval);" +
                        "        callback('Completed ' + count + ' intervals');" +
                        "    }" +
                        "}, 500);"
        );

        endTime = System.currentTimeMillis();
        duration = endTime - startTime;

        System.out.println("    Result: " + result);
        System.out.println("    Duration: " + duration + "ms (should be ~1500ms)");

        System.out.println("\n  Async JavaScript test PASSED");
    }

    @Test(priority = 10)
    public void demonstratePerformanceMetrics() {
        System.out.println("\n=== PERFORMANCE METRICS VIA JAVASCRIPT ===");

        driver.get("https://www.demoblaze.com/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));

        // 1. Get performance timing
        System.out.println("  1. Page load performance metrics:");

        // Navigation timing
        long navigationStart = (Long) js.executeScript(
                "return window.performance.timing.navigationStart;"
        );
        long loadEventEnd = (Long) js.executeScript(
                "return window.performance.timing.loadEventEnd;"
        );

        long pageLoadTime = loadEventEnd - navigationStart;
        System.out.println("    Page load time: " + pageLoadTime + "ms");

        // 2. Get memory usage (if supported)
        try {
            Object memory = js.executeScript("return window.performance.memory;");
            if (memory != null) {
                System.out.println("  2. Memory usage (if available):");
                System.out.println("    Memory info: " + memory.toString());
            }
        } catch (Exception e) {
            System.out.println("  2. Memory API not supported in this browser");
        }

        // 3. Count DOM elements
        long totalElements = (Long) js.executeScript(
                "return document.getElementsByTagName('*').length;"
        );
        System.out.println("  3. Total DOM elements: " + totalElements);

        // 4. Count by tag type
        long divCount = (Long) js.executeScript(
                "return document.getElementsByTagName('div').length;"
        );
        long imgCount = (Long) js.executeScript(
                "return document.getElementsByTagName('img').length;"
        );
        long linkCount = (Long) js.executeScript(
                "return document.getElementsByTagName('a').length;"
        );

        System.out.println("    Div elements: " + divCount);
        System.out.println("    Image elements: " + imgCount);
        System.out.println("    Link elements: " + linkCount);

        // 5. Calculate page weight (approximate)
        long pageWeight = (Long) js.executeScript(
                "return document.documentElement.innerHTML.length;"
        );
        double pageWeightKB = pageWeight / 1024.0;
        System.out.println("  4. Approximate page weight: " +
                String.format("%.2f", pageWeightKB) + " KB");

        System.out.println("\n  Performance metrics test PASSED");
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