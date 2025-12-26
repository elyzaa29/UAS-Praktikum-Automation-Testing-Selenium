package com.praktikum.testing.otomation.tests;

import com.praktikum.testing.otomation.utils.ScreenshotUtil;
import com.praktikum.testing.otomation.utils.TestDataGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.praktikum.testing.otomation.utils.ExtentReportManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;

import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup(Method method) {
        System.out.println("\n=== Starting test: " + method.getName() + " ===");

        // TAMBAH BARIS INI (1): Start test di Extent Report
        ExtentReportManager.getInstance();
        ExtentReportManager.createTest(method.getName());

        // Setup WebDriver (tetap sama)
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        System.out.println("Browser: Chrome");
        System.out.println("Window maximized");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // TAMBAH BARIS INI (2): Update status di Extent Report
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.getTest().fail("Test FAILED: " + result.getThrowable().getMessage());

            // Take screenshot
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver,
                    result.getName() + "_FAILED");
            System.out.println("Test FAILED - Screenshot: " + screenshotPath);

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReportManager.getTest().pass("Test PASSED ✓");
            System.out.println("Test PASSED ✓");
        } else {
            ExtentReportManager.getTest().skip("Test SKIPPED");
            System.out.println("Test SKIPPED");
        }

        // Close browser
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed");
        }

        System.out.println("=== Test finished ===\n");
    }

    // TAMBAH METHOD INI (3): Flush report di akhir suite
    @AfterSuite
    public void cleanupSuite() {
        // Cek apakah report sudah di-flush sebelumnya
        if (ExtentReportManager.getInstance() != null) {
            try {
                ExtentReportManager.getInstance().flush();
                System.out.println("Report saved to: test-output/ExtentReport.html");
                System.out.println("Open: file://" + System.getProperty("user.dir") + "/test-output/ExtentReport.html");
            } catch (Exception e) {
                System.out.println("⚠ Report already flushed");
            }
        }
    }
    // Helper method to get test data
    protected TestDataGenerator.TestUser getTestUser() {
        return TestDataGenerator.getNewTestUser();
    }

    // Navigate to Demoblaze - TAMBAH LOGGING KE REPORT (4)
    protected void goToDemoblaze() {
        driver.get("https://www.demoblaze.com/");
        System.out.println("Navigated to: https://www.demoblaze.com/");

        // TAMBAH BARIS INI: Log ke Extent Report
        ExtentReportManager.getTest().info("Navigated to Demoblaze homepage");
    }

    // TAMBAH METHOD INI (5): Helper untuk logging
    protected void logToReport(String message) {
        ExtentReportManager.getTest().info(message);
        System.out.println("LOG: " + message);
    }
}