package com.orangehrm.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    private WebDriver driver;

    private WebDriverWait wait;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public WaitUtils(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    // ============================================================
    // WAIT FOR VISIBILITY
    // ============================================================

    public WebElement waitForVisibility(
            By locator) {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        locator
                )
        );
    }

    // ============================================================
    // WAIT FOR CLICKABLE
    // ============================================================

    public WebElement waitForClickable(
            By locator) {

        return wait.until(
                ExpectedConditions.elementToBeClickable(
                        locator
                )
        );
    }

    // ============================================================
    // WAIT FOR PRESENCE
    // ============================================================

    public WebElement waitForPresence(
            By locator) {

        return wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        locator
                )
        );
    }

    // ============================================================
    // WAIT FOR INVISIBILITY
    // ============================================================

    public boolean waitForInvisibility(
            By locator) {

        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        locator
                )
        );
    }

    // ============================================================
    // WAIT FOR TEXT
    // ============================================================

    public boolean waitForText(
            By locator,
            String text) {

        return wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        locator,
                        text
                )
        );
    }

    // ============================================================
    // WAIT FOR URL
    // ============================================================

    public boolean waitForUrlContains(
            String urlPart) {

        return wait.until(
                ExpectedConditions.urlContains(
                        urlPart
                )
        );
    }

    // ============================================================
    // WAIT FOR PAGE LOAD
    // ============================================================

    public void waitForPageLoad() {

        wait.until(
                driver ->
                        ((JavascriptExecutor) driver)
                                .executeScript(
                                        "return document.readyState"
                                )
                                .equals("complete")
        );
    }

    // ============================================================
    // SCROLL INTO VIEW
    // ============================================================

    public void scrollIntoView(
            WebElement element) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView" +
                        "({block:'center'});",
                        element
                );
    }

    // ============================================================
    // CLICK WITH WAIT
    // ============================================================

    public void click(
            By locator) {

        WebElement element =
                waitForClickable(locator);

        scrollIntoView(element);

        element.click();
    }

    // ============================================================
    // TYPE WITH WAIT
    // ============================================================

    public void type(
            By locator,
            String text) {

        WebElement element =
                waitForVisibility(locator);

        element.clear();

        element.sendKeys(text);
    }

    // ============================================================
    // GET TEXT WITH WAIT
    // ============================================================

    public String getText(
            By locator) {

        return waitForVisibility(locator)
                .getText()
                .trim();
    }
}