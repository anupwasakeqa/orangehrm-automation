package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.orangehrm.utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;

    private WaitUtils waitUtils;


    // =====================================================
    // USERNAME LOCATORS
    // =====================================================

    private By usernameByName =
            By.cssSelector("input[name='username']");

    private By usernameByPlaceholder =
            By.cssSelector("input[placeholder='Username']");

    private By usernameByType =
            By.cssSelector("input[type='text']");


    // =====================================================
    // PASSWORD LOCATORS
    // =====================================================

    private By passwordByName =
            By.cssSelector("input[name='password']");

    private By passwordByPlaceholder =
            By.cssSelector("input[placeholder='Password']");

    private By passwordByType =
            By.cssSelector("input[type='password']");


    // =====================================================
    // LOGIN BUTTON
    // =====================================================

    private By loginButton =
            By.cssSelector("button[type='submit']");


    // =====================================================
    // DASHBOARD
    // =====================================================

    private By dashboard =
            By.xpath(
                    "//h6[normalize-space()='Dashboard']"
            );


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.waitUtils =
                new WaitUtils(driver);
    }


    // =====================================================
    // WAIT FOR LOGIN PAGE
    // =====================================================

    public void waitForLoginPage() {

        System.out.println(
                "Waiting for OrangeHRM login page..."
        );

        waitUtils.waitForUrlContains(
                "/auth/login"
        );

        System.out.println(
                "OrangeHRM login URL detected."
        );

        waitUtils.waitForPresence(
                By.tagName("body")
        );

        System.out.println(
                "OrangeHRM login page loaded."
        );
    }


    // =====================================================
    // GET USERNAME
    // =====================================================

    private WebElement getUsernameField() {

        System.out.println(
                "Finding Username field..."
        );


        // 1. name=username

        try {

            WebElement element =
                    waitUtils.waitForVisibility(
                            usernameByName
                    );

            System.out.println(
                    "Username found using name='username'."
            );

            return element;

        } catch (Exception e) {

            System.out.println(
                    "name='username' locator failed."
            );
        }


        // 2. placeholder=Username

        try {

            WebElement element =
                    waitUtils.waitForVisibility(
                            usernameByPlaceholder
                    );

            System.out.println(
                    "Username found using placeholder."
            );

            return element;

        } catch (Exception e) {

            System.out.println(
                    "placeholder locator failed."
            );
        }


        // 3. input[type=text]

        try {

            WebElement element =
                    waitUtils.waitForVisibility(
                            usernameByType
                    );

            System.out.println(
                    "Username found using input[type='text']."
            );

            return element;

        } catch (Exception e) {

            System.out.println(
                    "All Username locators failed."
            );

            throw e;
        }
    }


    // =====================================================
    // GET PASSWORD
    // =====================================================

    private WebElement getPasswordField() {

        System.out.println(
                "Finding Password field..."
        );


        // 1. name=password

        try {

            WebElement element =
                    waitUtils.waitForVisibility(
                            passwordByName
                    );

            System.out.println(
                    "Password found using name='password'."
            );

            return element;

        } catch (Exception e) {

            System.out.println(
                    "name='password' locator failed."
            );
        }


        // 2. placeholder=Password

        try {

            WebElement element =
                    waitUtils.waitForVisibility(
                            passwordByPlaceholder
                    );

            System.out.println(
                    "Password found using placeholder."
            );

            return element;

        } catch (Exception e) {

            System.out.println(
                    "placeholder locator failed."
            );
        }


        // 3. input[type=password]

        try {

            WebElement element =
                    waitUtils.waitForVisibility(
                            passwordByType
                    );

            System.out.println(
                    "Password found using input[type='password']."
            );

            return element;

        } catch (Exception e) {

            System.out.println(
                    "All Password locators failed."
            );

            throw e;
        }
    }


    // =====================================================
    // ENTER USERNAME
    // =====================================================

    public void enterUsername(String username) {

        System.out.println(
                "Entering username..."
        );

        WebElement usernameElement =
                getUsernameField();

        usernameElement.clear();

        usernameElement.sendKeys(
                username
        );

        System.out.println(
                "Username entered successfully."
        );
    }


    // =====================================================
    // ENTER PASSWORD
    // =====================================================

    public void enterPassword(String password) {

        System.out.println(
                "Entering password..."
        );

        WebElement passwordElement =
                getPasswordField();

        passwordElement.clear();

        passwordElement.sendKeys(
                password
        );

        System.out.println(
                "Password entered successfully."
        );
    }


    // =====================================================
    // CLICK LOGIN
    // =====================================================

    public void clickLogin() {

        System.out.println(
                "Clicking Login button..."
        );

        WebElement loginElement =
                waitUtils.waitForClickable(
                        loginButton
                );

        loginElement.click();

        System.out.println(
                "Login button clicked."
        );
    }


    // =====================================================
    // VERIFY LOGIN
    // =====================================================

    public boolean isLoginSuccessful() {

        try {

            waitUtils.waitForUrlContains(
                    "/dashboard"
            );

            System.out.println(
                    "Login successful."
            );

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Dashboard URL verification failed."
            );


            try {

                waitUtils.waitForVisibility(
                        dashboard
                );

                System.out.println(
                        "Dashboard element detected."
                );

                return true;

            } catch (Exception dashboardException) {

                System.out.println(
                        "Login verification failed."
                );

                return false;
            }
        }
    }


    // =====================================================
    // COMPLETE LOGIN
    // =====================================================

    public void login(
            String username,
            String password) {

        System.out.println(
                "Starting OrangeHRM login..."
        );

        waitForLoginPage();

        enterUsername(username);

        enterPassword(password);

        clickLogin();

        if (!isLoginSuccessful()) {

            throw new RuntimeException(
                    "OrangeHRM login failed."
            );
        }

        System.out.println(
                "OrangeHRM login completed successfully."
        );
    }
}