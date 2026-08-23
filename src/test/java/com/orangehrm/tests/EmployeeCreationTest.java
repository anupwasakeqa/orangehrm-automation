package com.orangehrm.tests;

import org.testng.annotations.Test;
import com.orangehrm.pages.EmployeePage;
import com.orangehrm.pages.LoginPage;

public class EmployeeCreationTest extends BaseTest {

    @Test
    public void createEmployeeTest() {
        System.out.println("Employee creation test started");

        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        System.out.println("Login successful");

        // Employee Page
        EmployeePage employeePage = new EmployeePage(driver);

        String firstName = "Anup";
        String middleName = "QA";
        String lastName = "Automation";

        // Create employee
        employeePage.createEmployee(firstName, middleName, lastName);

        System.out.println("Employee creation test completed");
    }
}
