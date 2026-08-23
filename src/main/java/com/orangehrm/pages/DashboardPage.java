package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.utils.WaitUtils;

public class DashboardPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // =====================================================
    // DASHBOARD HEADING
    // =====================================================

    private By dashboardHeading =
            By.xpath("//h6[normalize-space()='Dashboard']");

    // =====================================================
    // PIM MENU
    // =====================================================

    private By pimMenu =
            By.xpath("//span[normalize-space()='PIM']");

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public DashboardPage(WebDriver driver) {

        this.driver = driver;

        this.waitUtils =
                new WaitUtils(driver);
    }

    // =====================================================
    // VERIFY DASHBOARD
    // =====================================================

    public boolean isDashboardDisplayed() {

        try {

            return waitUtils
                    .waitForVisibility(dashboardHeading)
                    .isDisplayed();

        } catch (Exception e) {

            System.out.println(
                    "Dashboard heading not displayed."
            );

            return false;
        }
    }

    // =====================================================
    // CLICK PIM
    // =====================================================

    public void clickPIM() {

        waitUtils
                .waitForClickable(pimMenu)
                .click();

        System.out.println(
                "PIM menu clicked successfully."
        );
    }
}