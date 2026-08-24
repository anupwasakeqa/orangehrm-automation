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

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                getUsername(),
                getPassword()
        );

        System.out.println(
                "Login successful."
        );

        // ========================================================
        // EMPLOYEE PAGE
        // ========================================================

        EmployeePage employeePage =
                new EmployeePage(driver);

        // ========================================================
        // UNIQUE EMPLOYEE DATA
        // ========================================================

        String uniqueValue =
                String.valueOf(
                        System.currentTimeMillis()
                ).substring(8);

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
                "Employee creation action completed."
        );

        // ========================================================
        // CAPTURE EMPLOYEE NUMBER
        // ========================================================

        String employeeNumber =
                employeePage.getCurrentEmployeeId();

        Assert.assertNotNull(
                employeeNumber,
                "Generated Employee Number should not be null."
        );

        Assert.assertFalse(
                employeeNumber.trim().isEmpty(),
                "Generated Employee Number should not be empty."
        );

        System.out.println(
                "Created Employee Number: "
                        + employeeNumber
        );

        // ========================================================
        // VERIFY CREATED EMPLOYEE
        // ========================================================

        boolean employeeExists =
                employeePage.verifyEmployeeExistsByEmpNumber(
                        employeeNumber
                );

        Assert.assertTrue(
                employeeExists,
                "Created employee was not found using Employee Number: "
                        + employeeNumber
        );

        System.out.println(
                "Created employee verified successfully in UI."
        );

        // ========================================================
        // NAVIGATE TO EMPLOYEE LIST
        // ========================================================

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "NAVIGATING TO EMPLOYEE LIST"
        );
        System.out.println(
                "================================================"
        );

        employeePage.clickPIM();
        employeePage.clickEmployeeList();

        System.out.println(
                "Employee List opened successfully."
        );

        // ========================================================
        // SEARCH CREATED EMPLOYEE AND OPEN EDIT
        // ========================================================

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "SEARCHING CREATED EMPLOYEE FOR EDIT"
        );
        System.out.println(
                "================================================"
        );

        employeePage.clickEditEmployee(
                firstName
        );

        System.out.println(
                "Employee edit page opened successfully."
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
                "Employee Number    : "
                        + employeeNumber
        );

        System.out.println(
                "Original Name      : "
                        + firstName + " "
                        + middleName + " "
                        + lastName
        );

        System.out.println(
                "Updated First Name : "
                        + updatedFirstName
        );

        System.out.println(
                "Updated Last Name  : "
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