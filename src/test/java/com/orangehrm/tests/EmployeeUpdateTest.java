package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.pages.EmployeePage;
import com.orangehrm.pages.LoginPage;

public class EmployeeUpdateTest extends BaseTest {

    @Test
    public void updateEmployeeTest() {

        System.out.println();
        System.out.println("================================================");
        System.out.println("EMPLOYEE UPDATE TEST STARTED");
        System.out.println("================================================");

        // ========================================================
        // LOGIN
        // ========================================================

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                getUsername(),
                getPassword()
        );

        System.out.println("Login successful.");

        // ========================================================
        // EMPLOYEE PAGE
        // ========================================================

        EmployeePage employeePage =
                new EmployeePage(driver);

        // ========================================================
        // UNIQUE EMPLOYEE DATA
        // ========================================================

        String uniqueValue =
                String.valueOf(System.currentTimeMillis())
                        .substring(8);

        String firstName =
                "Anup" + uniqueValue;

        String middleName =
                "QA";

        String lastName =
                "Auto";

        String updatedFirstName =
                "Upd" + uniqueValue;

        String updatedLastName =
                "Updated";

        System.out.println(
                "Employee to create: "
                        + firstName + " "
                        + middleName + " "
                        + lastName
        );

        System.out.println(
                "Updated employee name: "
                        + updatedFirstName + " "
                        + updatedLastName
        );

        // ========================================================
        // CREATE EMPLOYEE
        // ========================================================

        employeePage.createEmployee(
                firstName,
                middleName,
                lastName
        );

        System.out.println(
                "Employee created successfully."
        );

        // ========================================================
        // GET EMPLOYEE ID
        // ========================================================

        String employeeId =
                employeePage.getCurrentEmployeeId();

        System.out.println(
                "Generated Employee ID: "
                        + employeeId
        );

        Assert.assertNotNull(
                employeeId,
                "Generated Employee ID should not be null."
        );

        Assert.assertFalse(
                employeeId.trim().isEmpty(),
                "Generated Employee ID should not be empty."
        );

        // ========================================================
        // VERIFY CREATED EMPLOYEE
        // ========================================================

        System.out.println();
        System.out.println(
                "Verifying created employee using ID..."
        );

        boolean employeeExists =
                employeePage.verifyEmployeeExistsById(
                        employeeId
                );

        Assert.assertTrue(
                employeeExists,
                "Created employee was not found with ID: "
                        + employeeId
        );

        System.out.println(
                "Created employee verified successfully."
        );

        // ========================================================
        // OPEN EMPLOYEE FOR EDIT
        // ========================================================

        employeePage.clickEditEmployeeById(
                employeeId
        );

        System.out.println(
                "Employee edit page opened."
        );

        // ========================================================
        // UPDATE FIRST NAME
        // ========================================================

        employeePage.updateFirstName(
                updatedFirstName
        );

        System.out.println(
                "First name updated to: "
                        + updatedFirstName
        );

        // ========================================================
        // UPDATE LAST NAME
        // ========================================================

        employeePage.updateLastName(
                updatedLastName
        );

        System.out.println(
                "Last name updated to: "
                        + updatedLastName
        );

        // ========================================================
        // SAVE
        // ========================================================

        employeePage.clickSave();

        System.out.println(
                "Employee details saved."
        );

        // ========================================================
        // VERIFY UPDATED DETAILS
        // ========================================================

        boolean updated =
                employeePage.verifyEmployeeDetails(
                        updatedFirstName,
                        updatedLastName
                );

        Assert.assertTrue(
                updated,
                "Employee details were not updated correctly. "
                        + "Expected First Name: "
                        + updatedFirstName
                        + ", Expected Last Name: "
                        + updatedLastName
        );

        // ========================================================
        // SUCCESS
        // ========================================================

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "EMPLOYEE UPDATE TEST PASSED"
        );
        System.out.println(
                "================================================"
        );

        System.out.println(
                "Employee ID       : "
                        + employeeId
        );

        System.out.println(
                "Original Name     : "
                        + firstName + " "
                        + middleName + " "
                        + lastName
        );

        System.out.println(
                "Updated First Name: "
                        + updatedFirstName
        );

        System.out.println(
                "Updated Last Name : "
                        + updatedLastName
        );

        System.out.println(
                "Employee update verified successfully."
        );

        System.out.println(
                "================================================"
        );
    }
}