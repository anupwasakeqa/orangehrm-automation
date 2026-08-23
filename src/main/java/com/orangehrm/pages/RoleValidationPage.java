package com.orangehrm.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RoleValidationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public RoleValidationPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    // ============================================================
    // LOCATORS
    // ============================================================

    private By adminMenu = By.xpath(
            "//a[contains(@href,'/admin/viewAdminModule')]"
    );

    private By userManagementMenu = By.xpath(
            "//span[normalize-space()='User Management']"
    );

    private By usersMenu = By.xpath(
            "//a[normalize-space()='Users']"
    );

    private By usernameInput = By.xpath(
            "//label[normalize-space()='Username']" +
            "/ancestor::div[contains(@class,'oxd-input-group')]" +
            "//input"
    );

    private By searchButton = By.xpath(
            "//button[normalize-space()='Search']"
    );

    private By resetButton = By.xpath(
            "//button[normalize-space()='Reset']"
    );

    private By tableRows = By.xpath(
            "//div[contains(@class,'oxd-table-body')]" +
            "//div[contains(@class,'oxd-table-row')]"
    );

    // ============================================================
    // NAVIGATION
    // ============================================================

    public void openAdminModule() {

        WebElement admin = wait.until(
                ExpectedConditions.elementToBeClickable(adminMenu)
        );

        admin.click();
    }

    public void openUserManagement() {

        WebElement userManagement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        userManagementMenu
                )
        );

        userManagement.click();
    }

    public void openUsers() {

        WebElement users = wait.until(
                ExpectedConditions.elementToBeClickable(
                        usersMenu
                )
        );

        users.click();
    }

    // ============================================================
    // SEARCH USER
    // ============================================================

    public void searchUser(String username) {

        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        usernameInput
                )
        );

        usernameField.clear();

        usernameField.sendKeys(username);

        WebElement search = wait.until(
                ExpectedConditions.elementToBeClickable(
                        searchButton
                )
        );

        search.click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        tableRows
                )
        );
    }

    // ============================================================
    // VERIFY USER EXISTS
    // ============================================================

    public boolean isUserDisplayed(String username) {

        By userRow = By.xpath(
                "//div[contains(@class,'oxd-table-body')]" +
                "//div[contains(@class,'oxd-table-row')]" +
                "[.//div[contains(@class,'oxd-table-cell')]" +
                "[normalize-space()='" + username + "']]"
        );

        try {

            WebElement row = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            userRow
                    )
            );

            return row.isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // ============================================================
    // GET USER ROLE
    // ============================================================

    public String getUserRole(String username) {

        By userRoleCell = By.xpath(
                "//div[contains(@class,'oxd-table-body')]" +
                "//div[contains(@class,'oxd-table-row')]" +
                "[.//div[contains(@class,'oxd-table-cell')]" +
                "[normalize-space()='" + username + "']]" +
                "//div[contains(@class,'oxd-table-cell')][3]"
        );

        WebElement roleCell = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        userRoleCell
                )
        );

        return roleCell.getText().trim();
    }

    // ============================================================
    // VERIFY USER ROLE
    // ============================================================

    public boolean isUserRoleCorrect(
            String username,
            String expectedRole
    ) {

        String actualRole = getUserRole(username);

        return actualRole.equalsIgnoreCase(
                expectedRole
        );
    }

    // ============================================================
    // GET ACTUAL ROLE
    // ============================================================

    public String getActualRole(String username) {

        return getUserRole(username);
    }

    // ============================================================
    // RESET SEARCH
    // ============================================================

    public void resetSearch() {

        WebElement reset = wait.until(
                ExpectedConditions.elementToBeClickable(
                        resetButton
                )
        );

        reset.click();
    }
}