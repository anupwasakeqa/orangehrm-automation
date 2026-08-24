package com.orangehrm.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.api.EmployeeApi;
import com.orangehrm.pages.EmployeePage;
import com.orangehrm.pages.LoginPage;

import io.restassured.response.Response;

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
        // OBJECTS
        // ========================================================

        EmployeePage employeePage = new EmployeePage(driver);
        EmployeeApi employeeApi = new EmployeeApi();

        // ========================================================
        // COOKIES + BASE URL
        // ========================================================

        Map<String, String> cookies = getCookies();
        String baseUrl = getBaseUrl();

        Assert.assertNotNull(
                baseUrl,
                "Base URL is null."
        );

        Assert.assertNotNull(
                cookies,
                "Authentication cookies are null."
        );

        Assert.assertFalse(
                cookies.isEmpty(),
                "Authentication cookies are empty."
        );

        System.out.println("Base URL: " + baseUrl);

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
        // CREATE EMPLOYEE USING API
        // ========================================================

        System.out.println();
        System.out.println("================================================");
        System.out.println("CREATING EMPLOYEE USING CREATE API");
        System.out.println("================================================");

        Response createResponse =
                employeeApi.createEmployee(
                        baseUrl,
                        firstName,
                        middleName,
                        lastName,
                        cookies
                );

        Assert.assertNotNull(
                createResponse,
                "Create employee API response is null."
        );

        int createStatus =
                createResponse.getStatusCode();

        System.out.println(
                "CREATE STATUS: " + createStatus
        );

        System.out.println(
                "CREATE RESPONSE:"
        );

        System.out.println(
                createResponse.asPrettyString()
        );

        Assert.assertTrue(
                createStatus == 200
                        || createStatus == 201,
                "Employee creation API failed. Status: "
                        + createStatus
        );

        // ========================================================
        // CAPTURE INTERNAL EMPLOYEE NUMBER FROM API
        // ========================================================

        Integer employeeNumber = null;

        try {

            employeeNumber =
                    createResponse
                            .jsonPath()
                            .getInt("data.empNumber");

        } catch (Exception e) {

            Assert.fail(
                    "Unable to extract empNumber from CREATE API response.",
                    e
            );
        }

        Assert.assertNotNull(
                employeeNumber,
                "Generated Employee Number should not be null."
        );

        Assert.assertTrue(
                employeeNumber > 0,
                "Generated Employee Number should be greater than zero."
        );

        System.out.println(
                "Internal Employee Number: "
                        + employeeNumber
        );

        // ========================================================
        // VERIFY CREATED EMPLOYEE IN UI
        // ========================================================

        System.out.println();
        System.out.println("================================================");
        System.out.println("VERIFYING CREATED EMPLOYEE IN UI");
        System.out.println("================================================");

        boolean employeeExists =
                employeePage.verifyEmployeeExistsByEmpNumber(
                        String.valueOf(employeeNumber)
                );

        Assert.assertTrue(
                employeeExists,
                "Created employee was not found in UI using empNumber: "
                        + employeeNumber
        );

        System.out.println(
                "Created employee verified successfully in UI."
        );

        // ========================================================
        // OPEN CREATED EMPLOYEE DIRECTLY BY EMP NUMBER
        // ========================================================

        System.out.println();
        System.out.println("================================================");
        System.out.println("OPENING CREATED EMPLOYEE BY EMP NUMBER");
        System.out.println("================================================");

        employeePage.clickEditEmployeeById(
                String.valueOf(employeeNumber)
        );

        System.out.println(
                "Employee edit page opened successfully using empNumber: "
                        + employeeNumber
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
        // FINAL SUCCESS
        // ========================================================

        System.out.println();
        System.out.println("================================================");
        System.out.println("EMPLOYEE UPDATE TEST PASSED");
        System.out.println("================================================");

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