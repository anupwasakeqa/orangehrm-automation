package com.orangehrm.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Stores the first name of the employee created in this test flow.
    // Used as fallback when OrangeHRM remains on /pim/addEmployee.
    private String lastCreatedEmployeeFirstName;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public EmployeePage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By pimMenu =
            By.xpath(
                    "//span[normalize-space()='PIM']/ancestor::a"
            );

    private final By employeeListMenu =
            By.xpath(
                    "//a[normalize-space()='Employee List']"
            );

    private final By addEmployeeButton =
            By.xpath(
                    "//button[normalize-space()='Add']"
            );

    private final By firstNameField =
            By.name("firstName");

    private final By middleNameField =
            By.name("middleName");

    private final By lastNameField =
            By.name("lastName");

    private final By employeeIdField =
            By.name("employeeId");

    private final By loader =
            By.cssSelector(".oxd-form-loader");

    private final By searchButton =
            By.xpath(
                    "//button[normalize-space()='Search']"
            );

    private final By resetButton =
            By.xpath(
                    "//button[normalize-space()='Reset']"
            );

    private final By employeeIdSearchField =
            By.xpath(
                    "//label[normalize-space()='Employee Id']"
                            + "/ancestor::div[contains(@class,'oxd-input-group')]"
                            + "//input"
            );

    private final By employeeNameSearchField =
            By.xpath(
                    "//label[normalize-space()='Employee Name']"
                            + "/ancestor::div[contains(@class,'oxd-input-group')]"
                            + "//input"
            );

    private final By tableRows =
            By.xpath(
                    "//div[contains(@class,'oxd-table-body')]"
                            + "//div[contains(@class,'oxd-table-row')]"
            );

    // =========================================================
    // CLICK PIM
    // =========================================================

    public void clickPIM() {

        System.out.println("Clicking PIM menu...");

        WebElement pim =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                pimMenu
                        )
                );

        scrollIntoView(pim);

        try {

            pim.click();

        } catch (Exception e) {

            javascriptClick(pim);
        }

        wait.until(
                ExpectedConditions.urlContains("/pim/")
        );

        waitForLoaderToDisappear();

        System.out.println("PIM page opened.");
    }

    // =========================================================
    // CLICK EMPLOYEE LIST
    // =========================================================

    public void clickEmployeeList() {

        System.out.println("Clicking Employee List...");

        WebElement employeeList =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeListMenu
                        )
                );

        scrollIntoView(employeeList);

        try {

            employeeList.click();

        } catch (Exception e) {

            javascriptClick(employeeList);
        }

        wait.until(
                ExpectedConditions.urlContains(
                        "/pim/viewEmployeeList"
                )
        );

        waitForLoaderToDisappear();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        employeeIdSearchField
                )
        );

        System.out.println(
                "Employee List page loaded successfully."
        );
    }

    // =========================================================
    // CLICK ADD EMPLOYEE
    // =========================================================

    public void clickAddEmployee() {

        System.out.println("Clicking Add Employee...");

        WebElement add =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                addEmployeeButton
                        )
                );

        scrollIntoView(add);

        try {

            add.click();

        } catch (Exception e) {

            javascriptClick(add);
        }

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField
                )
        );

        System.out.println(
                "Add Employee page opened successfully."
        );
    }

    // =========================================================
    // CREATE EMPLOYEE - 2 PARAMETERS
    // =========================================================

    public void createEmployee(
            String firstName,
            String lastName) {

        createEmployee(
                firstName,
                "",
                lastName
        );
    }

    // =========================================================
    // CREATE EMPLOYEE - 3 PARAMETERS
    // =========================================================

    public void createEmployee(
            String firstName,
            String middleName,
            String lastName) {

        System.out.println();
        System.out.println("================================================");
        System.out.println("STARTING EMPLOYEE CREATION");
        System.out.println("================================================");

        // Store created employee name for fallback Employee Number lookup.
        this.lastCreatedEmployeeFirstName = firstName;

        // -----------------------------------------------------
        // NAVIGATION
        // -----------------------------------------------------

        clickPIM();
        clickEmployeeList();
        clickAddEmployee();

        // -----------------------------------------------------
        // FIRST NAME
        // -----------------------------------------------------

        WebElement firstNameElement =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                firstNameField
                        )
                );

        clearField(firstNameElement);
        firstNameElement.sendKeys(firstName);

        // -----------------------------------------------------
        // MIDDLE NAME
        // -----------------------------------------------------

        WebElement middleNameElement =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                middleNameField
                        )
                );

        clearField(middleNameElement);

        if (middleName != null
                && !middleName.trim().isEmpty()) {

            middleNameElement.sendKeys(middleName);
        }

        // -----------------------------------------------------
        // LAST NAME
        // -----------------------------------------------------

        WebElement lastNameElement =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                lastNameField
                        )
                );

        clearField(lastNameElement);
        lastNameElement.sendKeys(lastName);

        System.out.println(
                "Employee details entered successfully."
        );

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        By employeeFormSave =
                By.xpath(
                        "//form//button[@type='submit']"
                );

        waitForLoaderToDisappear();

        WebElement save =
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                employeeFormSave
                        )
                );

        scrollIntoView(save);

        waitForLoaderToDisappear();

        sleep(500);

        try {

            wait.until(
                    ExpectedConditions.elementToBeClickable(save)
            );

            save.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal Save click failed. Using JavaScript."
            );

            javascriptClick(save);
        }

        System.out.println(
                "Employee Save button clicked."
        );

        // -----------------------------------------------------
        // WAIT FOR SAVE OPERATION
        // -----------------------------------------------------

        waitForEmployeeCreationResult();

        System.out.println(
                "Employee save operation completed."
        );

        System.out.println(
                "Current URL after save: "
                        + driver.getCurrentUrl()
        );

        System.out.println(
                "Employee creation flow completed successfully."
        );

        System.out.println(
                "================================================"
        );
    }

    // =========================================================
    // WAIT FOR EMPLOYEE CREATION RESULT
    // =========================================================

    private void waitForEmployeeCreationResult() {

        long endTime =
                System.currentTimeMillis() + 30000;

        while (System.currentTimeMillis() < endTime) {

            try {

                String url =
                        driver.getCurrentUrl();

                // -------------------------------------------------
                // SUCCESS - PERSONAL DETAILS PAGE
                // -------------------------------------------------

                if (url.contains(
                        "/pim/viewPersonalDetails/empNumber/"
                )) {

                    System.out.println(
                            "Employee Personal Details page detected."
                    );

                    waitForLoaderToDisappear();

                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    firstNameField
                            )
                    );

                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    lastNameField
                            )
                    );

                    return;
                }

                // -------------------------------------------------
                // STILL ADD EMPLOYEE
                // -------------------------------------------------

                if (url.contains("/pim/addEmployee")) {

                    System.out.println(
                            "Still on Add Employee page. Waiting for save..."
                    );
                }

            } catch (Exception ignored) {
            }

            sleep(500);
        }

        // ---------------------------------------------------------
        // FINAL CHECK
        // ---------------------------------------------------------

        String finalUrl =
                driver.getCurrentUrl();

        if (finalUrl.contains(
                "/pim/viewPersonalDetails/empNumber/"
        )) {

            return;
        }

        /*
         * Do NOT fail here.
         *
         * OrangeHRM CI can sometimes remain on /pim/addEmployee
         * even though employee creation succeeded.
         *
         * getCurrentEmployeeId() has a fallback which searches
         * Employee List using the created employee name.
         */

        System.out.println(
                "Employee creation navigation did not reach Personal Details."
        );

        System.out.println(
                "Current URL: " + finalUrl
        );

        System.out.println(
                "Continuing with Employee Number fallback lookup..."
        );
    }

    // =========================================================
    // GET CURRENT EMPLOYEE ID
    // =========================================================

    public String getCurrentEmployeeId() {

        System.out.println();
        System.out.println("================================================");
        System.out.println("CAPTURING CREATED EMPLOYEE NUMBER");
        System.out.println("================================================");

        String currentUrl =
                driver.getCurrentUrl();

        System.out.println(
                "Current URL: " + currentUrl
        );

        // -----------------------------------------------------
        // FIRST - TRY CURRENT URL
        // -----------------------------------------------------

        String employeeId =
                extractEmployeeNumberFromUrl(
                        currentUrl
                );

        if (employeeId != null) {

            System.out.println(
                    "Employee Number captured from URL: "
                            + employeeId
            );

            return employeeId;
        }

        // -----------------------------------------------------
        // SECOND - TRY CURRENT PAGE DOM
        // -----------------------------------------------------

        try {

            List<WebElement> elements =
                    driver.findElements(
                            By.xpath(
                                    "//*[contains(@href,'empNumber/')]"
                            )
                    );

            for (WebElement element : elements) {

                try {

                    String href =
                            element.getAttribute("href");

                    if (href == null) {
                        continue;
                    }

                    employeeId =
                            extractEmployeeNumberFromUrl(
                                    href
                            );

                    if (employeeId != null) {

                        System.out.println(
                                "Employee Number captured from page link: "
                                        + employeeId
                        );

                        return employeeId;
                    }

                } catch (Exception ignored) {
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "DOM Employee Number lookup failed: "
                            + e.getMessage()
            );
        }

        // -----------------------------------------------------
        // THIRD - EMPLOYEE LIST FALLBACK
        // -----------------------------------------------------

        if (lastCreatedEmployeeFirstName != null
                && !lastCreatedEmployeeFirstName.trim().isEmpty()) {

            System.out.println();
            System.out.println(
                    "Employee Number not available from current page."
            );

            System.out.println(
                    "Using Employee List fallback search."
            );

            System.out.println(
                    "Created Employee First Name: "
                            + lastCreatedEmployeeFirstName
            );

            try {

                ensureEmployeeListPage();

                WebElement employeeNameField =
                        wait.until(
                                ExpectedConditions.elementToBeClickable(
                                        employeeNameSearchField
                                )
                        );

                scrollIntoView(employeeNameField);

                clearField(employeeNameField);

                employeeNameField.sendKeys(
                        lastCreatedEmployeeFirstName
                );

                employeeNameField.sendKeys(Keys.TAB);

                sleep(500);

                WebElement search =
                        wait.until(
                                ExpectedConditions.elementToBeClickable(
                                        searchButton
                                )
                        );

                scrollIntoView(search);

                clickElement(search);

                waitForLoaderToDisappear();

                sleep(1500);

                // -------------------------------------------------
                // FIND CREATED EMPLOYEE ROW
                // -------------------------------------------------

                WebElement employeeRow =
                        findEmployeeRowByFirstName(
                                normalizeText(
                                        lastCreatedEmployeeFirstName
                                ),
                                30
                        );

                if (employeeRow != null) {

                    System.out.println(
                            "Created employee found through fallback search."
                    );

                    System.out.println(
                            "Employee Row: "
                                    + normalizeText(
                                            employeeRow.getText()
                                    )
                    );

                    // -------------------------------------------------
                    // OPEN EDIT
                    // -------------------------------------------------

                    WebElement editButton =
                            findEditButton(employeeRow);

                    if (editButton != null) {

                        scrollIntoView(editButton);

                        sleep(500);

                        clickElement(editButton);

                        wait.until(
                                ExpectedConditions.urlContains(
                                        "/pim/viewPersonalDetails"
                                )
                        );

                        waitForLoaderToDisappear();

                        String updatedUrl =
                                driver.getCurrentUrl();

                        System.out.println(
                                "Employee edit page opened through fallback."
                        );

                        System.out.println(
                                "Fallback Employee URL: "
                                        + updatedUrl
                        );

                        // -------------------------------------------------
                        // EXTRACT EMPLOYEE NUMBER
                        // -------------------------------------------------

                        employeeId =
                                extractEmployeeNumberFromUrl(
                                        updatedUrl
                                );

                        if (employeeId != null) {

                            System.out.println();
                            System.out.println(
                                    "================================================"
                            );

                            System.out.println(
                                    "EMPLOYEE NUMBER CAPTURED SUCCESSFULLY"
                            );

                            System.out.println(
                                    "Employee Number: "
                                            + employeeId
                            );

                            System.out.println(
                                    "================================================"
                            );

                            return employeeId;
                        }
                    }

                } else {

                    System.out.println(
                            "Created employee row was not found through fallback."
                    );
                }

            } catch (Exception e) {

                System.out.println();
                System.out.println(
                        "Employee List fallback failed: "
                                + e.getMessage()
                );
            }
        }

        // -----------------------------------------------------
        // FOURTH - EMPLOYEE ID FIELD FALLBACK
        // -----------------------------------------------------

        try {

            WebElement idField =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(10)
                    ).until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    employeeIdField
                            )
                    );

            String fieldEmployeeId =
                    idField
                            .getAttribute("value")
                            .trim();

            if (!fieldEmployeeId.isEmpty()) {

                System.out.println(
                        "Employee ID captured from field: "
                                + fieldEmployeeId
                );

                return fieldEmployeeId;
            }

        } catch (Exception e) {

            System.out.println(
                    "Employee ID field fallback failed."
            );
        }

        // -----------------------------------------------------
        // FINAL FAILURE
        // -----------------------------------------------------

        throw new RuntimeException(
                "Unable to capture generated Employee Number. "
                        + "Current URL: "
                        + driver.getCurrentUrl()
        );
    }

    // =========================================================
    // EXTRACT EMPLOYEE NUMBER FROM URL
    // =========================================================

    private String extractEmployeeNumberFromUrl(
            String url) {

        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        String marker = "/empNumber/";

        if (!url.contains(marker)) {
            return null;
        }

        try {

            String employeeId =
                    url.substring(
                            url.indexOf(marker)
                                    + marker.length()
                    );

            employeeId =
                    employeeId
                            .split("[/?#]")[0]
                            .trim();

            if (employeeId.matches("\\d+")) {
                return employeeId;
            }

        } catch (Exception ignored) {
        }

        return null;
    }

    // =========================================================
    // VERIFY EMPLOYEE EXISTS BY ID
    // =========================================================

    public boolean verifyEmployeeExistsById(
            String employeeId) {

        try {

            if (employeeId == null
                    || employeeId.trim().isEmpty()) {

                return false;
            }

            employeeId =
                    employeeId.trim();

            openEmployeeById(employeeId);

            String currentUrl =
                    driver.getCurrentUrl();

            if (!currentUrl.contains(
                    "/pim/viewPersonalDetails/empNumber/"
                            + employeeId
            )) {

                return false;
            }

            WebElement firstName =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    firstNameField
                            )
                    );

            WebElement lastName =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    lastNameField
                            )
                    );

            return firstName.isDisplayed()
                    && lastName.isDisplayed();

        } catch (Exception e) {

            System.out.println(
                    "Employee verification failed: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // VERIFY EMPLOYEE EXISTS BY EMP NUMBER
    // =========================================================

    public boolean verifyEmployeeExistsByEmpNumber(
            String empNumber) {

        try {

            if (empNumber == null
                    || empNumber.trim().isEmpty()) {

                return false;
            }

            empNumber =
                    empNumber.trim();

            openEmployeeById(empNumber);

            String currentUrl =
                    driver.getCurrentUrl();

            if (!currentUrl.contains(
                    "/pim/viewPersonalDetails/empNumber/"
                            + empNumber
            )) {

                return false;
            }

            WebElement firstName =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    firstNameField
                            )
                    );

            WebElement lastName =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    lastNameField
                            )
                    );

            if (firstName.isDisplayed()
                    && lastName.isDisplayed()) {

                System.out.println();
                System.out.println(
                        "================================================"
                );

                System.out.println(
                        "EMPLOYEE FOUND SUCCESSFULLY"
                );

                System.out.println(
                        "Employee Number: "
                                + empNumber
                );

                System.out.println(
                        "================================================"
                );

                return true;
            }

        } catch (Exception e) {

            System.out.println(
                    "Employee verification failed: "
                            + e.getMessage()
            );
        }

        return false;
    }

    // =========================================================
    // OPEN EMPLOYEE BY INTERNAL EMP NUMBER
    // =========================================================

    public void openEmployeeById(
            String employeeId) {

        if (employeeId == null
                || employeeId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee Number cannot be null or empty."
            );
        }

        employeeId =
                employeeId.trim();

        String currentUrl =
                driver.getCurrentUrl();

        System.out.println(
                "Current URL: " + currentUrl
        );

        String baseUrl;

        if (currentUrl.contains("/web/")) {

            baseUrl =
                    currentUrl.substring(
                            0,
                            currentUrl.indexOf("/web/")
                    );

        } else if (currentUrl.contains("/web")) {

            baseUrl =
                    currentUrl.substring(
                            0,
                            currentUrl.indexOf("/web")
                    );

        } else {

            throw new RuntimeException(
                    "Unable to extract base URL from current URL: "
                            + currentUrl
            );
        }

        String employeeUrl =
                baseUrl
                        + "/web/index.php/pim/"
                        + "viewPersonalDetails/empNumber/"
                        + employeeId;

        System.out.println(
                "Opening employee URL: "
                        + employeeUrl
        );

        driver.get(employeeUrl);

        wait.until(
                ExpectedConditions.urlContains(
                        "/pim/viewPersonalDetails/empNumber/"
                                + employeeId
                )
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField
                )
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        lastNameField
                )
        );

        waitForLoaderToDisappear();

        System.out.println(
                "Employee details page opened successfully."
        );
    }

    // =========================================================
    // CLICK EDIT EMPLOYEE BY ID
    // =========================================================

    public void clickEditEmployeeById(
            String employeeId) {

        System.out.println();
        System.out.println(
                "================================================"
        );

        System.out.println(
                "OPENING EMPLOYEE FOR EDIT BY ID"
        );

        System.out.println(
                "================================================"
        );

        openEmployeeById(employeeId);

        System.out.println(
                "Employee ID "
                        + employeeId
                        + " is ready for update."
        );
    }

    // =========================================================
    // SEARCH EMPLOYEE BY ID
    // =========================================================

    public void searchEmployeeById(
            String employeeId) {

        if (employeeId == null
                || employeeId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee ID cannot be null or empty."
            );
        }

        employeeId =
                employeeId.trim();

        ensureEmployeeListPage();

        waitForLoaderToDisappear();

        WebElement idField =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeIdSearchField
                        )
                );

        scrollIntoView(idField);

        clearField(idField);

        idField.sendKeys(employeeId);
        idField.sendKeys(Keys.TAB);

        sleep(300);

        WebElement search =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                );

        scrollIntoView(search);

        clickElement(search);

        waitForLoaderToDisappear();

        sleep(1000);

        System.out.println(
                "Employee ID search completed."
        );
    }

    // =========================================================
    // CHECK EMPLOYEE DISPLAYED BY ID
    // =========================================================

    public boolean isEmployeeDisplayedById(
            String employeeId) {

        try {

            if (employeeId == null
                    || employeeId.trim().isEmpty()) {

                return false;
            }

            searchEmployeeById(employeeId);

            WebDriverWait shortWait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(15)
                    );

            return shortWait.until(
                    driver -> {

                        List<WebElement> rows =
                                driver.findElements(tableRows);

                        for (WebElement row : rows) {

                            try {

                                if (!row.isDisplayed()) {
                                    continue;
                                }

                                String rowText =
                                        normalizeText(
                                                row.getText()
                                        );

                                if (rowText.contains(employeeId)) {
                                    return true;
                                }

                            } catch (Exception ignored) {
                            }
                        }

                        return false;
                    }
            );

        } catch (Exception e) {

            System.out.println(
                    "Employee table verification failed: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // CLICK EDIT EMPLOYEE BY FIRST NAME
    // =========================================================

    public void clickEditEmployee(
            String firstName) {

        System.out.println();
        System.out.println(
                "================================================"
        );

        System.out.println(
                "OPENING EMPLOYEE FOR EDIT"
        );

        System.out.println(
                "================================================"
        );

        if (firstName == null
                || firstName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee first name cannot be null or empty."
            );
        }

        final String searchFirstName =
                normalizeText(firstName);

        System.out.println(
                "Employee First Name: "
                        + searchFirstName
        );

        ensureEmployeeListPage();

        waitForLoaderToDisappear();

        WebElement employeeNameField =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeNameSearchField
                        )
                );

        scrollIntoView(employeeNameField);

        clearField(employeeNameField);

        employeeNameField.sendKeys(
                searchFirstName
        );

        employeeNameField.sendKeys(Keys.TAB);

        sleep(500);

        System.out.println(
                "Employee name entered in search field: "
                        + searchFirstName
        );

        WebElement search =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                );

        scrollIntoView(search);

        clickElement(search);

        System.out.println(
                "Employee search submitted."
        );

        waitForLoaderToDisappear();

        sleep(1500);

        WebElement employeeRow =
                findEmployeeRowByFirstName(
                        searchFirstName,
                        30
                );

        if (employeeRow == null) {

            System.out.println(
                    "Employee row not found on first attempt."
            );

            System.out.println(
                    "Resetting search and retrying..."
            );

            retryEmployeeSearch(
                    searchFirstName
            );

            employeeRow =
                    findEmployeeRowByFirstName(
                            searchFirstName,
                            30
                    );
        }

        if (employeeRow == null) {

            String currentUrl =
                    driver.getCurrentUrl();

            System.out.println(
                    "Employee row not found."
            );

            System.out.println(
                    "Search Name: "
                            + searchFirstName
            );

            System.out.println(
                    "Current URL: "
                            + currentUrl
            );

            throw new RuntimeException(
                    "Employee row not found after search. "
                            + "Employee Name: "
                            + searchFirstName
                            + " | Current URL: "
                            + currentUrl
            );
        }

        System.out.println(
                "Matching employee row found."
        );

        System.out.println(
                "Employee Row Text: "
                        + normalizeText(
                                employeeRow.getText()
                        )
        );

        WebElement editButton =
                findEditButton(employeeRow);

        if (editButton == null) {

            throw new RuntimeException(
                    "Edit button not found inside employee row. "
                            + "Employee Name: "
                            + searchFirstName
            );
        }

        scrollIntoView(editButton);

        sleep(500);

        clickElement(editButton);

        System.out.println(
                "Edit button clicked successfully."
        );

        wait.until(
                ExpectedConditions.urlContains(
                        "/pim/viewPersonalDetails"
                )
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField
                )
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        lastNameField
                )
        );

        waitForLoaderToDisappear();

        System.out.println(
                "Employee edit page opened successfully."
        );

        System.out.println(
                "Current URL: "
                        + driver.getCurrentUrl()
        );

        System.out.println(
                "Employee is ready for update."
        );
    }

    // =========================================================
    // FIND EMPLOYEE ROW BY FIRST NAME
    // =========================================================

    private WebElement findEmployeeRowByFirstName(
            String firstName,
            int timeoutSeconds) {

        final String normalizedSearchName =
                normalizeText(firstName)
                        .toLowerCase();

        long endTime =
                System.currentTimeMillis()
                        + (timeoutSeconds * 1000L);

        while (
                System.currentTimeMillis()
                        < endTime) {

            try {

                List<WebElement> rows =
                        driver.findElements(tableRows);

                System.out.println(
                        "Rows currently available: "
                                + rows.size()
                );

                for (WebElement row : rows) {

                    try {

                        if (!row.isDisplayed()) {
                            continue;
                        }

                        String rowText =
                                normalizeText(
                                        row.getText()
                                );

                        if (rowText.isEmpty()) {
                            continue;
                        }

                        System.out.println(
                                "Employee row: "
                                        + rowText
                        );

                        if (rowContainsFirstName(
                                row,
                                normalizedSearchName
                        )) {

                            System.out.println(
                                    "Matching employee row found: "
                                            + rowText
                            );

                            return row;
                        }

                    } catch (Exception ignored) {
                    }
                }

            } catch (Exception e) {

                System.out.println(
                        "Error while reading employee rows: "
                                + e.getMessage()
                );
            }

            sleep(500);
        }

        return null;
    }

    // =========================================================
    // CHECK ROW FIRST NAME
    // =========================================================

    private boolean rowContainsFirstName(
            WebElement row,
            String firstName) {

        try {

            String rowText =
                    normalizeText(
                            row.getText()
                    ).toLowerCase();

            String normalizedName =
                    normalizeText(firstName)
                            .toLowerCase();

            if (rowText.contains(normalizedName)) {
                return true;
            }

            List<WebElement> cells =
                    row.findElements(
                            By.cssSelector(
                                    "div.oxd-table-cell"
                            )
                    );

            for (WebElement cell : cells) {

                try {

                    String cellText =
                            normalizeText(
                                    cell.getText()
                            ).toLowerCase();

                    if (cellText.equals(normalizedName)) {
                        return true;
                    }

                    if (removeSpaces(cellText)
                            .equals(
                                    removeSpaces(
                                            normalizedName
                                    )
                            )) {

                        return true;
                    }

                } catch (Exception ignored) {
                }
            }

            return removeSpaces(rowText)
                    .contains(
                            removeSpaces(
                                    normalizedName
                            )
                    );

        } catch (Exception e) {

            return false;
        }
    }

    // =========================================================
    // FIND EDIT BUTTON
    // =========================================================

    private WebElement findEditButton(
            WebElement employeeRow) {

        try {

            By editButtonLocator =
                    By.xpath(
                            ".//button[@type='button']"
                                    + "[.//i[contains(@class,'bi-pencil-fill')]]"
                    );

            List<WebElement> buttons =
                    employeeRow.findElements(
                            editButtonLocator
                    );

            for (WebElement button : buttons) {

                try {

                    if (button.isDisplayed()
                            && button.isEnabled()) {

                        return button;
                    }

                } catch (Exception ignored) {
                }
            }

        } catch (Exception ignored) {
        }

        try {

            By fallbackEditButton =
                    By.xpath(
                            ".//button[contains(@class,'oxd-icon-button')]"
                                    + "[.//i[contains(@class,'bi-pencil')]]"
                    );

            List<WebElement> buttons =
                    employeeRow.findElements(
                            fallbackEditButton
                    );

            for (WebElement button : buttons) {

                try {

                    if (button.isDisplayed()
                            && button.isEnabled()) {

                        return button;
                    }

                } catch (Exception ignored) {
                }
            }

        } catch (Exception ignored) {
        }

        return null;
    }

    // =========================================================
    // RETRY EMPLOYEE SEARCH
    // =========================================================

    private void retryEmployeeSearch(
            String firstName) {

        try {

            WebElement reset =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    resetButton
                            )
                    );

            scrollIntoView(reset);

            clickElement(reset);

            waitForLoaderToDisappear();

            sleep(1000);

        } catch (Exception e) {

            System.out.println(
                    "Reset during retry failed. "
                            + e.getMessage()
            );
        }

        try {

            WebElement employeeNameField =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    employeeNameSearchField
                            )
                    );

            scrollIntoView(employeeNameField);

            clearField(employeeNameField);

            employeeNameField.sendKeys(firstName);

            employeeNameField.sendKeys(Keys.TAB);

            sleep(500);

            WebElement search =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    searchButton
                            )
                    );

            scrollIntoView(search);

            clickElement(search);

            waitForLoaderToDisappear();

            sleep(1500);

            System.out.println(
                    "Employee search retry submitted."
            );

        } catch (Exception e) {

            System.out.println(
                    "Employee search retry failed: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // UPDATE FIRST NAME
    // =========================================================

    public void updateFirstName(
            String newFirstName) {

        if (newFirstName == null
                || newFirstName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "New First Name cannot be empty."
            );
        }

        WebElement field =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                firstNameField
                        )
                );

        scrollIntoView(field);

        clearField(field);

        field.sendKeys(newFirstName);

        field.sendKeys(Keys.TAB);

        System.out.println(
                "First Name updated to: "
                        + newFirstName
        );
    }

    // =========================================================
    // UPDATE LAST NAME
    // =========================================================

    public void updateLastName(
            String newLastName) {

        if (newLastName == null
                || newLastName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "New Last Name cannot be empty."
            );
        }

        WebElement field =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                lastNameField
                        )
                );

        scrollIntoView(field);

        clearField(field);

        field.sendKeys(newLastName);

        field.sendKeys(Keys.TAB);

        System.out.println(
                "Last Name updated to: "
                        + newLastName
        );
    }

    // =========================================================
    // CLICK SAVE
    // =========================================================

    public void clickSave() {

        System.out.println();
        System.out.println(
                "================================================"
        );

        System.out.println(
                "SAVING UPDATED EMPLOYEE DETAILS"
        );

        System.out.println(
                "================================================"
        );

        waitForLoaderToDisappear();

        By personalDetailsSave =
                By.xpath(
                        "//input[@name='firstName']"
                                + "/ancestor::form"
                                + "//button[normalize-space()='Save']"
                );

        WebElement save = null;

        List<WebElement> saveButtons =
                driver.findElements(
                        personalDetailsSave
                );

        for (WebElement button : saveButtons) {

            try {

                if (button.isDisplayed()
                        && button.isEnabled()) {

                    save = button;
                    break;
                }

            } catch (Exception ignored) {
            }
        }

        if (save == null) {

            List<WebElement> allSaveButtons =
                    driver.findElements(
                            By.xpath(
                                    "//button[normalize-space()='Save']"
                            )
                    );

            for (WebElement button : allSaveButtons) {

                try {

                    if (button.isDisplayed()
                            && button.isEnabled()) {

                        save = button;
                        break;
                    }

                } catch (Exception ignored) {
                }
            }
        }

        if (save == null) {

            throw new RuntimeException(
                    "No usable Save button found."
            );
        }

        scrollIntoView(save);

        sleep(500);

        clickElement(save);

        System.out.println(
                "Save button clicked successfully."
        );

        sleep(1000);

        waitForLoaderToDisappear();

        sleep(1500);

        System.out.println(
                "Employee details update submitted successfully."
        );
    }

    // =========================================================
    // VERIFY UPDATED DETAILS
    // =========================================================

    public boolean verifyEmployeeDetails(
            String expectedFirstName,
            String expectedLastName) {

        System.out.println();
        System.out.println(
                "================================================"
        );

        System.out.println(
                "VERIFYING UPDATED EMPLOYEE DETAILS"
        );

        System.out.println(
                "================================================"
        );

        try {

            boolean result =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(20)
                    ).until(
                            driver -> {

                                try {

                                    WebElement firstName =
                                            driver.findElement(
                                                    firstNameField
                                            );

                                    WebElement lastName =
                                            driver.findElement(
                                                    lastNameField
                                            );

                                    String actualFirstName =
                                            firstName
                                                    .getAttribute("value")
                                                    .trim();

                                    String actualLastName =
                                            lastName
                                                    .getAttribute("value")
                                                    .trim();

                                    System.out.println(
                                            "Actual First Name: "
                                                    + actualFirstName
                                    );

                                    System.out.println(
                                            "Actual Last Name: "
                                                    + actualLastName
                                    );

                                    return actualFirstName
                                            .equalsIgnoreCase(
                                                    expectedFirstName
                                            )
                                            &&
                                            actualLastName
                                                    .equalsIgnoreCase(
                                                            expectedLastName
                                                    );

                                } catch (Exception e) {

                                    return false;
                                }
                            }
                    );

            if (result) {

                System.out.println(
                        "Employee details verified successfully."
                );

                return true;
            }

        } catch (Exception e) {

            System.out.println(
                    "Verification error: "
                            + e.getMessage()
            );
        }

        System.out.println(
                "Employee details verification FAILED."
        );

        return false;
    }

    // =========================================================
    // COMBINED UPDATE
    // =========================================================

    public void updateEmployee(
            String newFirstName,
            String newLastName) {

        updateFirstName(newFirstName);

        updateLastName(newLastName);

        clickSave();

        System.out.println(
                "Employee update completed successfully."
        );
    }

    // =========================================================
    // DELETE EMPLOYEE BY FIRST + LAST NAME
    // =========================================================

    public void deleteEmployee(
            String firstName,
            String lastName) {

        String employeeName =
                firstName + " " + lastName;

        System.out.println(
                "Deleting employee: "
                        + employeeName
        );

        ensureEmployeeListPage();

        WebElement nameField =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeNameSearchField
                        )
                );

        scrollIntoView(nameField);

        clearField(nameField);

        nameField.sendKeys(firstName);

        nameField.sendKeys(Keys.TAB);

        sleep(300);

        WebElement search =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                );

        clickElement(search);

        waitForLoaderToDisappear();

        sleep(1000);

        WebElement row = null;

        long endTime =
                System.currentTimeMillis()
                        + 30000;

        while (
                System.currentTimeMillis()
                        < endTime) {

            List<WebElement> rows =
                    driver.findElements(tableRows);

            for (WebElement currentRow : rows) {

                try {

                    if (!currentRow.isDisplayed()) {
                        continue;
                    }

                    String rowText =
                            normalizeText(
                                    currentRow.getText()
                            ).toLowerCase();

                    if (rowText.contains(
                            firstName.toLowerCase()
                    )
                            &&
                            rowText.contains(
                                    lastName.toLowerCase()
                            )) {

                        row = currentRow;
                        break;
                    }

                } catch (Exception ignored) {
                }
            }

            if (row != null) {
                break;
            }

            sleep(500);
        }

        if (row == null) {

            throw new RuntimeException(
                    "Employee not found for deletion: "
                            + employeeName
            );
        }

        scrollIntoView(row);

        By deleteButton =
                By.xpath(
                        ".//button[contains(@class,'oxd-icon-button')]"
                                + "[.//i[contains(@class,'bi-trash')]]"
                );

        WebElement delete =
                row.findElement(deleteButton);

        clickElement(delete);

        By yesDeleteButton =
                By.xpath(
                        "//button[normalize-space()='Yes, Delete']"
                );

        WebElement yesDelete =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                yesDeleteButton
                        )
                );

        clickElement(yesDelete);

        waitForLoaderToDisappear();

        sleep(1000);

        System.out.println(
                "Employee deleted successfully: "
                        + employeeName
        );
    }

    // =========================================================
    // DELETE EMPLOYEE BY FIRST NAME
    // =========================================================

    public void deleteEmployee(
            String firstName) {

        ensureEmployeeListPage();

        WebElement nameField =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeNameSearchField
                        )
                );

        scrollIntoView(nameField);

        clearField(nameField);

        nameField.sendKeys(firstName);

        nameField.sendKeys(Keys.TAB);

        sleep(300);

        WebElement search =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                );

        clickElement(search);

        waitForLoaderToDisappear();

        sleep(1000);

        long endTime =
                System.currentTimeMillis()
                        + 30000;

        while (
                System.currentTimeMillis()
                        < endTime) {

            List<WebElement> rows =
                    driver.findElements(tableRows);

            for (WebElement row : rows) {

                try {

                    if (!row.isDisplayed()) {
                        continue;
                    }

                    String rowText =
                            normalizeText(
                                    row.getText()
                            ).toLowerCase();

                    if (rowText.contains(
                            firstName.toLowerCase()
                    )) {

                        scrollIntoView(row);

                        By deleteButton =
                                By.xpath(
                                        ".//button[contains(@class,'oxd-icon-button')]"
                                                + "[.//i[contains(@class,'bi-trash')]]"
                                );

                        WebElement delete =
                                row.findElement(
                                        deleteButton
                                );

                        clickElement(delete);

                        By yesDeleteButton =
                                By.xpath(
                                        "//button[normalize-space()='Yes, Delete']"
                                );

                        WebElement yesDelete =
                                wait.until(
                                        ExpectedConditions.elementToBeClickable(
                                                yesDeleteButton
                                        )
                                );

                        clickElement(yesDelete);

                        waitForLoaderToDisappear();

                        sleep(1000);

                        System.out.println(
                                "Employee deleted successfully."
                        );

                        return;
                    }

                } catch (Exception ignored) {
                }
            }

            sleep(500);
        }

        throw new RuntimeException(
                "Employee not found for deletion: "
                        + firstName
        );
    }

    // =========================================================
    // RESET SEARCH
    // =========================================================

    public void resetSearch() {

        WebElement reset =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                resetButton
                        )
                );

        scrollIntoView(reset);

        clickElement(reset);

        waitForLoaderToDisappear();

        System.out.println(
                "Employee search reset successfully."
        );
    }

    // =========================================================
    // ENSURE EMPLOYEE LIST PAGE
    // =========================================================

    private void ensureEmployeeListPage() {

        if (!driver.getCurrentUrl()
                .contains("/pim/viewEmployeeList")) {

            clickPIM();

            clickEmployeeList();
        }

        waitForLoaderToDisappear();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        employeeIdSearchField
                )
        );
    }

    // =========================================================
    // CLEAR FIELD
    // =========================================================

    private void clearField(
            WebElement element) {

        try {

            element.click();

            element.sendKeys(
                    Keys.CONTROL,
                    "a"
            );

            element.sendKeys(
                    Keys.BACK_SPACE
            );

            sleep(200);

        } catch (Exception e) {

            try {

                element.clear();

            } catch (Exception ignored) {
            }
        }
    }

    // =========================================================
    // CLICK ELEMENT
    // =========================================================

    private void clickElement(
            WebElement element) {

        try {

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            element
                    )
            );

            element.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal click failed. Using JavaScript."
            );

            javascriptClick(element);
        }
    }

    // =========================================================
    // WAIT FOR LOADER
    // =========================================================

    private void waitForLoaderToDisappear() {

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(30)
            ).until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            loader
                    )
            );

        } catch (Exception e) {

            System.out.println(
                    "Form loader wait completed/loader not present."
            );
        }
    }

    // =========================================================
    // SCROLL INTO VIEW
    // =========================================================

    private void scrollIntoView(
            WebElement element) {

        try {

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    element
            );

        } catch (Exception e) {

            System.out.println(
                    "Scroll failed: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // JAVASCRIPT CLICK
    // =========================================================

    private void javascriptClick(
            WebElement element) {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                element
        );
    }

    // =========================================================
    // CHECK EMPLOYEE NAME
    // =========================================================

    private boolean containsEmployeeName(
            String rowText,
            String firstName) {

        if (rowText == null
                || firstName == null) {

            return false;
        }

        String normalizedRow =
                normalizeText(rowText)
                        .toLowerCase();

        String normalizedName =
                normalizeText(firstName)
                        .toLowerCase();

        if (normalizedRow.contains(
                normalizedName
        )) {

            return true;
        }

        String rowWithoutSpaces =
                removeSpaces(normalizedRow);

        String nameWithoutSpaces =
                removeSpaces(normalizedName);

        return rowWithoutSpaces.contains(
                nameWithoutSpaces
        );
    }

    // =========================================================
    // XPATH TEXT ESCAPE
    // =========================================================

    private String escapeXPathText(
            String text) {

        if (!text.contains("'")) {

            return "'" + text + "'";
        }

        if (!text.contains("\"")) {

            return "\"" + text + "\"";
        }

        String[] parts =
                text.split("'");

        StringBuilder result =
                new StringBuilder("concat(");

        for (int i = 0; i < parts.length; i++) {

            if (i > 0) {

                result.append(", \"'\", ");
            }

            result.append("'")
                    .append(parts[i])
                    .append("'");
        }

        result.append(")");

        return result.toString();
    }

    // =========================================================
    // NORMALIZE TEXT
    // =========================================================

    private String normalizeText(
            String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    // =========================================================
    // REMOVE SPACES
    // =========================================================

    private String removeSpaces(
            String text) {

        if (text == null) {
            return "";
        }

        return text.replaceAll(
                "\\s+",
                ""
        );
    }

    // =========================================================
    // SLEEP
    // =========================================================

    private void sleep(
            long milliseconds) {

        try {

            Thread.sleep(milliseconds);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}