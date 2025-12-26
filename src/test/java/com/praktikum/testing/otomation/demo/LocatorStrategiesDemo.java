package com.praktikum.testing.otomation.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

/**
 * Locator Strategies Demo untuk website Demoblaze.com
 * URL: https://www.demoblaze.com/
 * Demonstrasi berbagai strategi locator di Selenium WebDriver
 */
public class LocatorStrategiesDemo {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void demonstrateAllLocatorStrategies() {
        System.out.println("\n=== DEMONSTRATING ALL LOCATOR STRATEGIES ON DEMOBLAZE ===\n");

        // Navigate to Demoblaze homepage
        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("Navigated to Demoblaze homepage\n");

        // =================================================================
        // 1. LOCATOR BY ID - Most reliable and fastest
        // =================================================================
        System.out.println("1. LOCATOR BY ID:");
        System.out.println("   - Most reliable karena ID seharusnya unique");
        System.out.println("   - Fastest selector");
        System.out.println("   - Contoh di Demoblaze:");

        try {
            // Navbar ID
            WebElement navbarById = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("nava"))
            );
            System.out.println("   • Found navbar by ID 'nava': " + navbarById.getTagName());
            System.out.println("   • Navbar text: " + navbarById.getText());

            // Login button ID
            WebElement loginById = driver.findElement(By.id("login2"));
            System.out.println("   • Found login button by ID 'login2': " + loginById.getText());

            // Cart button ID
            WebElement cartById = driver.findElement(By.id("cartur"));
            System.out.println("   • Found cart button by ID 'cartur': " + cartById.getText());

