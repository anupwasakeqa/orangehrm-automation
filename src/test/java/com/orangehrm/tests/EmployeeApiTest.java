package com.orangehrm.tests;

import static io.restassured.RestAssured.given;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmployeeApiTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL =
            "https://opensource-demo.orangehrmlive.com";

    private static final String LOGIN_URL =
            BASE_URL + "/web/index.php/auth/login";

    private static final String EMPLOYEE_API =
            BASE_URL + "/web/index.php/api/v2/pim/employees";

    /*
     * Actual PUT endpoint captured from browser Network tab
     */
    private static final String UPDATE_PERSONAL_DETAILS_API =
            BASE_URL
            + "/web/index.php/api/v2/pim/employees/{employeeNumber}/personal-details";

    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "admin123";

    private static final String FIRST_NAME = "Anup";
    private static final String MIDDLE_NAME = "Test";
    private static final String LAST_NAME = "Employee";

    private static final String UPDATED_FIRST_NAME =
            "AnupUpdated";

    private static final String UPDATED_MIDDLE_NAME =
            "Automation";

    private static final String UPDATED_LAST_NAME =
            "EmployeeUpdated";

    private String orangeHrmCookie;

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod
    public void setUp() {

        System.out.println("================================================");
        System.out.println("TEST SETUP STARTED");
        System.out.println("================================================");

        ChromeOptions options = new ChromeOptions();

        String ciEnvironment = System.getenv("CI");

        if ("true".equalsIgnoreCase(ciEnvironment)) {

            System.out.println("CI environment detected.");
            System.out.println("Running Chrome in headless mode.");

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

        } else {

            System.out.println("Local environment detected.");
            System.out.println("Running Chrome in normal mode.");

            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver(options);

        if (!"true".equalsIgnoreCase(ciEnvironment)) {
            driver.manage().window().maximize();
        }

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );

        openLoginPage();
    }

    // =========================================================
    // TEARDOWN
    // =========================================================

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    // =========================================================
    // OPEN LOGIN PAGE
    // =========================================================

    private void openLoginPage() {

        driver.get(LOGIN_URL);

        wait.until(
                ExpectedConditions.urlContains("/auth/login")
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("username")
                )
        );
    }

    // =========================================================
    // LOGIN
    // =========================================================

    private void login() {

        WebElement usernameField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.name("username")
                        )
                );

        usernameField.clear();
        usernameField.sendKeys(USERNAME);

        WebElement passwordField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.name("password")
                        )
                );

        passwordField.clear();
        passwordField.sendKeys(PASSWORD);

        WebElement loginButton =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.cssSelector("button[type='submit']")
                        )
                );

        loginButton.click();

        wait.until(
                ExpectedConditions.urlContains("/dashboard")
        );

        Assert.assertNotNull(
                driver.manage().getCookieNamed("orangehrm"),
                "OrangeHRM cookie was not created"
        );

        orangeHrmCookie =
                driver.manage()
                        .getCookieNamed("orangehrm")
                        .getValue();

        System.out.println(
                "Login successful"
        );
    }

    // =========================================================
    // CREATE EMPLOYEE API
    // =========================================================

    private Response createEmployeeViaApi(
            String firstName,
            String middleName,
            String lastName) {

        String requestBody =
                "{"
                + "\"firstName\":\"" + firstName + "\","
                + "\"middleName\":\"" + middleName + "\","
                + "\"lastName\":\"" + lastName + "\""
                + "}";

        Response response =
                given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .header(
                            "X-Requested-With",
                            "XMLHttpRequest"
                    )
                    .cookie(
                            "orangehrm",
                            orangeHrmCookie
                    )
                    .body(requestBody)
                .when()
                    .post(EMPLOYEE_API)
                .then()
                    .extract()
                    .response();

        return response;
    }

    // =========================================================
    // GET ALL EMPLOYEES
    // =========================================================

    private Response getEmployeesApi() {

        return given()
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookie(
                        "orangehrm",
                        orangeHrmCookie
                )
            .when()
                .get(EMPLOYEE_API)
            .then()
                .extract()
                .response();
    }

    // =========================================================
    // GET SINGLE EMPLOYEE
    // =========================================================

    private Response getEmployeeApi(
            int employeeNumber) {

        return given()
                .accept(ContentType.JSON)
                .header(
                        "X-Requested-With",
                        "XMLHttpRequest"
                )
                .cookie(
                        "orangehrm",
                        orangeHrmCookie
                )
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

    // =========================================================
    // UPDATE EMPLOYEE USING ACTUAL PUT API
    // =========================================================

    private Response updateEmployeeViaApi(
            int employeeNumber,
            String employeeId,
            String firstName,
            String middleName,
            String lastName) {

        /*
         * Browser Network Payload:
         *
         * {
         *   "lastName":"010Z",
         *   "firstName":"A8DCoTest",
         *   "middleName":"4Ys",
         *   "employeeId":"0312",
         *   "otherId":"",
         *   "drivingLicenseNo":"",
         *   "drivingLicenseExpiredDate":null,
         *   "gender":null,
         *   "birthday":null
         * }
         */

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
                UPDATE_PERSONAL_DETAILS_API.replace(
                        "{employeeNumber}",
                        String.valueOf(employeeNumber)
                );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "PUT URL:"
        );

        System.out.println(
                updateUrl
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

        Response response =
                given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .header(
                            "X-Requested-With",
                            "XMLHttpRequest"
                    )
                    .cookie(
                            "orangehrm",
                            orangeHrmCookie
                    )
                    .body(requestBody)
                .when()
                    .put(updateUrl)
                .then()
                    .extract()
                    .response();

        return response;
    }

    // =========================================================
    // TEST 1 - GET ALL EMPLOYEES
    // =========================================================

    @Test
    public void getEmployeeTest() {

        login();

        Response response =
                getEmployeesApi();

        System.out.println(
                "GET ALL EMPLOYEES STATUS: "
                + response.statusCode()
        );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "GET Employees API failed"
        );
    }

    // =========================================================
    // TEST 2 - GET SINGLE EMPLOYEE
    // =========================================================

    @Test
    public void getSingleEmployeeTest() {

        login();

        Response allEmployees =
                getEmployeesApi();

        Assert.assertEquals(
                allEmployees.statusCode(),
                200,
                "GET Employees API failed"
        );

        int employeeNumber =
                allEmployees
                        .jsonPath()
                        .getInt("data[0].empNumber");

        System.out.println(
                "Employee Number: "
                + employeeNumber
        );

        Response response =
                getEmployeeApi(employeeNumber);

        Assert.assertEquals(
                response.statusCode(),
                200,
                "GET Single Employee API failed"
        );
    }

    // =========================================================
    // TEST 3 - CREATE EMPLOYEE
    // =========================================================

    @Test
    public void createEmployeeTest() {

        login();

        Response response =
                createEmployeeViaApi(
                        FIRST_NAME,
                        MIDDLE_NAME,
                        LAST_NAME
                );

        System.out.println(
                "CREATE EMPLOYEE STATUS: "
                + response.statusCode()
        );

        System.out.println(
                "CREATE EMPLOYEE RESPONSE:"
        );

        System.out.println(
                response.asPrettyString()
        );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "CREATE Employee API failed"
        );

        int employeeNumber =
                response
                        .jsonPath()
                        .getInt("data.empNumber");

        Assert.assertTrue(
                employeeNumber > 0,
                "Employee number was not generated"
        );
    }

    // =========================================================
    // TEST 4 - CREATE + PUT UPDATE + GET VERIFY
    // =========================================================

    @Test
    public void updateEmployeeTest() {

        login();

        // -----------------------------------------------------
        // STEP 1 - CREATE EMPLOYEE
        // -----------------------------------------------------

        String unique =
                String.valueOf(
                        System.currentTimeMillis() % 100000
                );

        String originalFirstName =
                "Anup" + unique;

        String originalMiddleName =
                "Test" + unique;

        String originalLastName =
                "Employee" + unique;

        Response createResponse =
                createEmployeeViaApi(
                        originalFirstName,
                        originalMiddleName,
                        originalLastName
                );

        System.out.println(
                "CREATE STATUS: "
                + createResponse.statusCode()
        );

        System.out.println(
                "CREATE RESPONSE:"
        );

        System.out.println(
                createResponse.asPrettyString()
        );

        Assert.assertEquals(
                createResponse.statusCode(),
                200,
                "Employee creation failed"
        );

        // -----------------------------------------------------
        // STEP 2 - GET INTERNAL EMPLOYEE NUMBER
        // -----------------------------------------------------

        int employeeNumber =
                createResponse
                        .jsonPath()
                        .getInt("data.empNumber");

        Assert.assertTrue(
                employeeNumber > 0,
                "Invalid employee number"
        );

        System.out.println(
                "Internal Employee Number: "
                + employeeNumber
        );

        // -----------------------------------------------------
        // STEP 3 - GET EMPLOYEE ID
        // -----------------------------------------------------

        String employeeId =
                createResponse
                        .jsonPath()
                        .getString("data.employeeId");

        /*
         * OrangeHRM demo API can return employeeId as null
         * during employee creation.
         *
         * Therefore we should NOT fail the test here.
         */

        if (employeeId == null) {

            employeeId = "";

            System.out.println(
                    "Employee ID returned as null. "
                    + "Using empty value for PUT payload."
            );

        } else {

            System.out.println(
                    "Employee ID: "
                    + employeeId
            );
        }

        // -----------------------------------------------------
        // STEP 4 - ACTUAL PUT UPDATE
        // -----------------------------------------------------

        Response updateResponse =
                updateEmployeeViaApi(
                        employeeNumber,
                        employeeId,
                        UPDATED_FIRST_NAME,
                        UPDATED_MIDDLE_NAME,
                        UPDATED_LAST_NAME
                );

        System.out.println(
                "PUT STATUS: "
                + updateResponse.statusCode()
        );

        System.out.println(
                "PUT RESPONSE:"
        );

        System.out.println(
                updateResponse.asPrettyString()
        );

        // -----------------------------------------------------
        // STEP 5 - VERIFY PUT STATUS
        // -----------------------------------------------------

        Assert.assertEquals(
                updateResponse.statusCode(),
                200,
                "PUT Employee API failed"
        );

        // -----------------------------------------------------
        // STEP 6 - GET EMPLOYEE AFTER UPDATE
        // -----------------------------------------------------

        Response getResponse =
                getEmployeeApi(employeeNumber);

        System.out.println(
                "GET AFTER UPDATE STATUS: "
                + getResponse.statusCode()
        );

        System.out.println(
                "GET AFTER UPDATE RESPONSE:"
        );

        System.out.println(
                getResponse.asPrettyString()
        );

        Assert.assertEquals(
                getResponse.statusCode(),
                200,
                "GET Employee API failed after update"
        );

        // -----------------------------------------------------
        // STEP 7 - VERIFY FIRST NAME
        // -----------------------------------------------------

        String actualFirstName =
                getResponse
                        .jsonPath()
                        .getString("data.firstName");

        Assert.assertEquals(
                actualFirstName,
                UPDATED_FIRST_NAME,
                "First Name was not updated correctly"
        );

        // -----------------------------------------------------
        // STEP 8 - VERIFY MIDDLE NAME
        // -----------------------------------------------------

        String actualMiddleName =
                getResponse
                        .jsonPath()
                        .getString("data.middleName");

        Assert.assertEquals(
                actualMiddleName,
                UPDATED_MIDDLE_NAME,
                "Middle Name was not updated correctly"
        );

        // -----------------------------------------------------
        // STEP 9 - VERIFY LAST NAME
        // -----------------------------------------------------

        String actualLastName =
                getResponse
                        .jsonPath()
                        .getString("data.lastName");

        Assert.assertEquals(
                actualLastName,
                UPDATED_LAST_NAME,
                "Last Name was not updated correctly"
        );

        // -----------------------------------------------------
        // FINAL RESULT
        // -----------------------------------------------------

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "EMPLOYEE UPDATE API TEST PASSED"
        );

        System.out.println(
                "Employee Number: "
                + employeeNumber
        );

        System.out.println(
                "Updated First Name: "
                + actualFirstName
        );

        System.out.println(
                "Updated Middle Name: "
                + actualMiddleName
        );

        System.out.println(
                "Updated Last Name: "
                + actualLastName
        );

        System.out.println(
                "=============================================="
        );
    }
}