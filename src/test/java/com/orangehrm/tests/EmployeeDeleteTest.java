package com.orangehrm.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.api.EmployeeApi;
import com.orangehrm.pages.EmployeePage;
import com.orangehrm.pages.LoginPage;

import io.restassured.response.Response;

public class EmployeeDeleteTest extends BaseTest {

    @Test
    public void deleteEmployeeTest() {

        System.out.println(
                "================================================"
        );

        System.out.println(
                "EMPLOYEE DELETE TEST STARTED"
        );

        System.out.println(
                "================================================"
        );

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
        // OBJECTS
        // ========================================================

        EmployeePage employeePage =
                new EmployeePage(driver);

        EmployeeApi employeeApi =
                new EmployeeApi();

        // ========================================================
        // COOKIES + BASE URL
        // ========================================================

        Map<String, String> cookies =
                getCookies();

        String baseUrl =
                getBaseUrl();

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

        System.out.println(
                "Base URL: " + baseUrl
        );

        // ========================================================
        // UNIQUE EMPLOYEE DATA
        // ========================================================

        String uniqueValue =
                String.valueOf(
                        System.currentTimeMillis()
                ).substring(8);

        String firstName =
                "Amelia" + uniqueValue;

        String middleName =
                "Test";

        String lastName =
                "Delete";

        String employeeName =
                firstName + " " + lastName;

        System.out.println();
        System.out.println(
                "================================================"
        );

        System.out.println(
                "CREATING UNIQUE EMPLOYEE USING API"
        );

        System.out.println(
                "First Name  : " + firstName
        );

        System.out.println(
                "Middle Name : " + middleName
        );

        System.out.println(
                "Last Name   : " + lastName
        );

        System.out.println(
                "Full Name   : " + employeeName
        );

        System.out.println(
                "================================================"
        );

        // ========================================================
        // CREATE EMPLOYEE USING API
        // ========================================================

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
                "CREATE STATUS: "
                        + createStatus
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
        // CAPTURE EMPLOYEE NUMBER DIRECTLY FROM CREATE RESPONSE
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
                "Created employee empNumber is null."
        );

        Assert.assertTrue(
                employeeNumber > 0,
                "Created employee empNumber must be greater than zero."
        );

        System.out.println(
                "Internal Employee Number: "
                        + employeeNumber
        );

        // ========================================================
        // VERIFY CREATED EMPLOYEE IN UI
        // ========================================================

        System.out.println();
        System.out.println(
                "================================================"
        );

        System.out.println(
                "VERIFYING CREATED EMPLOYEE IN UI"
        );

        System.out.println(
                "================================================"
        );

        boolean employeeExistsInUI =
                employeePage.verifyEmployeeExistsByEmpNumber(
                        String.valueOf(employeeNumber)
                );

        Assert.assertTrue(
                employeeExistsInUI,
                "Created employee was not found in UI using empNumber: "
                        + employeeNumber
        );

        System.out.println(
                "Created employee verified successfully in UI."
        );

        // ========================================================
        // DELETE EMPLOYEE USING API
        // ========================================================

        System.out.println();
        System.out.println(
                "================================================"
        );

        System.out.println(
                "CALLING EMPLOYEE DELETE API"
        );

        System.out.println(
                "================================================"
        );

        Response deleteResponse =
                employeeApi.deleteEmployee(
                        baseUrl,
                        String.valueOf(employeeNumber),
                        cookies
                );

        Assert.assertNotNull(
                deleteResponse,
                "Delete API response is null."
        );

        int deleteStatusCode =
                deleteResponse.getStatusCode();

        System.out.println(
                "DELETE API Status Code: "
                        + deleteStatusCode
        );

        System.out.println(
                "DELETE API Response:"
        );

        String deleteResponseBody =
                deleteResponse.asString();

        if (deleteResponseBody != null
                && !deleteResponseBody.trim().isEmpty()) {

            System.out.println(
                    deleteResponse.asPrettyString()
            );

        } else {

            System.out.println(
                    "DELETE API returned an empty response body."
            );
        }

        // ========================================================
        // VERIFY DELETE API STATUS
        // ========================================================

        Assert.assertTrue(
                deleteStatusCode == 200
                        || deleteStatusCode == 204,
                "Delete API failed. Status Code: "
                        + deleteStatusCode
        );

        System.out.println(
                "DELETE API verification passed."
        );

        // ========================================================
        // VERIFY EMPLOYEE NO LONGER EXISTS
        // USING SINGLE EMPLOYEE API
        // ========================================================

        System.out.println();
        System.out.println(
                "================================================"
        );

        System.out.println(
                "VERIFYING EMPLOYEE DELETION"
        );

        System.out.println(
                "================================================"
        );

        Response verificationResponse =
                employeeApi.getEmployee(
                        baseUrl,
                        String.valueOf(employeeNumber),
                        cookies
                );

        Assert.assertNotNull(
                verificationResponse,
                "Delete verification response is null."
        );

        int verificationStatus =
                verificationResponse.getStatusCode();

        System.out.println(
                "Delete verification status: "
                        + verificationStatus
        );

        String verificationBody =
                verificationResponse.asString();

        if (verificationBody != null
                && !verificationBody.trim().isEmpty()) {

            System.out.println(
                    "Delete verification response:"
            );

            System.out.println(
                    verificationResponse.asPrettyString()
            );
        }

        // ========================================================
        // AFTER DELETE:
        //
        // Single employee endpoint should no longer return 200.
        // Usually OrangeHRM returns 404.
        // ========================================================

        Assert.assertNotEquals(
                verificationStatus,
                200,
                "Employee still exists in API after delete. "
                        + "Employee Number: "
                        + employeeNumber
        );

        System.out.println(
                "Employee no longer exists in API."
        );

        // ========================================================
        // FINAL SUCCESS
        // ========================================================

        System.out.println(
                "================================================"
        );

        System.out.println(
                "EMPLOYEE DELETION VERIFIED SUCCESSFULLY"
        );

        System.out.println(
                "Employee Name   : "
                        + employeeName
        );

        System.out.println(
                "Employee Number : "
                        + employeeNumber
        );

        System.out.println(
                "================================================"
        );

        System.out.println(
                "EMPLOYEE DELETE TEST PASSED"
        );

        System.out.println(
                "================================================"
        );
    }
}