            // Sign up button ID
            WebElement signupById = driver.findElement(By.id("signin2"));
            System.out.println("   • Found signup button by ID 'signin2': " + signupById.getText());

        } catch (Exception e) {
            System.out.println("  Error finding element by ID: " + e.getMessage());
        }
        System.out.println();

        // =================================================================
        // 2. LOCATOR BY NAME
        // =================================================================
        System.out.println("2. LOCATOR BY NAME:");
        System.out.println("   - Biasanya untuk form elements");
        System.out.println("   - Tidak semua element punya name attribute");

        try {
            // Search elements by name (jika ada)
            List<WebElement> elementsByName = driver.findElements(By.name("*"));
            System.out.println("   • Found " + elementsByName.size() + " elements with name attribute");

            // Jika tidak ada, coba cari di dalam modal login
            driver.findElement(By.id("login2")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));

            // Username field in login modal
            WebElement usernameByName = driver.findElement(By.name("loginusername"));
            System.out.println("   • Found username field by name 'loginusername': " +
                    usernameByName.getAttribute("type"));

            // Password field
            WebElement passwordByName = driver.findElement(By.name("loginpassword"));
            System.out.println("   • Found password field by name 'loginpassword': " +
                    passwordByName.getAttribute("type"));

            // Close modal
            driver.findElement(By.xpath("//button[contains(text(),'Close')]")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));

        } catch (Exception e) {
            System.out.println("   Error finding element by NAME: " + e.getMessage());
        }
        System.out.println();

        // =================================================================
        // 3. LOCATOR BY CLASS NAME
        // =================================================================
        System.out.println("3. LOCATOR BY CLASS NAME:");
        System.out.println("   - Sering digunakan untuk styling");
        System.out.println("   - Bisa multiple elements dengan class sama");

        try {
            // Product cards class
            List<WebElement> cardsByClass = driver.findElements(By.className("card"));
            System.out.println("   • Found " + cardsByClass.size() + " product cards by class 'card'");

            if (!cardsByClass.isEmpty()) {
                System.out.println("   • First card tag: " + cardsByClass.get(0).getTagName());
                System.out.println("   • First card classes: " + cardsByClass.get(0).getAttribute("class"));
            }

            // Card block class
            List<WebElement> cardBlocks = driver.findElements(By.className("card-block"));
            System.out.println("   • Found " + cardBlocks.size() + " card blocks by class 'card-block'");

            // Card title class
            List<WebElement> cardTitles = driver.findElements(By.className("card-title"));
            System.out.println("   • Found " + cardTitles.size() + " card titles by class 'card-title'");

            if (!cardTitles.isEmpty()) {
                System.out.println("   • First product title: " + cardTitles.get(0).getText());
            }

            // Price class
            List<WebElement> prices = driver.findElements(By.className("card-text"));
            System.out.println("   • Found " + prices.size() + " price elements by class 'card-text'");

        } catch (Exception e) {
            System.out.println("    Error finding element by CLASS NAME: " + e.getMessage());
        }
        System.out.println();

        // =================================================================
        // 4. LOCATOR BY TAG NAME
        // =================================================================
        System.out.println("4. LOCATOR BY TAG NAME:");
        System.out.println("   - Generic selector berdasarkan HTML tag");
        System.out.println("   - Banyak hasil, perlu filter lebih lanjut");

        try {
            // All links on page
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            System.out.println("   • Found " + allLinks.size() + " links (<a> tags) on page");

            // All images
            List<WebElement> allImages = driver.findElements(By.tagName("img"));
            System.out.println("   • Found " + allImages.size() + " images (<img> tags) on page");

            // All headings
            List<WebElement> allHeadings = driver.findElements(By.tagName("h"));
            System.out.println("   • Found " + allHeadings.size() + " headings (<h1>-<h6> tags) on page");

            // Get specific headings
            List<WebElement> h1Elements = driver.findElements(By.tagName("h1"));
            List<WebElement> h2Elements = driver.findElements(By.tagName("h2"));
            List<WebElement> h3Elements = driver.findElements(By.tagName("h3"));
            List<WebElement> h4Elements = driver.findElements(By.tagName("h4"));
            List<WebElement> h5Elements = driver.findElements(By.tagName("h5"));

            System.out.println("   • Breakdown by heading level:");
            System.out.println("     - h1: " + h1Elements.size());
            System.out.println("     - h2: " + h2Elements.size());
            System.out.println("     - h3: " + h3Elements.size());
            System.out.println("     - h4: " + h4Elements.size());
            System.out.println("     - h5: " + h5Elements.size());

            // All paragraphs
            List<WebElement> allParagraphs = driver.findElements(By.tagName("p"));
            System.out.println("   • Found " + allParagraphs.size() + " paragraphs (<p> tags) on page");

            // All divs
            List<WebElement> allDivs = driver.findElements(By.tagName("div"));
            System.out.println("   • Found " + allDivs.size() + " divs (<div> tags) on page");

        } catch (Exception e) {
            System.out.println("   ❌ Error finding element by TAG NAME: " + e.getMessage());
        }
        System.out.println();

        // =================================================================
        // 5. LOCATOR BY LINK TEXT
        // =================================================================
        System.out.println("5. LOCATOR BY LINK TEXT:");
        System.out.println("   - Exact match dengan teks link");
        System.out.println("   - Case-sensitive");

        try {
            // Navigasi link
            WebElement homeLink = driver.findElement(By.linkText("Home"));
            System.out.println("   • Found 'Home' link: " + homeLink.getAttribute("href"));

            WebElement contactLink = driver.findElement(By.linkText("Contact"));
            System.out.println("   • Found 'Contact' link: " + contactLink.getAttribute("href"));

            WebElement aboutLink = driver.findElement(By.linkText("About us"));
            System.out.println("   • Found 'About us' link: " + aboutLink.getAttribute("href"));

            // Product category links
            WebElement phonesLink = driver.findElement(By.linkText("Phones"));
            System.out.println("   • Found 'Phones' link: " + phonesLink.getAttribute("href"));

            WebElement laptopsLink = driver.findElement(By.linkText("Laptops"));
            System.out.println("   • Found 'Laptops' link: " + laptopsLink.getAttribute("href"));

            WebElement monitorsLink = driver.findElement(By.linkText("Monitors"));
            System.out.println("   • Found 'Monitors' link: " + monitorsLink.getAttribute("href"));

            // Cari link dengan teks spesifik
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            System.out.println("   • Total links found: " + allLinks.size());
            System.out.println("   • Sample link texts:");
            for (int i = 0; i < Math.min(5, allLinks.size()); i++) {
                String linkText = allLinks.get(i).getText();
                if (!linkText.trim().isEmpty()) {
                    System.out.println("     - '" + linkText + "'");
                }
            }

        } catch (Exception e) {
            System.out.println("    Error finding element by LINK TEXT: " + e.getMessage());
        }
        System.out.println();

        // =================================================================
        // 6. LOCATOR BY PARTIAL LINK TEXT
        // =================================================================
        System.out.println("6. LOCATOR BY PARTIAL LINK TEXT:");
        System.out.println("   - Partial match dengan teks link");
        System.out.println("   - Berguna ketika teks dinamis");

        try {
            // Cari link dengan partial text
            WebElement partialHome = driver.findElement(By.partialLinkText("Home"));
            System.out.println("   • Found link containing 'Home': " + partialHome.getText());

            WebElement partialCont = driver.findElement(By.partialLinkText("Cont"));
            System.out.println("   • Found link containing 'Cont': " + partialCont.getText());

            WebElement partialAbout = driver.findElement(By.partialLinkText("About"));
            System.out.println("   • Found link containing 'About': " + partialAbout.getText());

            // Product categories partial
            WebElement partialPhone = driver.findElement(By.partialLinkText("Phone"));
            System.out.println("   • Found link containing 'Phone': " + partialPhone.getText());

            WebElement partialLaptop = driver.findElement(By.partialLinkText("Laptop"));
            System.out.println("   • Found link containing 'Laptop': " + partialLaptop.getText());

            WebElement partialMonitor = driver.findElement(By.partialLinkText("Monitor"));
            System.out.println("   • Found link containing 'Monitor': " + partialMonitor.getText());

            // Cari "Add to cart" link di detail produk
            driver.findElement(By.cssSelector(".card-title a")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("h2.name")
            ));

            WebElement addToCartPartial = driver.findElement(By.partialLinkText("Add to"));
            System.out.println("   • Found 'Add to cart' link containing 'Add to': " +
                    addToCartPartial.getText());

            // Kembali ke homepage
            driver.navigate().back();
            wait.until(ExpectedConditions.titleContains("STORE"));

        } catch (Exception e) {
            System.out.println("   Error finding element by PARTIAL LINK TEXT: " + e.getMessage());
        }
        System.out.println();

        // =================================================================
        // 7. LOCATOR BY CSS SELECTOR
        // =================================================================
        System.out.println("7. LOCATOR BY CSS SELECTOR:");
        System.out.println("   - Sangat powerful dan flexible");
        System.out.println("   - Support semua CSS selector patterns");
        System.out.println("   - Recommended untuk complex scenarios");

        try {
            // CSS by ID
            System.out.println("   • CSS by ID (#):");
            WebElement navByCssId = driver.findElement(By.cssSelector("#nava"));
            System.out.println("     - #nava (navbar): " + navByCssId.getTagName());

            WebElement loginByCssId = driver.findElement(By.cssSelector("#login2"));
            System.out.println("     - #login2 (login): " + loginByCssId.getText());

            // CSS by Class (.)
            System.out.println("   • CSS by Class (.):");
            List<WebElement> cardsByCssClass = driver.findElements(By.cssSelector(".card"));
            System.out.println("     - .card (product cards): " + cardsByCssClass.size() + " found");

            List<WebElement> titlesByCssClass = driver.findElements(By.cssSelector(".card-title"));
            System.out.println("     - .card-title (product titles): " + titlesByCssClass.size() + " found");

            // CSS by Tag
            System.out.println("   • CSS by Tag:");
            List<WebElement> linksByCssTag = driver.findElements(By.cssSelector("a"));
            System.out.println("     - a (links): " + linksByCssTag.size() + " found");

            // CSS by Attribute
            System.out.println("   • CSS by Attribute:");
            List<WebElement> elementsWithId = driver.findElements(By.cssSelector("[id]"));
            System.out.println("     - [id] (elements with id): " + elementsWithId.size() + " found");

            List<WebElement> elementsWithClass = driver.findElements(By.cssSelector("[class]"));
            System.out.println("     - [class] (elements with class): " + elementsWithClass.size() + " found");

            // CSS with multiple conditions
            System.out.println("   • CSS with multiple conditions:");
            WebElement firstProductLink = driver.findElement(
                    By.cssSelector(".card .card-title a")
            );
            System.out.println("     - .card .card-title a (first product link): " +
                    firstProductLink.getText());

            // CSS :nth-child selector
            System.out.println("   • CSS :nth-child selector:");
            WebElement secondCard = driver.findElement(
                    By.cssSelector(".card:nth-child(2)")
            );
            System.out.println("     - .card:nth-child(2) (second card): found");

            // CSS :first-child selector
            WebElement firstCard = driver.findElement(
                    By.cssSelector(".card:first-child")
            );
            System.out.println("     - .card:first-child (first card): found");

            // CSS :last-child selector
            WebElement lastCard = driver.findElement(
                    By.cssSelector(".card:last-child")
            );
            System.out.println("     - .card:last-child (last card): found");

            // CSS :contains (jQuery-style, tidak support di semua browser)
            System.out.println("   • Advanced CSS selectors:");
            // Cari element dengan text tertentu menggunakan XPath lebih baik

        } catch (Exception e) {
            System.out.println("   Error finding element by CSS SELECTOR: " + e.getMessage());
        }
        System.out.println();

        // =================================================================
        // 8. LOCATOR BY XPATH
        // =================================================================
        System.out.println("8. LOCATOR BY XPATH:");
        System.out.println("   - Most powerful but slower");
        System.out.println("   - Recommended untuk complex relationships");
        System.out.println("   - Gunakan relative XPath, hindari absolute");

        try {
            // Relative XPath by attribute
            System.out.println("   • Relative XPath by attribute:");
            WebElement navbarByXpath = driver.findElement(
                    By.xpath("//nav[@id='nava']")
            );
            System.out.println("     - //nav[@id='nava'] (navbar): found");

            WebElement loginByXpath = driver.findElement(
                    By.xpath("//a[@id='login2']")
            );
            System.out.println("     - //a[@id='login2'] (login button): " + loginByXpath.getText());

            // XPath by text
            System.out.println("   • XPath by text:");
            WebElement homeByText = driver.findElement(
                    By.xpath("//a[text()='Home']")
            );
            System.out.println("     - //a[text()='Home']: " + homeByText.getAttribute("href"));

            WebElement phonesByText = driver.findElement(
                    By.xpath("//a[contains(text(),'Phones')]")
            );
            System.out.println("     - //a[contains(text(),'Phones')]: " + phonesByText.getAttribute("href"));

            // XPath with parent/child relationships
            System.out.println("   • XPath with parent/child:");
            WebElement productInCard = driver.findElement(
                    By.xpath("//div[@class='card']//a[@class='hrefch']")
            );
            System.out.println("     - //div[@class='card']//a[@class='hrefch'] (product in card): " +
                    productInCard.getText());

            // XPath with index
            System.out.println("   • XPath with index:");
            WebElement firstProductXpath = driver.findElement(
                    By.xpath("(//div[@class='card'])[1]")
            );
            System.out.println("     - (//div[@class='card'])[1] (first product card): found");

            WebElement secondProductXpath = driver.findElement(
                    By.xpath("(//div[@class='card'])[2]")
            );
            System.out.println("     - (//div[@class='card'])[2] (second product card): found");

            // XPath with multiple conditions
            System.out.println("   • XPath with multiple conditions:");
            WebElement cardWithLink = driver.findElement(
                    By.xpath("//div[@class='card' and .//a]")
            );
            System.out.println("     - //div[@class='card' and .//a] (card with link): found");

            // XPath with starts-with
            System.out.println("   • XPath with starts-with:");
            List<WebElement> elementsStartingWith = driver.findElements(
                    By.xpath("//*[starts-with(@id, 'na')]")
            );
            System.out.println("     - //*[starts-with(@id, 'na')] (elements with id starting with 'na'): " +
                    elementsStartingWith.size() + " found");

            // XPath with following-sibling
            System.out.println("   • XPath with axis:");
            try {
                WebElement followingSibling = driver.findElement(
                        By.xpath("//a[@id='login2']/following-sibling::a")
                );
                System.out.println("     - //a[@id='login2']/following-sibling::a: " +
                        followingSibling.getAttribute("id"));
            } catch (Exception e) {
                System.out.println("     - No following sibling found");
            }

            // XPath with ancestor
            WebElement ancestorDiv = driver.findElement(
                    By.xpath("//a[@id='login2']/ancestor::div")
            );
            System.out.println("     - //a[@id='login2']/ancestor::div: found with class '" +
                    ancestorDiv.getAttribute("class") + "'");

        } catch (Exception e) {
            System.out.println("   ❌ Error finding element by XPATH: " + e.getMessage());
        }
        System.out.println();

        System.out.println("=== ALL LOCATOR STRATEGIES DEMONSTRATED SUCCESSFULLY ===");
    }

    @Test
    public void demonstrateLocatorBestPractices() {
        System.out.println("\n=== LOCATOR BEST PRACTICES FOR DEMOBLAZE ===\n");

        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("BEST PRACTICE 1: Always prefer ID if available");
        System.out.println("  • ID should be unique and fastest to locate");

        try {
            // Example: Using ID for navbar
            WebElement navbar = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("nava"))
            );
            System.out.println("  ✓ Navbar found by ID 'nava': " + navbar.getTagName());
        } catch (Exception e) {
            System.out.println("  ✗ Navbar not found by ID");
        }

        System.out.println("\nBEST PRACTICE 2: Use CSS Selector for complex scenarios");
        System.out.println("  • More readable than XPath");
        System.out.println("  • Faster in most browsers");

        try {
            // Find first product using CSS selector
            WebElement firstProduct = driver.findElement(
                    By.cssSelector(".card .card-title a")
            );
            System.out.println("  ✓ First product found by CSS: " + firstProduct.getText());

            // Find all product cards
            List<WebElement> allProducts = driver.findElements(
                    By.cssSelector(".card")
            );
            System.out.println("  ✓ Found " + allProducts.size() + " products using CSS");

        } catch (Exception e) {
            System.out.println("  ✗ Error with CSS selector: " + e.getMessage());
        }

        System.out.println("\nBEST PRACTICE 3: Avoid absolute XPath");
        System.out.println("  • Absolute XPath is brittle and breaks easily");
        System.out.println("  • Example of BAD practice:");
        System.out.println("    /html/body/nav/div[1]/a");

        System.out.println("\nBEST PRACTICE 4: Use relative XPath when needed");
        System.out.println("  • More robust than absolute");
        System.out.println("  • Good for complex relationships");

        try {
            // Good: Relative XPath
            WebElement loginButton = driver.findElement(
                    By.xpath("//a[@id='login2']")
            );
            System.out.println("  ✓ Login button found by relative XPath: " + loginButton.getText());

            // Find product with specific text
            WebElement phonesLink = driver.findElement(
                    By.xpath("//a[contains(text(),'Phones')]")
            );
            System.out.println("  ✓ Phones link found by text XPath: " + phonesLink.getText());

        } catch (Exception e) {
            System.out.println("  ✗ Error with XPath: " + e.getMessage());
        }

        System.out.println("\nBEST PRACTICE 5: Combine strategies when necessary");
        System.out.println("  • Sometimes need multiple conditions");

        try {
            // Find card that contains specific product
            WebElement specificCard = driver.findElement(
                    By.xpath("//div[@class='card' and .//a[contains(text(),'Samsung')]]")
            );
            System.out.println("  ✓ Found card containing Samsung product");

            // Multiple attribute conditions
            WebElement element = driver.findElement(
                    By.cssSelector("a[id='login2'][data-target='#logInModal']")
            );
            System.out.println("  ✓ Found element with multiple attributes");

        } catch (Exception e) {
            System.out.println("  ✗ No card with specific product found");
        }

        System.out.println("\nBEST PRACTICE 6: Use explicit waits for dynamic content");
        System.out.println("  • Demoblaze uses modals and dynamic content");

        try {
            // Click login to open modal
            driver.findElement(By.id("login2")).click();

            // Wait for modal to appear
            WebElement loginModal = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("logInModal"))
            );
            System.out.println("  ✓ Login modal appeared after explicit wait");

            // Find elements inside modal
            WebElement usernameField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))
            );
            System.out.println("  ✓ Username field found inside modal");

            // Close modal
            driver.findElement(By.xpath("//button[contains(text(),'Close')]")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));

        } catch (Exception e) {
            System.out.println("  ✗ Error with explicit waits: " + e.getMessage());
        }

        System.out.println("\nBEST PRACTICE 7: Handle multiple elements with same locator");

        try {
            // Get all product titles
            List<WebElement> productTitles = driver.findElements(
                    By.cssSelector(".card-title")
            );

            System.out.println("  • Found " + productTitles.size() + " product titles");
            System.out.println("  • First 3 products:");

            for (int i = 0; i < Math.min(3, productTitles.size()); i++) {
                System.out.println("    " + (i+1) + ". " + productTitles.get(i).getText());
            }

        } catch (Exception e) {
            System.out.println("  ✗ Error handling multiple elements: " + e.getMessage());
        }

        System.out.println("\nBEST PRACTICE 8: Create reusable locator variables");
        System.out.println("  • Better maintainability");
        System.out.println("  • Example in Page Object Model:");
        System.out.println("    private By loginButton = By.id(\"login2\");");
        System.out.println("    private By productCards = By.cssSelector(\".card\");");

        System.out.println("\nBEST PRACTICE 9: Test locators in browser console first");
        System.out.println("  • Chrome DevTools: F12 → Console");
        System.out.println("  • Test CSS: $$(\".card\")");
        System.out.println("  • Test XPath: $x(\"//div[@class='card']\")");

        System.out.println("\nBEST PRACTICE 10: Prioritize readability over cleverness");
        System.out.println("  • Clear locators are easier to maintain");
        System.out.println("  • Comment complex locators");

        System.out.println("\n=== BEST PRACTICES DEMONSTRATED ===");
    }

    @Test
    public void demonstrateLocatorComparison() {
        System.out.println("\n=== LOCATOR COMPARISON FOR COMMON ELEMENTS ===\n");

        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("Comparing different locator strategies for the same element:\n");

        // Element 1: Login Button
        System.out.println("1. LOGIN BUTTON:");
        System.out.println("   • By ID: By.id(\"login2\")");
        System.out.println("   • By CSS: By.cssSelector(\"#login2\")");
        System.out.println("   • By XPath: By.xpath(\"//a[@id='login2']\")");
        System.out.println("   • By Link Text: By.linkText(\"Log in\")");

        try {
            long startTime, endTime;

            // Test ID locator speed
            startTime = System.currentTimeMillis();
            WebElement byId = driver.findElement(By.id("login2"));
            endTime = System.currentTimeMillis();
            System.out.println("   ✓ By.id() - Text: \"" + byId.getText() + "\" (" + (endTime-startTime) + "ms)");

            // Test CSS locator speed
            startTime = System.currentTimeMillis();
            WebElement byCss = driver.findElement(By.cssSelector("#login2"));
            endTime = System.currentTimeMillis();
            System.out.println("   ✓ By.cssSelector() - Text: \"" + byCss.getText() + "\" (" + (endTime-startTime) + "ms)");

            // Test XPath locator speed
            startTime = System.currentTimeMillis();
            WebElement byXpath = driver.findElement(By.xpath("//a[@id='login2']"));
            endTime = System.currentTimeMillis();
            System.out.println("   ✓ By.xpath() - Text: \"" + byXpath.getText() + "\" (" + (endTime-startTime) + "ms)");

        } catch (Exception e) {
            System.out.println("   ✗ Error: " + e.getMessage());
        }

        System.out.println("\n2. PRODUCT CARDS:");
        System.out.println("   • By Class Name: By.className(\"card\")");
        System.out.println("   • By CSS: By.cssSelector(\".card\")");
        System.out.println("   • By XPath: By.xpath(\"//div[@class='card']\")");
        System.out.println("   • By Tag with Class: By.cssSelector(\"div.card\")");

        try {
            long startTime, endTime;

            // Test Class Name
            startTime = System.currentTimeMillis();
            List<WebElement> byClassName = driver.findElements(By.className("card"));
            endTime = System.currentTimeMillis();
            System.out.println("   ✓ By.className() - Found: " + byClassName.size() + " (" + (endTime-startTime) + "ms)");

            // Test CSS
            startTime = System.currentTimeMillis();
            List<WebElement> byCss = driver.findElements(By.cssSelector(".card"));
            endTime = System.currentTimeMillis();
            System.out.println("   ✓ By.cssSelector() - Found: " + byCss.size() + " (" + (endTime-startTime) + "ms)");

            // Test XPath
            startTime = System.currentTimeMillis();
            List<WebElement> byXpath = driver.findElements(By.xpath("//div[@class='card']"));
            endTime = System.currentTimeMillis();
            System.out.println("   ✓ By.xpath() - Found: " + byXpath.size() + " (" + (endTime-startTime) + "ms)");

        } catch (Exception e) {
            System.out.println("   ✗ Error: " + e.getMessage());
        }

        System.out.println("\n3. FIRST PRODUCT LINK:");
        System.out.println("   • Complex CSS: By.cssSelector(\".card .card-title a\")");
        System.out.println("   • Complex XPath: By.xpath(\"//div[@class='card']//a[@class='hrefch']\")");

        try {
            driver.findElement(By.cssSelector(".card .card-title a")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("h2.name")
            ));

            System.out.println("\n4. ADD TO CART BUTTON (on product page):");
            System.out.println("   • By Link Text: By.linkText(\"Add to cart\")");
            System.out.println("   • By Partial Link: By.partialLinkText(\"Add to\")");
            System.out.println("   • By XPath Text: By.xpath(\"//a[contains(text(),'Add to cart')]\")");

            WebElement addToCart = driver.findElement(By.linkText("Add to cart"));
            System.out.println("   ✓ Add to cart button found: " + addToCart.getText());

            // Navigate back
            driver.navigate().back();
            wait.until(ExpectedConditions.titleContains("STORE"));

        } catch (Exception e) {
            System.out.println("   ✗ Error: " + e.getMessage());
            // Try to navigate back if error
            try { driver.navigate().back(); } catch (Exception ex) {}
        }

        System.out.println("\n=== LOCATOR COMPARISON COMPLETED ===");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(2000); // Pause untuk melihat hasil
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("\nBrowser closed");
        }
    }
}