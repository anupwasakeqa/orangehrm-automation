package com.orangehrm.tests;

import static io.restassured.RestAssured.given;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
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

    /*
     * ThreadLocal is important because TestNG is running tests in parallel.
     * Each test gets its own WebDriver instance.
     */

    private ThreadLocal<WebDriver> driverThreadLocal =
            new ThreadLocal<>();

    private ThreadLocal<WebDriverWait> waitThreadLocal =
            new ThreadLocal<>();

    private ThreadLocal<String> orangeHrmCookieThreadLocal =
            new ThreadLocal<>();

    private static final String BASE_URL =
            "https://opensource-demo.orangehrmlive.com";

    private static final String LOGIN_URL =
            BASE_URL + "/web/index.php/auth/login";

    private static final String EMPLOYEE_API =
            BASE_URL + "/web/index.php/api/v2/pim/employees";

    private static final String UPDATE_PERSONAL_DETAILS_API =
            BASE_URL
                    + "/web/index.php/api/v2/pim/employees/{employeeNumber}/personal-details";

    private static final String USERNAME =
            "Admin";

    private static final String PASSWORD =
            "admin123";

    private static final String FIRST_NAME =
            "Anup";

    private static final String MIDDLE_NAME =
            "Test";

    private static final String LAST_NAME =
            "Employee";

    private static final String UPDATED_FIRST_NAME =
            "AnupUpdated";

    private static final String UPDATED_MIDDLE_NAME =
            "Automation";

    private static final String UPDATED_LAST_NAME =
            "EmployeeUpdated";

    // =========================================================
    // DRIVER
    // =========================================================

    private WebDriver getDriver() {

        return driverThreadLocal.get();
    }

    // =========================================================
    // WAIT
    // =========================================================

    private WebDriverWait getWait() {

        return waitThreadLocal.get();
    }

    // =========================================================
    // COOKIE
    // =========================================================

    private String getOrangeHrmCookie() {

        return orangeHrmCookieThreadLocal.get();
    }

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        System.out.println(
                "================================================"
        );

        System.out.println(
                "EMPLOYEE API TEST SETUP STARTED"
        );

        System.out.println(
                "================================================"
        );

        ChromeOptions options =
                new ChromeOptions();

        // =====================================================
        // PAGE LOAD STRATEGY
        // =====================================================

        /*
         * IMPORTANT:
         *
         * OrangeHRM is a JavaScript SPA.
         * EAGER prevents Selenium from waiting unnecessarily
         * for all renderer/network activity.
         */
        options.setPageLoadStrategy(
                PageLoadStrategy.EAGER
        );

        // =====================================================
        // GITHUB ACTIONS / CI ENVIRONMENT
        // =====================================================

        String ci =
                System.getenv("CI");

        if ("true".equalsIgnoreCase(ci)) {

            System.out.println(
                    "CI environment detected."
            );

            options.addArguments(
                    "--headless=new"
            );

            options.addArguments(
                    "--no-sandbox"
            );

            options.addArguments(
                    "--disable-dev-shm-usage"
            );

            options.addArguments(
                    "--disable-gpu"
            );

            options.addArguments(
                    "--window-size=1920,1080"
            );

            options.addArguments(
                    "--disable-software-rasterizer"
            );

            options.addArguments(
                    "--disable-extensions"
            );

            options.addArguments(
                    "--disable-background-networking"
            );

            options.addArguments(
                    "--disable-background-timer-throttling"
            );

            options.addArguments(
                    "--disable-renderer-backgrounding"
            );

            options.addArguments(
                    "--disable-features=Translate,BackForwardCache"
            );

            System.out.println(
                    "Running Chrome in headless CI mode."
            );

        } else {

            System.out.println(
                    "Local environment detected."
            );
        }

        // =====================================================
        // COMMON CHROME OPTIONS
        // =====================================================

        options.addArguments(
                "--disable-notifications"
        );

        options.addArguments(
                "--disable-popup-blocking"
        );

        options.addArguments(
                "--remote-allow-origins=*"
        );

        options.addArguments(
                "--disable-infobars"
        );

        // =====================================================
        // CREATE DRIVER
        // =====================================================

        WebDriver driver =
                new ChromeDriver(options);

        driverThreadLocal.set(driver);

        // =====================================================
        // WAIT
        // =====================================================

        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(30)
                );

        waitThreadLocal.set(wait);

        // =====================================================
        // TIMEOUTS
        // =====================================================

        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(5)
                );

        driver.manage()
                .timeouts()
                .pageLoadTimeout(
                        Duration.ofSeconds(45)
                );

        // =====================================================
        // WINDOW
        // =====================================================

        if (!"true".equalsIgnoreCase(ci)) {

            driver.manage()
                    .window()
                    .maximize();
        }

        // =====================================================
        // OPEN LOGIN PAGE
        // =====================================================

        openLoginPage();

        System.out.println(
                "EMPLOYEE API TEST SETUP COMPLETED"
        );

        System.out.println(
                "================================================"
        );
    }

    // =========================================================
    // TEARDOWN
    // =========================================================

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        WebDriver driver =
                driverThreadLocal.get();

        try {

            if (driver != null) {

                System.out.println(
                        "Closing API test browser..."
                );

                driver.quit();

                System.out.println(
                        "API test browser closed successfully."
                );
            }

        } finally {

            driverThreadLocal.remove();

            waitThreadLocal.remove();

            orangeHrmCookieThreadLocal.remove();
        }
    }

    // =========================================================
    // OPEN LOGIN PAGE
    // =========================================================

    private void openLoginPage() {

        WebDriver driver =
                getDriver();

        WebDriverWait wait =
                getWait();

        System.out.println(
                "Opening OrangeHRM login page..."
        );

        int maxAttempts = 3;

        for (int attempt = 1;
                attempt <= maxAttempts;
                attempt++) {

            try {

                System.out.println(
                        "Login page navigation attempt "
                                + attempt
                                + " of "
                                + maxAttempts
                );

                driver.get(LOGIN_URL);

                /*
                 * EAGER strategy returns after DOMContentLoaded.
                 * Now explicitly wait for the actual login field.
                 */

                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.name("username")
                        )
                );

                System.out.println(
                        "OrangeHRM login page loaded."
                );

                System.out.println(
                        "Current URL: "
                                + driver.getCurrentUrl()
                );

                return;

            } catch (Exception e) {

                System.out.println(
                        "Login page navigation attempt "
                                + attempt
                                + " failed."
                );

                System.out.println(
                        "Navigation error: "
                                + e.getMessage()
                );

                if (attempt == maxAttempts) {

                    throw e;
                }

                try {

                    Thread.sleep(2000);

                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            "Thread interrupted during login page retry.",
                            interruptedException
                    );
                }
            }
        }
    }

    // =========================================================
    // LOGIN
    // =========================================================

    private void login() {

        WebDriver driver =
                getDriver();

        WebDriverWait wait =
                getWait();

        System.out.println(
                "Starting OrangeHRM login..."
        );

        /*
         * Login can sometimes refresh the DOM in CI.
         * Therefore locate elements immediately before using them.
         */

        for (int attempt = 1; attempt <= 3; attempt++) {

            try {

                wait.until(
                        ExpectedConditions.urlContains(
                                "/auth/login"
                        )
                );

                WebElement usernameField =
                        wait.until(
                                ExpectedConditions.refreshed(
                                        ExpectedConditions.visibilityOfElementLocated(
                                                By.name("username")
                                        )
                                )
                        );

                usernameField.clear();

                usernameField.sendKeys(
                        USERNAME
                );

                WebElement passwordField =
                        wait.until(
                                ExpectedConditions.refreshed(
                                        ExpectedConditions.visibilityOfElementLocated(
                                                By.name("password")
                                        )
                                )
                        );

                passwordField.clear();

                passwordField.sendKeys(
                        PASSWORD
                );

                WebElement loginButton =
                        wait.until(
                                ExpectedConditions.refreshed(
                                        ExpectedConditions.elementToBeClickable(
                                                By.cssSelector(
                                                        "button[type='submit']"
                                                )
                                        )
                                )
                        );

                loginButton.click();

                wait.until(
                        ExpectedConditions.urlContains(
                                "/dashboard"
                        )
                );

                break;

            } catch (Exception e) {

                if (attempt == 3) {

                    throw e;
                }

                System.out.println(
                        "Login attempt "
                                + attempt
                                + " failed. Retrying..."
                );

                try {

                    Thread.sleep(1500);

                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            "Thread interrupted during login retry.",
                            interruptedException
                    );
                }
            }
        }

        // =====================================================
        // WAIT FOR ORANGEHRM COOKIE
        // =====================================================

        wait.until(
                driverInstance ->
                        driverInstance
                                .manage()
                                .getCookieNamed("orangehrm") != null
        );

        String cookie =
                driver.manage()
                        .getCookieNamed("orangehrm")
                        .getValue();

        Assert.assertNotNull(
                cookie,
                "OrangeHRM cookie was not created"
        );

        orangeHrmCookieThreadLocal.set(
                cookie
        );

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

        return
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .header(
                                "X-Requested-With",
                                "XMLHttpRequest"
                        )
                        .cookie(
                                "orangehrm",
                                getOrangeHrmCookie()
                        )
                        .body(requestBody)
                .when()
                        .post(EMPLOYEE_API)
                .then()
                        .extract()
                        .response();
    }

    // =========================================================
    // GET ALL EMPLOYEES API
    // =========================================================

    private Response getEmployeesApi() {

        return
                given()
                        .accept(ContentType.JSON)
                        .header(
                                "X-Requested-With",
                                "XMLHttpRequest"
                        )
                        .cookie(
                                "orangehrm",
                                getOrangeHrmCookie()
                        )
                .when()
                        .get(EMPLOYEE_API)
                .then()
                        .extract()
                        .response();
    }

    // =========================================================
    // GET SINGLE EMPLOYEE API
    // =========================================================

    private Response getEmployeeApi(
            int employeeNumber) {

        return
                given()
                        .accept(ContentType.JSON)
                        .header(
                                "X-Requested-With",
                                "XMLHttpRequest"
                        )
                        .cookie(
                                "orangehrm",
                                getOrangeHrmCookie()
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
    // UPDATE EMPLOYEE API
    // =========================================================

    private Response updateEmployeeViaApi(
            int employeeNumber,
            String employeeId,
            String firstName,
            String middleName,
            String lastName) {

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

        return
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .header(
                                "X-Requested-With",
                                "XMLHttpRequest"
                        )
                        .cookie(
                                "orangehrm",
                                getOrangeHrmCookie()
                        )
                        .body(requestBody)
                .when()
                        .put(updateUrl)
                .then()
                        .extract()
                        .response();
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

        System.out.println(
                "GET ALL EMPLOYEES TEST PASSED"
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
                        .getInt(
                                "data[0].empNumber"
                        );

        Assert.assertTrue(
                employeeNumber > 0,
                "Employee number was not found"
        );

        System.out.println(
                "Employee Number: "
                        + employeeNumber
        );

        Response response =
                getEmployeeApi(
                        employeeNumber
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "GET Single Employee API failed"
        );

        System.out.println(
                "GET SINGLE EMPLOYEE TEST PASSED"
        );
    }

    // =========================================================
    // TEST 3 - CREATE EMPLOYEE
    // =========================================================

    @Test
    public void createEmployeeTest() {

        login();

        String unique =
                String.valueOf(
                        System.currentTimeMillis()
                                % 100000
                );

        String firstName =
                "Anup" + unique;

        String middleName =
                "Test" + unique;

        String lastName =
                "Employee" + unique;

        Response response =
                createEmployeeViaApi(
                        firstName,
                        middleName,
                        lastName
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
                        .getInt(
                                "data.empNumber"
                        );

        Assert.assertTrue(
                employeeNumber > 0,
                "Employee number was not generated"
        );

        System.out.println(
                "CREATE EMPLOYEE TEST PASSED"
        );
    }

    // =========================================================
    // TEST 4 - CREATE + UPDATE + GET VERIFY
    // =========================================================

    @Test
    public void updateEmployeeTest() {

        login();

        // -----------------------------------------------------
        // STEP 1 - CREATE EMPLOYEE
        // -----------------------------------------------------

        String unique =
                String.valueOf(
                        System.currentTimeMillis()
                                % 100000
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
        // STEP 2 - EMPLOYEE NUMBER
        // -----------------------------------------------------

        int employeeNumber =
                createResponse
                        .jsonPath()
                        .getInt(
                                "data.empNumber"
                        );

        Assert.assertTrue(
                employeeNumber > 0,
                "Invalid employee number"
        );

        System.out.println(
                "Internal Employee Number: "
                        + employeeNumber
        );

        // -----------------------------------------------------
        // STEP 3 - EMPLOYEE ID
        // -----------------------------------------------------

        String employeeId =
                createResponse
                        .jsonPath()
                        .getString(
                                "data.employeeId"
                        );

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
        // STEP 4 - PUT UPDATE
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

        Assert.assertEquals(
                updateResponse.statusCode(),
                200,
                "PUT Employee API failed"
        );

        // -----------------------------------------------------
        // STEP 5 - GET AFTER UPDATE
        // -----------------------------------------------------

        Response getResponse =
                getEmployeeApi(
                        employeeNumber
                );

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
        // STEP 6 - VERIFY FIRST NAME
        // -----------------------------------------------------

        String actualFirstName =
                getResponse
                        .jsonPath()
                        .getString(
                                "data.firstName"
                        );

        Assert.assertEquals(
                actualFirstName,
                UPDATED_FIRST_NAME,
                "First Name was not updated correctly"
        );

        // -----------------------------------------------------
        // STEP 7 - VERIFY MIDDLE NAME
        // -----------------------------------------------------

        String actualMiddleName =
                getResponse
                        .jsonPath()
                        .getString(
                                "data.middleName"
                        );

        Assert.assertEquals(
                actualMiddleName,
                UPDATED_MIDDLE_NAME,
                "Middle Name was not updated correctly"
        );

        // -----------------------------------------------------
        // STEP 8 - VERIFY LAST NAME
        // -----------------------------------------------------

        String actualLastName =
                getResponse
                        .jsonPath()
                        .getString(
                                "data.lastName"
                        );

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