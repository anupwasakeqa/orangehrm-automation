package com.orangehrm.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.api.EmployeeApi;
import com.orangehrm.pages.EmployeePage;
import com.orangehrm.pages.LoginPage;

import io.restassured.response.Response;

public class EmployeeDeleteTest extends BaseTest {

    // ============================================================
    // DELETE EMPLOYEE TEST
    // ============================================================

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
        // PAGE + API OBJECTS
        // ========================================================

        EmployeePage employeePage =
                new EmployeePage(driver);

        EmployeeApi employeeApi =
                new EmployeeApi();

        // ========================================================
        // CREATE UNIQUE EMPLOYEE
        // ========================================================

        String firstName =
                "Amelia";

        String middleName =
                "Test";

        String lastName =
                "Delete" + System.currentTimeMillis();

        String employeeName =
                firstName + " " + lastName;

        System.out.println(
                "================================================"
        );

        System.out.println(
                "CREATING UNIQUE EMPLOYEE"
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

        employeePage.createEmployee(
                firstName,
                middleName,
                lastName
        );

        System.out.println(
                "Employee creation submitted successfully: "
                        + employeeName
        );

        // ========================================================
        // GET COOKIES + BASE URL
        // ========================================================

        Map<String, String> cookies =
                getCookies();

        String baseUrl =
                getBaseUrl();

        Assert.assertNotNull(
                baseUrl,
                "Base URL is null."
        );

        Assert.assertFalse(
                cookies.isEmpty(),
                "Authentication cookies are empty."
        );

        System.out.println(
                "Base URL: " + baseUrl
        );

        // ========================================================
        // API GET - WAIT FOR CREATED EMPLOYEE
        // ========================================================

        System.out.println(
                "================================================"
        );

        System.out.println(
                "WAITING FOR CREATED EMPLOYEE IN GET API"
        );

        System.out.println(
                "================================================"
        );

        Response employeeResponse =
                getEmployeeWithRetry(
                        employeeApi,
                        baseUrl,
                        employeeName,
                        cookies
                );

        Assert.assertNotNull(
                employeeResponse,
                "Employee GET API response is null."
        );

        System.out.println(
                "GET API Status Code: "
                        + employeeResponse.getStatusCode()
        );

        System.out.println(
                "GET API Response:"
        );

        String getResponseBody =
                employeeResponse.asString();

        if (getResponseBody != null
                && !getResponseBody.trim().isEmpty()) {

            System.out.println(
                    employeeResponse.asPrettyString()
            );

        } else {

            System.out.println(
                    "GET API returned an empty response body."
            );
        }

        Assert.assertEquals(
                employeeResponse.getStatusCode(),
                200,
                "Employee GET API failed."
        );

        // ========================================================
        // FIND MATCHING EMPLOYEE
        // ========================================================

        String employeeId =
                null;

        Integer employeeNumber =
                null;

        int totalEmployees =
                employeeResponse
                        .jsonPath()
                        .getInt("data.size()");

        System.out.println(
                "Employees returned by API: "
                        + totalEmployees
        );

        for (
                int i = 0;
                i < totalEmployees;
                i++
        ) {

            String apiEmployeeId =
                    employeeResponse
                            .jsonPath()
                            .getString(
                                    "data[" + i + "].employeeId"
                            );

            String apiFirstName =
                    employeeResponse
                            .jsonPath()
                            .getString(
                                    "data[" + i + "].firstName"
                            );

            String apiMiddleName =
                    employeeResponse
                            .jsonPath()
                            .getString(
                                    "data[" + i + "].middleName"
                            );

            String apiLastName =
                    employeeResponse
                            .jsonPath()
                            .getString(
                                    "data[" + i + "].lastName"
                            );

            String apiEmpNumberText =
                    employeeResponse
                            .jsonPath()
                            .getString(
                                    "data[" + i + "].empNumber"
                            );

            System.out.println(
                    "API Employee "
                            + (i + 1)
                            + " : "
                            + apiFirstName
                            + " "
                            + apiMiddleName
                            + " "
                            + apiLastName
                            + " | Employee ID: "
                            + apiEmployeeId
                            + " | empNumber: "
                            + apiEmpNumberText
            );

            String apiFullName =
                    (
                            (apiFirstName == null
                                    ? ""
                                    : apiFirstName)
                                    + " "
                                    + (apiLastName == null
                                    ? ""
                                    : apiLastName)
                    ).trim();

            if (apiFullName.equalsIgnoreCase(
                    employeeName
            )) {

                employeeId =
                        apiEmployeeId;

                try {

                    employeeNumber =
                            Integer.valueOf(
                                    apiEmpNumberText
                            );

                } catch (NumberFormatException e) {

                    Assert.fail(
                            "Invalid empNumber received from API: "
                                    + apiEmpNumberText
                    );
                }

                System.out.println(
                        "================================================"
                );

                System.out.println(
                        "MATCHING EMPLOYEE FOUND"
                );

                System.out.println(
                        "Employee Name : "
                                + employeeName
                );

                System.out.println(
                        "Employee ID   : "
                                + employeeId
                );

                System.out.println(
                        "empNumber     : "
                                + employeeNumber
                );

                System.out.println(
                        "================================================"
                );

                break;
            }
        }

        // ========================================================
        // VERIFY API EMPLOYEE DATA
        // ========================================================

        Assert.assertNotNull(
                employeeId,
                "Matching Employee ID was not found in GET API."
        );

        Assert.assertFalse(
                employeeId.trim().isEmpty(),
                "Employee ID is empty."
        );

        Assert.assertNotNull(
                employeeNumber,
                "Matching API empNumber was not found for Employee ID: "
                        + employeeId
        );

        employeeId =
                employeeId.trim();

        System.out.println(
                "Employee ID captured from API: "
                        + employeeId
        );

        System.out.println(
                "Employee Number captured from API: "
                        + employeeNumber
        );

        // ========================================================
        // VERIFY CREATED EMPLOYEE IN UI
        // ========================================================
        //
        // IMPORTANT:
        //
        // employeeId = 0382
        // employeeNumber = 179
        //
        // Direct URL requires:
        //
        // /empNumber/179
        //
        // NOT:
        //
        // /empNumber/0382
        //
        // ========================================================

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

        System.out.println(
                "Employee ID   : "
                        + employeeId
        );

        System.out.println(
                "empNumber     : "
                        + employeeNumber
        );

        // ========================================================
        // DELETE EMPLOYEE USING API
        // ========================================================

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
        // VERIFY DELETE API
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
        // VERIFY DELETION USING API
        // ========================================================

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
                getEmployeeWithRetryAfterDelete(
                        employeeApi,
                        baseUrl,
                        employeeName,
                        cookies
                );

        Assert.assertNotNull(
                verificationResponse,
                "Delete verification GET response is null."
        );

        System.out.println(
                "Delete verification GET status: "
                        + verificationResponse.getStatusCode()
        );

        String verificationBody =
                verificationResponse.asString();

        if (verificationBody != null
                && !verificationBody.trim().isEmpty()) {

            System.out.println(
                    verificationResponse.asPrettyString()
            );
        }

        // ========================================================
        // CHECK EMPLOYEE DOES NOT EXIST
        // ========================================================

        boolean employeeStillExistsInApi =
                containsEmployee(
                        verificationResponse,
                        employeeName
                );

        Assert.assertFalse(
                employeeStillExistsInApi,
                "Employee still exists in API after delete: "
                        + employeeName
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
                "Employee ID     : "
                        + employeeId
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

    // ============================================================
    // GET EMPLOYEE WITH RETRY
    // ============================================================

    private Response getEmployeeWithRetry(
            EmployeeApi employeeApi,
            String baseUrl,
            String employeeName,
            Map<String, String> cookies) {

        final int maxAttempts =
                8;

        final long waitBetweenAttempts =
                1500L;

        Response lastResponse =
                null;

        for (
                int attempt = 1;
                attempt <= maxAttempts;
                attempt++
        ) {

            System.out.println(
                    "GET attempt "
                            + attempt
                            + " of "
                            + maxAttempts
            );

            try {

                lastResponse =
                        employeeApi.getEmployeeByName(
                                baseUrl,
                                employeeName,
                                cookies
                        );

                if (lastResponse == null) {

                    System.out.println(
                            "Attempt "
                                    + attempt
                                    + " returned null response."
                    );

                } else {

                    System.out.println(
                            "Attempt "
                                    + attempt
                                    + " status: "
                                    + lastResponse.getStatusCode()
                    );

                    if (lastResponse.getStatusCode() == 200) {

                        int count =
                                lastResponse
                                        .jsonPath()
                                        .getInt(
                                                "data.size()"
                                        );

                        System.out.println(
                                "Employees returned on attempt "
                                        + attempt
                                        + ": "
                                        + count
                        );

                        if (containsEmployee(
                                lastResponse,
                                employeeName
                        )) {

                            System.out.println(
                                    "Created employee is now available in GET API."
                            );

                            return lastResponse;
                        }
                    }
                }

            } catch (Exception e) {

                System.out.println(
                        "GET attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage()
                );
            }

            if (attempt < maxAttempts) {

                sleep(
                        waitBetweenAttempts
                );
            }
        }

        return lastResponse;
    }

    // ============================================================
    // GET EMPLOYEE AFTER DELETE WITH RETRY
    // ============================================================

    private Response getEmployeeWithRetryAfterDelete(
            EmployeeApi employeeApi,
            String baseUrl,
            String employeeName,
            Map<String, String> cookies) {

        final int maxAttempts =
                5;

        final long waitBetweenAttempts =
                1500L;

        Response lastResponse =
                null;

        for (
                int attempt = 1;
                attempt <= maxAttempts;
                attempt++
        ) {

            System.out.println(
                    "DELETE verification GET attempt "
                            + attempt
                            + " of "
                            + maxAttempts
            );

            try {

                lastResponse =
                        employeeApi.getEmployeeByName(
                                baseUrl,
                                employeeName,
                                cookies
                        );

                if (lastResponse != null) {

                    System.out.println(
                            "Verification attempt "
                                    + attempt
                                    + " status: "
                                    + lastResponse.getStatusCode()
                    );

                    if (lastResponse.getStatusCode() == 200) {

                        boolean exists =
                                containsEmployee(
                                        lastResponse,
                                        employeeName
                                );

                        System.out.println(
                                "Employee exists after delete: "
                                        + exists
                        );

                        if (!exists) {

                            System.out.println(
                                    "Employee deletion confirmed by API."
                            );

                            return lastResponse;
                        }
                    }
                }

            } catch (Exception e) {

                System.out.println(
                        "Delete verification attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage()
                );
            }

            if (attempt < maxAttempts) {

                sleep(
                        waitBetweenAttempts
                );
            }
        }

        return lastResponse;
    }

    // ============================================================
    // CHECK EMPLOYEE EXISTS IN API RESPONSE
    // ============================================================

    private boolean containsEmployee(
            Response response,
            String expectedEmployeeName) {

        if (response == null) {

            return false;
        }

        try {

            int totalEmployees =
                    response
                            .jsonPath()
                            .getInt(
                                    "data.size()"
                            );

            if (totalEmployees <= 0) {

                return false;
            }

            String[] expectedParts =
                    expectedEmployeeName
                            .trim()
                            .split("\\s+");

            if (expectedParts.length < 2) {

                return false;
            }

            String expectedFirstName =
                    expectedParts[0];

            String expectedLastName =
                    expectedParts[
                            expectedParts.length - 1
                    ];

            for (
                    int i = 0;
                    i < totalEmployees;
                    i++
            ) {

                String firstName =
                        response
                                .jsonPath()
                                .getString(
                                        "data[" + i + "].firstName"
                                );

                String lastName =
                        response
                                .jsonPath()
                                .getString(
                                        "data[" + i + "].lastName"
                                );

                if (firstName != null
                        && lastName != null
                        && firstName.equalsIgnoreCase(
                                expectedFirstName
                        )
                        && lastName.equalsIgnoreCase(
                                expectedLastName
                        )) {

                    return true;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error while checking API employee data: "
                            + e.getMessage()
            );
        }

        return false;
    }

    // ============================================================
    // SLEEP
    // ============================================================

    private void sleep(
            long milliseconds) {

        try {

            Thread.sleep(
                    milliseconds
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "Thread interrupted while waiting.",
                    e
            );
        }
    }
}