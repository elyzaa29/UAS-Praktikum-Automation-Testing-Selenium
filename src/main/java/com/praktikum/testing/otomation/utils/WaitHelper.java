package com.praktikum.testing.otomation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitHelper {
    private WebDriver driver;
    private WebDriverWait wait;

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Wait for modal to appear (Demoblaze uses modals for login/signup)
    public void waitForModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("modal-content")));
    }

    public void waitForModalToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.className("modal-content")));
    }

    // Wait for products to load
    public void waitForProducts() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.className("card"), 0));
    }

    // Wait for specific text in element
    public void waitForTextInElement(WebElement element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    // Wait for URL to contain specific text
    public void waitForUrl(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    // Wait for element to be stale (useful after page refresh)
    public void waitForStaleness(WebElement element) {
        wait.until(ExpectedConditions.stalenessOf(element));
    }
}