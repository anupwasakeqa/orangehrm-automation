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
                "Final GET API Status Code: "
                        + employeeResponse.getStatusCode()
        );

        // ========================================================
        // PRINT GET RESPONSE
        // ========================================================

        String getResponseBody =
                employeeResponse.asString();

        System.out.println(
                "GET API Response:"
        );

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
                getDataSize(employeeResponse);

        System.out.println(
                "Employees returned by API: "
                        + totalEmployees
        );

        // ========================================================
        // SEARCH EMPLOYEE
        // ========================================================

        for (
                int i = 0;
                i < totalEmployees;
                i++
        ) {

            String apiEmployeeId =
                    getStringValue(
                            employeeResponse,
                            "data[" + i + "].employeeId"
                    );

            String apiFirstName =
                    getStringValue(
                            employeeResponse,
                            "data[" + i + "].firstName"
                    );

            String apiMiddleName =
                    getStringValue(
                            employeeResponse,
                            "data[" + i + "].middleName"
                    );

            String apiLastName =
                    getStringValue(
                            employeeResponse,
                            "data[" + i + "].lastName"
                    );

            String apiEmpNumberText =
                    getStringValue(
                            employeeResponse,
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

            // ====================================================
            // MATCH USING FIRST NAME + LAST NAME
            // ====================================================

            boolean firstNameMatches =
                    apiFirstName != null
                            && apiFirstName.trim()
                                    .equalsIgnoreCase(firstName);

            boolean lastNameMatches =
                    apiLastName != null
                            && apiLastName.trim()
                                    .equalsIgnoreCase(lastName);

            if (firstNameMatches && lastNameMatches) {

                employeeId =
                        apiEmployeeId;

                // =================================================
                // EMPLOYEE ID CAN BE NULL IN ORANGEHRM
                // =================================================

                if (employeeId != null) {

                    employeeId =
                            employeeId.trim();

                    if (employeeId.isEmpty()) {
                        employeeId = null;
                    }
                }

                // =================================================
                // GET INTERNAL EMPLOYEE NUMBER
                // =================================================

                if (apiEmpNumberText != null
                        && !apiEmpNumberText.trim().isEmpty()) {

                    try {

                        employeeNumber =
                                Integer.valueOf(
                                        apiEmpNumberText.trim()
                                );

                    } catch (NumberFormatException e) {

                        Assert.fail(
                                "Invalid empNumber received from API: "
                                        + apiEmpNumberText
                        );
                    }
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
        // IMPORTANT:
        //
        // OrangeHRM may return employeeId = null.
        //
        // employeeId is NOT required for DELETE.
        //
        // empNumber is the actual identifier required by
        // deleteEmployee().
        // ========================================================

        Assert.assertNotNull(
                employeeNumber,
                "Matching API empNumber was not found for employee: "
                        + employeeName
        );

        System.out.println(
                "Employee Number captured from API: "
                        + employeeNumber
        );

        if (employeeId == null) {

            System.out.println(
                    "Employee ID is null. This is acceptable because "
                            + "DELETE API uses empNumber."
            );

        } else {

            System.out.println(
                    "Employee ID captured from API: "
                            + employeeId
            );
        }

        // ========================================================
        // VERIFY CREATED EMPLOYEE IN UI
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
                12;

        final long waitBetweenAttempts =
                2000L;

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
                                getDataSize(
                                        lastResponse
                                );

                        System.out.println(
                                "Employees returned on attempt "
                                        + attempt
                                        + ": "
                                        + count
                        );

                        boolean exists =
                                containsEmployee(
                                        lastResponse,
                                        employeeName
                                );

                        System.out.println(
                                "Created employee found on attempt "
                                        + attempt
                                        + ": "
                                        + exists
                        );

                        if (exists) {

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
                8;

        final long waitBetweenAttempts =
                2000L;

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
                    getDataSize(response);

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
                        getStringValue(
                                response,
                                "data[" + i + "].firstName"
                        );

                String lastName =
                        getStringValue(
                                response,
                                "data[" + i + "].lastName"
                        );

                if (firstName != null
                        && lastName != null
                        && firstName.trim()
                                .equalsIgnoreCase(
                                        expectedFirstName
                                )
                        && lastName.trim()
                                .equalsIgnoreCase(
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
    // GET DATA SIZE SAFELY
    // ============================================================

    private int getDataSize(
            Response response) {

        if (response == null) {
            return 0;
        }

        try {

            Object data =
                    response
                            .jsonPath()
                            .get("data");

            if (data == null) {
                return 0;
            }

            if (data instanceof java.util.List<?>) {

                return (
                        (java.util.List<?>) data
                ).size();
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to read API data size: "
                            + e.getMessage()
            );
        }

        return 0;
    }

    // ============================================================
    // GET STRING VALUE SAFELY
    // ============================================================

    private String getStringValue(
            Response response,
            String path) {

        if (response == null) {
            return null;
        }

        try {

            Object value =
                    response
                            .jsonPath()
                            .get(path);

            if (value == null) {
                return null;
            }

            return String.valueOf(value);

        } catch (Exception e) {

            return null;
        }
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