package com.orangehrm.api;

import java.util.Map;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmployeeApi {

    // ============================================================
    // EMPLOYEE API ENDPOINT
    // ============================================================

    private static final String EMPLOYEE_API =
            "/web/index.php/api/v2/pim/employees";

    // ============================================================
    // GET ALL EMPLOYEES
    // ============================================================

    public Response getEmployees(
            String baseUrl,
            Map<String, String> cookies) {

        return given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookies(cookies)
            .when()
                .get(EMPLOYEE_API)
            .then()
                .extract()
                .response();
    }

    // ============================================================
    // GET EMPLOYEE BY NAME
    // ============================================================

    public Response getEmployeeByName(
            String baseUrl,
            String employeeName,
            Map<String, String> cookies) {

        return given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookies(cookies)
                .queryParam(
                        "nameOrId",
                        employeeName
                )
            .when()
                .get(EMPLOYEE_API)
            .then()
                .extract()
                .response();
    }

    // ============================================================
    // GET SINGLE EMPLOYEE
    // ============================================================

    public Response getEmployee(
            String baseUrl,
            String employeeNumber,
            Map<String, String> cookies) {

        return given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookies(cookies)
            .when()
                .get(
                        EMPLOYEE_API
                                + "/"
                                + employeeNumber
                )
            .then()
                .extract()
                .response();
    }

    // ============================================================
    // GET EMPLOYEE BY ID
    // ============================================================

    public Response getEmployeeById(
            String baseUrl,
            String employeeNumber,
            Map<String, String> cookies) {

        return given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookies(cookies)
            .when()
                .get(
                        EMPLOYEE_API
                                + "/"
                                + employeeNumber
                )
            .then()
                .extract()
                .response();
    }

    // ============================================================
    // CREATE EMPLOYEE
    // ============================================================

    public Response createEmployee(
            String baseUrl,
            String firstName,
            String middleName,
            String lastName,
            Map<String, String> cookies) {

        String requestBody =
                "{"
                + "\"firstName\":\"" + firstName + "\","
                + "\"middleName\":\"" + middleName + "\","
                + "\"lastName\":\"" + lastName + "\""
                + "}";

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "CREATE EMPLOYEE API"
        );

        System.out.println(
                "Request Body:"
        );

        System.out.println(
                requestBody
        );

        System.out.println(
                "=============================================="
        );

        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookies(cookies)
                .body(requestBody)
            .when()
                .post(EMPLOYEE_API)
            .then()
                .extract()
                .response();
    }

    // ============================================================
    // UPDATE EMPLOYEE - PERSONAL DETAILS
    // ============================================================

    public Response updateEmployee(
            String baseUrl,
            String employeeNumber,
            String employeeId,
            String firstName,
            String middleName,
            String lastName,
            Map<String, String> cookies) {

        String requestBody =
                "{"
                + "\"lastName\":\"" + lastName + "\","
                + "\"firstName\":\"" + firstName + "\","
                + "\"middleName\":\"" + middleName + "\","
                + "\"employeeId\":\"" + employeeId + "\","
                + "\"otherId\":\"\","
                + "\"drivingLicenseNo\":\"\","
                + "\"drivingLicenseExpiredDate\":null,"
                + "\"gender\":null,"
                + "\"birthday\":null"
                + "}";

        String updateUrl =
                EMPLOYEE_API
                        + "/"
                        + employeeNumber
                        + "/personal-details";

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "UPDATE EMPLOYEE API"
        );

        System.out.println(
                "PUT URL: "
                        + baseUrl
                        + updateUrl
        );

        System.out.println(
                "PUT Request Body:"
        );

        System.out.println(
                requestBody
        );

        System.out.println(
                "=============================================="
        );

        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookies(cookies)
                .body(requestBody)
            .when()
                .put(updateUrl)
            .then()
                .extract()
                .response();
    }

    // ============================================================
    // DELETE EMPLOYEE
    // ============================================================

    public Response deleteEmployee(
            String baseUrl,
            String employeeNumber,
            Map<String, String> cookies) {

        String requestBody =
                "{\"ids\":["
                        + employeeNumber
                        + "]}";

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "DELETE EMPLOYEE API"
        );

        System.out.println(
                "Employee Number: "
                        + employeeNumber
        );

        System.out.println(
                "Request Body:"
        );

        System.out.println(
                requestBody
        );

        System.out.println(
                "=============================================="
        );

        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookies(cookies)
                .body(requestBody)
            .when()
                .delete(EMPLOYEE_API)
            .then()
                .extract()
                .response();
    }

    // ============================================================
    // DELETE EMPLOYEE BY ID
    // ============================================================

    public Response deleteEmployeeById(
            String baseUrl,
            String employeeNumber,
            Map<String, String> cookies) {

        return deleteEmployee(
                baseUrl,
                employeeNumber,
                cookies
        );
    }
}