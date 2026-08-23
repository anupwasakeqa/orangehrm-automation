package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {

        System.out.println("Login test started");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                getUsername(),
                getPassword()
        );

        // Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("URL after login: " + driver.getCurrentUrl());
        System.out.println("Title after login: " + driver.getTitle());

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/dashboard"),
                "Login failed. Actual URL: " + driver.getCurrentUrl()
        );

        System.out.println("Login successful");
    }
}