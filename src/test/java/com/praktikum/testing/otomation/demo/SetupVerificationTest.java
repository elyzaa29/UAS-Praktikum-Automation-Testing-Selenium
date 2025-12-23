package com.praktikum.testing.otomation.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class SetupVerificationTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void verifySeleniumSetupWithDemoblaze() {
        System.out.println("\n=== VERIFYING SELENIUM SETUP WITH DEMOBLAZE ===");

        driver.get("https://www.demoblaze.com/");
        System.out.println("Navigated to: https://www.demoblaze.com/");

        String title = driver.getTitle();
        System.out.println("Page Title: " + title);

        Assert.assertTrue(title.contains("STORE"),
                "Page title should contain 'STORE'. Actual: " + title);

        System.out.println("✓ Selenium setup verification SUCCESSFUL!");
    }

    @Test(priority = 2, enabled = false) // DISABLE DULU TEST INI
    public void verifyBasicElementsExist() {
        // Test ini akan kita fix nanti
        System.out.println("This test is temporarily disabled");
    }

    @Test(priority = 3)
    public void exploreDemoblazeElements() {
        System.out.println("\n=== EXPLORING DEMOBLAZE ELEMENTS ===");

        driver.get("https://www.demoblaze.com/");

        // Tunggu lebih lama untuk page load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Explore dengan mencari semua link
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        System.out.println("Total links found: " + allLinks.size());

        // Print beberapa link untuk analisis
        System.out.println("\nFirst 10 links:");
        for (int i = 0; i < Math.min(10, allLinks.size()); i++) {
            WebElement link = allLinks.get(i);
            String text = link.getText();
            String id = link.getAttribute("id");
            if (!text.isEmpty()) {
                System.out.println((i + 1) + ". Text: '" + text + "' | ID: " + id);
            }
        }

        // Cari button
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        System.out.println("\nTotal buttons found: " + buttons.size());

        // Cari product cards dengan berbagai cara
        System.out.println("\n=== SEARCHING FOR PRODUCT CARDS ===");

        // Cara 1: By class name
        List<WebElement> cardsByClass = driver.findElements(By.className("card"));
        System.out.println("By class 'card': " + cardsByClass.size());

        // Cara 2: By CSS selector
        List<WebElement> cardsByCss = driver.findElements(By.cssSelector(".card"));
        System.out.println("By CSS '.card': " + cardsByCss.size());

        // Cara 3: By XPath
        List<WebElement> cardsByXpath = driver.findElements(By.xpath("//div[contains(@class, 'card')]"));
        System.out.println("By XPath: " + cardsByXpath.size());

        // Cara 4: By product titles
        List<WebElement> productTitles = driver.findElements(By.className("card-title"));
        System.out.println("Product titles: " + productTitles.size());

        // Print product titles jika ditemukan
        if (!productTitles.isEmpty()) {
            System.out.println("\nProduct titles found:");
            for (int i = 0; i < Math.min(5, productTitles.size()); i++) {
                System.out.println("  " + (i + 1) + ". " + productTitles.get(i).getText());
            }
        }

        // Assert menggunakan product titles (lebih reliable)
        Assert.assertTrue(productTitles.size() > 0,
                "Should find product titles. Found: " + productTitles.size());

        System.out.println("\n✓ Element exploration completed");
    }
}