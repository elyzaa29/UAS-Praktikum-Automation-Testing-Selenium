package com.praktikum.testing.otomation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Create screenshot directory if not exists
            String screenshotDir = "screenshots/";
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate timestamp
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Create file name
            String fileName = screenshotDir + testName + "_" + timeStamp + ".png";

            // Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Save to file
            File destination = new File(fileName);
            FileUtils.copyFile(source, destination);

            System.out.println("Screenshot saved: " + fileName);
            return fileName;

        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    public static String takeScreenshot(WebDriver driver) {
        return takeScreenshot(driver, "screenshot");
    }
}