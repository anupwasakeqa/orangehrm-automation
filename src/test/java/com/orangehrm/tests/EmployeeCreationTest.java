package com.orangehrm.tests;

import org.testng.annotations.Test;

import com.orangehrm.pages.EmployeePage;
import com.orangehrm.pages.LoginPage;

public class EmployeeCreationTest extends BaseTest {

    @Test
    public void createEmployeeTest() {

        System.out.println("================================================");
        System.out.println("EMPLOYEE CREATION TEST STARTED");
        System.out.println("================================================");

        // LOGIN
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                "Admin",
                "admin123"
        );

        System.out.println("Login completed successfully.");

        // EMPLOYEE PAGE
        EmployeePage employeePage = new EmployeePage(driver);

        // TEST DATA
        String firstName = "Anup";
        String middleName = "Automation";
        String lastName = "Test";

        System.out.println("Employee First Name: " + firstName);
        System.out.println("Employee Middle Name: " + middleName);
        System.out.println("Employee Last Name: " + lastName);

        // CREATE EMPLOYEE
        employeePage.createEmployee(
                firstName,
                middleName,
                lastName
        );

        System.out.println(
                "Employee creation test completed successfully."
        );

        System.out.println("================================================");
        System.out.println("EMPLOYEE CREATION TEST PASSED");
        System.out.println("================================================");
    }
}