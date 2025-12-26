package com.praktikum.testing.otomation.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Navigation and Windows Handling Demo untuk website Demoblaze.com
 * URL: https://www.demoblaze.com/
 * Demonstrasi berbagai teknik navigasi dan window handling
 */
public class NavigationAndWindowDemo {
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
    public void demonstrateBasicNavigation() {
        System.out.println("\n=== BASIC NAVIGATION COMMANDS ===");

        // 1. Navigate to homepage menggunakan driver.get()
        driver.get("https://www.demoblaze.com/");
        System.out.println("  1. Navigated to homepage using driver.get()");
        System.out.println("     Current URL: " + driver.getCurrentUrl());
        System.out.println("     Page Title: " + driver.getTitle());

        // Tunggu homepage fully loaded
        wait.until(ExpectedConditions.titleContains("STORE"));

        // 2. Navigate to product category menggunakan click
        WebElement phonesLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Phones"))
        );
        phonesLink.click();
        System.out.println("\n  2. Clicked 'Phones' category link");
        System.out.println("     Current URL: " + driver.getCurrentUrl());
        System.out.println("     Page Title: " + driver.getTitle());

        // Tunggu halaman category
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".card-title")
        ));

        // 3. Navigate back ke homepage
        driver.navigate().back();
        System.out.println("\n  3. Navigated back using navigate().back()");
        System.out.println("     Current URL: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("demoblaze"));

        // 4. Navigate forward ke category page
        driver.navigate().forward();
        System.out.println("\n  4. Navigated forward using navigate().forward()");
        System.out.println("     Current URL: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("#"));

        // 5. Refresh page
        driver.navigate().refresh();
        System.out.println("\n  5. Refreshed page using navigate().refresh()");
        System.out.println("     Page refreshed successfully");

        // 6. Navigate to specific URL menggunakan navigate().to()
        driver.navigate().to("https://www.demoblaze.com/index.html#");
        System.out.println("\n  6. Navigated to specific URL using navigate().to()");
        System.out.println("     Current URL: " + driver.getCurrentUrl());

        // 7. Navigate ke contact page
        WebElement contactLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Contact"))
        );
        contactLink.click();
        System.out.println("\n  7. Clicked 'Contact' link");
        System.out.println("     Current URL: " + driver.getCurrentUrl());

        // 8. Navigate ke about us
        driver.navigate().back();
        WebElement aboutLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("About us"))
        );
        aboutLink.click();
        System.out.println("\n  8. Clicked 'About us' link");
        System.out.println("     Current URL: " + driver.getCurrentUrl());

        System.out.println("\n✓ Basic navigation test PASSED");
    }

    @Test(priority = 2)
    public void demonstrateNavigationHistory() {
        System.out.println("\n=== NAVIGATION HISTORY MANAGEMENT ===");

        // Buka homepage
        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        // Simpan homepage URL
        String homepageUrl = driver.getCurrentUrl();
        System.out.println("  Step 1: Homepage - " + homepageUrl);

        // Navigate to Phones category
        driver.findElement(By.linkText("Phones")).click();
        wait.until(ExpectedConditions.urlContains("#"));
        String phonesUrl = driver.getCurrentUrl();
        System.out.println("  Step 2: Phones category - " + phonesUrl);

        // Navigate to Laptops category
        driver.findElement(By.linkText("Laptops")).click();
        wait.until(ExpectedConditions.urlContains("#"));
        String laptopsUrl = driver.getCurrentUrl();
        System.out.println("  Step 3: Laptops category - " + laptopsUrl);

        // Navigate to Monitors category
        driver.findElement(By.linkText("Monitors")).click();
        wait.until(ExpectedConditions.urlContains("#"));
        String monitorsUrl = driver.getCurrentUrl();
        System.out.println("  Step 4: Monitors category - " + monitorsUrl);

        // Sekarang kita ada di: Home → Phones → Laptops → Monitors

        // Navigate back 2 steps
        System.out.println("\n  Navigating back 2 steps...");
        driver.navigate().back(); // Back to Laptops
        System.out.println("    Back 1: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("laptops"));

        driver.navigate().back(); // Back to Phones
        System.out.println("    Back 2: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("phones"));

        // Navigate forward 1 step
        System.out.println("\n  Navigating forward 1 step...");
        driver.navigate().forward(); // Forward to Laptops
        System.out.println("    Forward 1: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("laptops"));

        // Navigate back to homepage menggunakan multiple back
        System.out.println("\n  Navigating back to homepage...");
        driver.navigate().back(); // Phones
        driver.navigate().back(); // Homepage
        System.out.println("    Current URL: " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(), homepageUrl);

        // Navigate langsung ke monitors menggunakan JavaScript
        System.out.println("\n  Navigating directly to monitors using JavaScript...");
        ((JavascriptExecutor) driver).executeScript(
                "window.location.href = 'https://www.demoblaze.com/index.html#';"
        );
        wait.until(ExpectedConditions.urlContains("#"));
        System.out.println("    Direct navigation to: " + driver.getCurrentUrl());

        System.out.println("\n✓ Navigation history test PASSED");
    }

    @Test(priority = 3)
    public void demonstrateMultipleWindowsHandling() {
        System.out.println("\n=== MULTIPLE WINDOWS HANDLING ===");

        // Buka homepage
        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        // Simpan window handle original
        String originalWindow = driver.getWindowHandle();
        System.out.println("  Original Window Handle: " + originalWindow);

        // Verifikasi hanya 1 window terbuka
        Set<String> allWindows = driver.getWindowHandles();
        System.out.println("  Number of windows open: " + allWindows.size());
        Assert.assertEquals(allWindows.size(), 1);

        // Buka link external di new window (jika ada)
        // Karena Demoblaze tidak punya link external, kita buka new window dengan JavaScript
        System.out.println("\n  Opening new window using JavaScript...");
        ((JavascriptExecutor) driver).executeScript(
                "window.open('https://www.google.com', '_blank');"
        );

        // Tunggu new window muncul
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Get all window handles
        allWindows = driver.getWindowHandles();
        System.out.println("  Total windows after opening new one: " + allWindows.size());

        // Switch ke new window
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                System.out.println("  Switched to new window: " + windowHandle);
                break;
            }
        }

        // Verifikasi kita di Google
        wait.until(ExpectedConditions.titleContains("Google"));
        System.out.println("  New window title: " + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("Google"));

        // Interact dengan Google
        WebElement googleSearchBox = driver.findElement(By.name("q"));
        googleSearchBox.sendKeys("Demoblaze automation testing");
        System.out.println("  Entered search query in Google");

        // Switch kembali ke original window
        driver.switchTo().window(originalWindow);
        System.out.println("\n  Switched back to original window");
        System.out.println("  Current title: " + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("STORE"));

        // Tutup new window
        System.out.println("\n  Switching back to close new window...");
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                driver.close();
                System.out.println("  Closed new window");
                break;
            }
        }

        // Switch kembali ke original window
        driver.switchTo().window(originalWindow);

        // Verifikasi hanya 1 window tersisa
        allWindows = driver.getWindowHandles();
        System.out.println("  Windows remaining: " + allWindows.size());
        Assert.assertEquals(allWindows.size(), 1);

        System.out.println("\n✓ Multiple windows handling test PASSED");
    }

    @Test(priority = 4)
    public void demonstrateTabsHandling() {
        System.out.println("\n=== MULTIPLE TABS HANDLING ===");

        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        String originalTab = driver.getWindowHandle();
        System.out.println("  Original Tab: " + originalTab);

        // Buka beberapa tabs dengan produk berbeda
        System.out.println("\n  Opening product pages in new tabs...");

        // Get semua produk
        List<WebElement> productLinks = driver.findElements(
                By.cssSelector(".card-title a")
        );

        System.out.println("  Found " + productLinks.size() + " products");

        // Buka 3 produk pertama di tab baru
        for (int i = 0; i < Math.min(3, productLinks.size()); i++) {
            String productName = productLinks.get(i).getText();

            // Buka di new tab menggunakan Ctrl+Click (Windows) atau Command+Click (Mac)
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL)
                    .click(productLinks.get(i))
                    .keyUp(Keys.CONTROL)
                    .build()
                    .perform();

            System.out.println("    Opened product in new tab: " + productName);

            // Tunggu sebentar antara klik
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }

        // Tunggu semua tab terbuka
        wait.until(ExpectedConditions.numberOfWindowsToBe(4)); // Original + 3 baru

        // Get semua tabs
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        System.out.println("\n  Total tabs open: " + tabs.size());

        // Iterate melalui semua tabs
        System.out.println("\n  Iterating through all tabs:");
        for (int i = 0; i < tabs.size(); i++) {
            driver.switchTo().window(tabs.get(i));
            String title = driver.getTitle();
            String url = driver.getCurrentUrl();

            System.out.println("    Tab " + (i+1) + ":");
            System.out.println("      Title: " + title);
            System.out.println("      URL: " + url);

            // Jika di product page, coba add to cart
            if (url.contains("prod.html")) {
                try {
                    WebElement addToCartBtn = driver.findElement(
                            By.linkText("Add to cart")
                    );
                    System.out.println("      Can add to cart: Yes");
                } catch (Exception e) {
                    System.out.println("      Can add to cart: No");
                }
            }
        }

        // Kembali ke original tab
        driver.switchTo().window(originalTab);
        System.out.println("\n  Returned to original tab");
        System.out.println("  Current title: " + driver.getTitle());

        // Tutup semua tabs kecuali original
        System.out.println("\n  Closing all other tabs...");
        for (String tab : tabs) {
            if (!tab.equals(originalTab)) {
                driver.switchTo().window(tab);
                driver.close();
            }
        }

        // Kembali ke original tab
        driver.switchTo().window(originalTab);

        // Verifikasi hanya 1 tab tersisa
        tabs = new ArrayList<>(driver.getWindowHandles());
        System.out.println("  Tabs remaining: " + tabs.size());
        Assert.assertEquals(tabs.size(), 1);

        System.out.println("\n✓ Multiple tabs handling test PASSED");
    }

    @Test(priority = 5)
    public void demonstrateNewWindowFeatures() {
        System.out.println("\n=== NEW WINDOW FEATURES ===");

        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        // 1. Open window dengan specific size
        System.out.println("\n  1. Opening new window with specific size...");

        // Simpan original window size
        Dimension originalSize = driver.manage().window().getSize();
        System.out.println("    Original window size: " + originalSize.getWidth() + "x" + originalSize.getHeight());

        // Buka new window
        ((JavascriptExecutor) driver).executeScript(
                "window.open('https://www.demoblaze.com/index.html#', 'newwindow', 'width=600,height=400');"
        );

        // Tunggu new window
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Switch ke new window
        Set<String> windows = driver.getWindowHandles();
        String originalWindow = driver.getWindowHandle();
        String newWindow = "";

        for (String window : windows) {
            if (!window.equals(originalWindow)) {
                newWindow = window;
                driver.switchTo().window(newWindow);
                break;
            }
        }

        // Set window size
        driver.manage().window().setSize(new Dimension(800, 600));
        Dimension newSize = driver.manage().window().getSize();
        System.out.println("    New window size: " + newSize.getWidth() + "x" + newSize.getHeight());

        // Set window position
        driver.manage().window().setPosition(new Point(100, 100));
        Point position = driver.manage().window().getPosition();
        System.out.println("    New window position: (" + position.getX() + "," + position.getY() + ")");

        // Maximize window
        driver.manage().window().maximize();
        System.out.println("    Maximized new window");

        // Minimize window (if supported)
        try {
            driver.manage().window().minimize();
            System.out.println("    Minimized new window");
            Thread.sleep(1000);
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("    Minimize not supported: " + e.getMessage());
        }

        // Fullscreen
        try {
            driver.manage().window().fullscreen();
            System.out.println("    Fullscreen mode activated");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("    Fullscreen not supported: " + e.getMessage());
        }

        // Kembali ke original window
        driver.switchTo().window(originalWindow);
        System.out.println("\n  2. Returned to original window");

        // Tutup new window
        driver.switchTo().window(newWindow);
        driver.close();
        driver.switchTo().window(originalWindow);

        System.out.println("\n✓ New window features test PASSED");
    }

    @Test(priority = 6)
    public void demonstrateModalWindows() {
        System.out.println("\n=== MODAL WINDOWS HANDLING ===");

        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        // 1. Contact Modal
        System.out.println("\n  1. Contact Modal:");
        WebElement contactLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Contact"))
        );
        contactLink.click();

        // Tunggu modal muncul
        WebElement contactModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal"))
        );
        System.out.println("    Contact modal displayed");

        // Verifikasi modal elements
        WebElement contactEmail = driver.findElement(By.id("recipient-email"));
        WebElement contactName = driver.findElement(By.id("recipient-name"));
        WebElement messageText = driver.findElement(By.id("message-text"));

        Assert.assertTrue(contactEmail.isDisplayed(), "Contact email field should be displayed");
        Assert.assertTrue(contactName.isDisplayed(), "Contact name field should be displayed");
        Assert.assertTrue(messageText.isDisplayed(), "Message field should be displayed");
        System.out.println("    All contact form elements verified");

        // Tutup modal
        WebElement closeButton = driver.findElement(
                By.xpath("//button[contains(text(),'Close')]")
        );
        closeButton.click();

        // Tunggu modal tertutup
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("exampleModal")));
        System.out.println("    Contact modal closed");

        // 2. About Us Modal
        System.out.println("\n  2. About Us Modal:");
        WebElement aboutLink = driver.findElement(By.linkText("About us"));
        aboutLink.click();

        WebElement aboutModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("videoModal"))
        );
        System.out.println("    About us modal displayed");

        // Verifikasi video player
        WebElement videoPlayer = driver.findElement(
                By.xpath("//video[@id='example-video']")
        );
        Assert.assertTrue(videoPlayer.isDisplayed(), "Video player should be displayed");
        System.out.println("    Video player verified");

        // Tutup modal
        closeButton = driver.findElement(
                By.xpath("(//button[contains(text(),'Close')])[2]")
        );
        closeButton.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("videoModal")));
        System.out.println("    About us modal closed");

        // 3. Login Modal
        System.out.println("\n  3. Login Modal:");
        WebElement loginLink = driver.findElement(By.id("login2"));
        loginLink.click();

        WebElement loginModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("logInModal"))
        );
        System.out.println("    Login modal displayed");

        // Isi form login
        WebElement usernameField = driver.findElement(By.id("loginusername"));
        WebElement passwordField = driver.findElement(By.id("loginpassword"));

        usernameField.sendKeys("testuser");
        passwordField.sendKeys("testpass");
        System.out.println("    Filled login form");

        // Tutup modal
        closeButton = driver.findElement(
                By.xpath("//button[contains(text(),'Close')]")
        );
        closeButton.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));
        System.out.println("    Login modal closed");

        // 4. Sign Up Modal
        System.out.println("\n  4. Sign Up Modal:");
        WebElement signupLink = driver.findElement(By.id("signin2"));
        signupLink.click();

        WebElement signupModal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("signInModal"))
        );
        System.out.println("    Sign up modal displayed");

        // Verifikasi form elements
        WebElement signupUsername = driver.findElement(By.id("sign-username"));
        WebElement signupPassword = driver.findElement(By.id("sign-password"));

        Assert.assertTrue(signupUsername.isDisplayed(), "Signup username field should be displayed");
        Assert.assertTrue(signupPassword.isDisplayed(), "Signup password field should be displayed");
        System.out.println("    Sign up form elements verified");

        // Tutup modal dengan Escape key
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("signInModal")));
        System.out.println("    Sign up modal closed with Escape key");

        System.out.println("\n✓ Modal windows handling test PASSED");
    }

    @Test(priority = 7)
    public void demonstrateBrowserNavigationState() {
        System.out.println("\n=== BROWSER NAVIGATION STATE ===");

        // Navigasi ke homepage
        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("  Initial state:");
        System.out.println("    URL: " + driver.getCurrentUrl());
        System.out.println("    Title: " + driver.getTitle());

        // Get page source length
        String pageSource = driver.getPageSource();
        System.out.println("    Page source length: " + pageSource.length() + " characters");

        // Navigate ke phones
        driver.findElement(By.linkText("Phones")).click();
        wait.until(ExpectedConditions.urlContains("#"));

        System.out.println("\n  After clicking Phones:");
        System.out.println("    URL: " + driver.getCurrentUrl());
        System.out.println("    Title: " + driver.getTitle());

        // Check if browser supports back/forward
        System.out.println("\n  Browser capabilities check:");

        // Execute JavaScript untuk check browser features
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Check if back/forward available
        Boolean canGoBack = (Boolean) js.executeScript("return window.history.length > 1");
        System.out.println("    Can go back: " + canGoBack);

        // Get history length
        Long historyLength = (Long) js.executeScript("return window.history.length");
        System.out.println("    History length: " + historyLength);

        // Get referrer
        String referrer = (String) js.executeScript("return document.referrer");
        System.out.println("    Referrer: " + (referrer.isEmpty() ? "None" : referrer));

        // Check if page fully loaded
        String readyState = (String) js.executeScript("return document.readyState");
        System.out.println("    Document ready state: " + readyState);

        // Get viewport size
        Long viewportWidth = (Long) js.executeScript("return window.innerWidth");
        Long viewportHeight = (Long) js.executeScript("return window.innerHeight");
        System.out.println("    Viewport size: " + viewportWidth + "x" + viewportHeight);

        // Get scroll position
        Long scrollX = (Long) js.executeScript("return window.scrollX");
        Long scrollY = (Long) js.executeScript("return window.scrollY");
        System.out.println("    Scroll position: (" + scrollX + ", " + scrollY + ")");

        // Scroll down
        js.executeScript("window.scrollTo(0, 500)");
        System.out.println("    Scrolled to position 500");

        // Get new scroll position
        scrollY = (Long) js.executeScript("return window.scrollY");
        System.out.println("    New scroll position Y: " + scrollY);

        System.out.println("\n✓ Browser navigation state test PASSED");
    }

    @Test(priority = 8)
    public void demonstrateCrossDomainNavigation() {
        System.out.println("\n=== CROSS-DOMAIN NAVIGATION ===");

        String originalWindow = driver.getWindowHandle();

        // 1. Navigate to Demoblaze
        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));
        System.out.println("  1. On Demoblaze: " + driver.getCurrentUrl());

        // 2. Open Google in new window
        ((JavascriptExecutor) driver).executeScript(
                "window.open('https://www.google.com', '_blank');"
        );
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Switch to Google window
        Set<String> windows = driver.getWindowHandles();
        String googleWindow = "";
        for (String window : windows) {
            if (!window.equals(originalWindow)) {
                googleWindow = window;
                driver.switchTo().window(googleWindow);
                break;
            }
        }

        wait.until(ExpectedConditions.titleContains("Google"));
        System.out.println("  2. Switched to Google: " + driver.getCurrentUrl());

        // Search something
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Demoblaze e-commerce");
        searchBox.sendKeys(Keys.RETURN);
        System.out.println("  3. Searched for 'Demoblaze e-commerce'");

        // 3. Open YouTube in another new window
        ((JavascriptExecutor) driver).executeScript(
                "window.open('https://www.youtube.com', '_blank');"
        );
        wait.until(ExpectedConditions.numberOfWindowsToBe(3));

        // Switch to YouTube window
        windows = driver.getWindowHandles();
        String youtubeWindow = "";
        for (String window : windows) {
            if (!window.equals(originalWindow) && !window.equals(googleWindow)) {
                youtubeWindow = window;
                driver.switchTo().window(youtubeWindow);
                break;
            }
        }

        wait.until(ExpectedConditions.titleContains("YouTube"));
        System.out.println("  4. Switched to YouTube: " + driver.getCurrentUrl());

        // 4. Navigate between domains
        System.out.println("\n  Navigating between domains:");

        // YouTube → Google
        driver.switchTo().window(googleWindow);
        System.out.println("    YouTube → Google: " + driver.getTitle());

        // Google → Demoblaze
        driver.switchTo().window(originalWindow);
        System.out.println("    Google → Demoblaze: " + driver.getTitle());

        // Demoblaze → YouTube
        driver.switchTo().window(youtubeWindow);
        System.out.println("    Demoblaze → YouTube: " + driver.getTitle());

        // 5. Close all external windows
        System.out.println("\n  Closing external windows...");

        // Close YouTube
        driver.close();
        System.out.println("    Closed YouTube window");

        // Switch to Google and close it
        driver.switchTo().window(googleWindow);
        driver.close();
        System.out.println("    Closed Google window");

        // Back to Demoblaze
        driver.switchTo().window(originalWindow);
        System.out.println("    Returned to Demoblaze: " + driver.getTitle());

        // Verify only Demoblaze remains
        windows = driver.getWindowHandles();
        System.out.println("    Windows remaining: " + windows.size());
        Assert.assertEquals(windows.size(), 1);

        System.out.println("\n✓ Cross-domain navigation test PASSED");
    }

    @Test(priority = 9)
    public void demonstrateNavigationErrorsHandling() {
        System.out.println("\n=== NAVIGATION ERRORS HANDLING ===");

        // 1. Try to navigate to non-existent page on same domain
        System.out.println("\n  1. Navigating to non-existent page...");
        try {
            driver.get("https://www.demoblaze.com/nonexistent-page.html");

            // Tunggu beberapa saat untuk melihat response
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String pageTitle = driver.getTitle();
            System.out.println("    Result: Page title - '" + pageTitle + "'");
            System.out.println("    Current URL: " + driver.getCurrentUrl());

            // Check berbagai kemungkinan error page
            if (pageTitle.contains("404") || pageTitle.contains("Error")
                    || pageTitle.contains("Not Found") || pageTitle.contains("Page Not Found")) {
                System.out.println("    Detected error page");
            } else {
                System.out.println("    Page loaded (might be custom error handling)");
            }

        } catch (Exception e) {
            System.out.println("    Result: Navigation failed - " + e.getMessage());

            // Navigate back to valid page
            driver.get("https://www.demoblaze.com/");
            wait.until(ExpectedConditions.titleContains("STORE"));
        }

        // 2. Try invalid URL format
        System.out.println("\n  2. Trying invalid URL format...");
        try {
            // Simpan current URL dulu
            String currentUrlBefore = driver.getCurrentUrl();

            try {
                driver.get("invalid://url.format");
                System.out.println("    Result: Should not reach here");
            } catch (Exception e) {
                System.out.println("    Result: Caught exception - " + e.getClass().getSimpleName() + ": " + e.getMessage());

                // Verifikasi kita masih di halaman sebelumnya
                System.out.println("    Still at URL: " + driver.getCurrentUrl());
            }

        } catch (Exception e) {
            System.out.println("    Unexpected error: " + e.getMessage());
        }

        // 3. Navigation timeout handling
        System.out.println("\n  3. Testing navigation timeout...");
        try {
            // Set very short timeout
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));

            // Try to load page that might be slow (gunakan URL yang diketahui lambat)
            long startTime = System.currentTimeMillis();
            try {
                driver.get("https://www.demoblaze.com/");
                long endTime = System.currentTimeMillis();
                System.out.println("    Result: Page loaded within " + (endTime - startTime) + "ms");
            } catch (TimeoutException e) {
                System.out.println("    Result: Page load timeout - " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("    Error in timeout test: " + e.getMessage());
        } finally {
            // Reset timeout
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        }

        // 4. Handle popup/prompt during navigation
        System.out.println("\n  4. Testing navigation with JavaScript alert...");
        try {
            // Navigate ke halaman valid dulu
            driver.get("https://www.demoblaze.com/");
            wait.until(ExpectedConditions.titleContains("STORE"));

            // Execute JavaScript that creates alert
            ((JavascriptExecutor) driver).executeScript("alert('Test Alert from JavaScript');");

            // Handle alert jika muncul
            try {
                WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                alertWait.until(ExpectedConditions.alertIsPresent());
                Alert alert = driver.switchTo().alert();
                System.out.println("    Alert text: " + alert.getText());
                alert.accept();
                System.out.println("    Alert accepted");
            } catch (TimeoutException e) {
                System.out.println("    No alert appeared within timeout");
            }

        } catch (Exception e) {
            System.out.println("    Error: " + e.getMessage());
        }

        // 5. Navigate to about:blank
        System.out.println("\n  5. Navigating to about:blank...");
        try {
            String previousUrl = driver.getCurrentUrl();
            driver.get("about:blank");

            System.out.println("    Previous URL: " + previousUrl);
            System.out.println("    Current URL: " + driver.getCurrentUrl());
            System.out.println("    Page title: '" + driver.getTitle() + "'");

            // Check page source
            String pageSource = driver.getPageSource();
            System.out.println("    Page source length: " + pageSource.length() + " characters");
            System.out.println("    Is page source empty? " + pageSource.trim().isEmpty());

        } catch (Exception e) {
            System.out.println("    Error navigating to about:blank: " + e.getMessage());
        }

        // 6. Test navigation with hash fragments
        System.out.println("\n  6. Testing navigation with hash fragments...");
        try {
            // Navigate dengan hash
            driver.get("https://www.demoblaze.com/#test-fragment");
            System.out.println("    URL with hash: " + driver.getCurrentUrl());

            // Tunggu page load
            wait.until(ExpectedConditions.titleContains("STORE"));
            System.out.println("    Page loaded with hash fragment");

        } catch (Exception e) {
            System.out.println("    Error with hash navigation: " + e.getMessage());
        }

        // 7. Test browser back/forward after error
        System.out.println("\n  7. Testing browser history after errors...");
        try {
            // Navigate ke beberapa halaman
            driver.get("https://www.demoblaze.com/");
            driver.findElement(By.linkText("Phones")).click();
            wait.until(ExpectedConditions.urlContains("#"));

            System.out.println("    Current URL: " + driver.getCurrentUrl());

            // Navigate ke URL yang mungkin error
            try {
                driver.get("https://www.demoblaze.com/invalid-page-12345");
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("    Got expected error: " + e.getClass().getSimpleName());
            }

            // Coba back
            System.out.println("    Attempting browser back...");
            driver.navigate().back();
            System.out.println("    After back: " + driver.getCurrentUrl());

            // Coba forward
            System.out.println("    Attempting browser forward...");
            driver.navigate().forward();
            System.out.println("    After forward: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("    Error in history test: " + e.getMessage());
        }

        // Kembali ke Demoblaze homepage untuk cleanup
        try {
            driver.get("https://www.demoblaze.com/");
            wait.until(ExpectedConditions.titleContains("STORE"));
            System.out.println("\n  Returned to Demoblaze homepage");
        } catch (Exception e) {
            System.out.println("\n  Error returning to homepage: " + e.getMessage());
        }

        System.out.println("\n✓ Navigation errors handling test PASSED");
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