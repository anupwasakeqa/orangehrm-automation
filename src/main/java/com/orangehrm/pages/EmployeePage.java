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

    private final By saveButton =
            By.xpath(
                    "//button[@type='submit']"
            );

    private final By loader =
            By.cssSelector(
                    ".oxd-form-loader"
            );

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
                    "//label[normalize-space()='Employee Id']" +
                    "/ancestor::div[contains(@class,'oxd-input-group')]" +
                    "//input"
            );

    private final By employeeNameSearchField =
            By.xpath(
                    "//label[normalize-space()='Employee Name']" +
                    "/ancestor::div[contains(@class,'oxd-input-group')]" +
                    "//input"
            );

    private final By tableRows =
            By.xpath(
                    "//div[contains(@class,'oxd-table-body')]" +
                    "//div[contains(@class,'oxd-table-row')]"
            );

    // =========================================================
    // CLICK PIM
    // =========================================================

    public void clickPIM() {

        System.out.println(
                "Clicking PIM menu..."
        );

        WebElement pim =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                pimMenu
                        )
                );

        scrollIntoView(pim);

        pim.click();

        wait.until(
                ExpectedConditions.urlContains(
                        "/pim/"
                )
        );

        waitForLoaderToDisappear();

        System.out.println(
                "PIM page opened."
        );
    }

    // =========================================================
    // CLICK EMPLOYEE LIST
    // =========================================================

    public void clickEmployeeList() {

        System.out.println(
                "Clicking Employee List..."
        );

        WebElement employeeList =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeListMenu
                        )
                );

        scrollIntoView(employeeList);

        employeeList.click();

        wait.until(
                ExpectedConditions.urlContains(
                        "/pim/viewEmployeeList"
                )
        );

        waitForLoaderToDisappear();

        System.out.println(
                "Employee List page loaded successfully."
        );
    }

    // =========================================================
    // CLICK ADD EMPLOYEE
    // =========================================================

    public void clickAddEmployee() {

        System.out.println(
                "Clicking Add Employee..."
        );

        WebElement add =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                addEmployeeButton
                        )
                );

        scrollIntoView(add);

        add.click();

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
        System.out.println(
                "================================================"
        );
        System.out.println(
                "STARTING EMPLOYEE CREATION"
        );
        System.out.println(
                "================================================"
        );

        // -----------------------------------------------------
        // NAVIGATE TO ADD EMPLOYEE
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

        firstNameElement.clear();

        firstNameElement.sendKeys(
                firstName
        );

        // -----------------------------------------------------
        // MIDDLE NAME
        // -----------------------------------------------------

        WebElement middleNameElement =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                middleNameField
                        )
                );

        middleNameElement.clear();

        if (middleName != null
                && !middleName.trim().isEmpty()) {

            middleNameElement.sendKeys(
                    middleName
            );
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

        lastNameElement.clear();

        lastNameElement.sendKeys(
                lastName
        );

        System.out.println(
                "Employee details entered successfully."
        );

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        waitForLoaderToDisappear();

        WebElement save =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                saveButton
                        )
                );

        scrollIntoView(save);

        sleep(500);

        save.click();

        System.out.println(
                "Employee Save button clicked."
        );

        // -----------------------------------------------------
        // WAIT FOR SAVE OPERATION
        // -----------------------------------------------------

        waitForLoaderToDisappear();

        sleep(2000);

        System.out.println(
                "Employee save operation completed."
        );

        System.out.println(
                "Current URL after save: "
                        + driver.getCurrentUrl()
        );

        // -----------------------------------------------------
        // IMPORTANT
        // -----------------------------------------------------
        //
        // Do NOT force URL:
        //
        // /pim/viewPersonalDetails/empNumber/
        //
        // OrangeHRM may keep the browser on:
        //
        // /pim/addEmployee
        //
        // after successful employee creation.
        //
        // The next step will locate the employee and obtain
        // the internal employee number.
        // -----------------------------------------------------

        System.out.println(
                "Employee creation flow completed successfully."
        );

        System.out.println(
                "================================================"
        );
    }

    // =========================================================
    // GET CURRENT EMPLOYEE NUMBER
    // =========================================================

    public String getCurrentEmployeeId() {

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "CAPTURING CREATED EMPLOYEE ID"
        );
        System.out.println(
                "================================================"
        );

        wait.until(
                ExpectedConditions.urlContains(
                        "/pim/viewPersonalDetails/empNumber/"
                )
        );

        String currentUrl =
                driver.getCurrentUrl();

        System.out.println(
                "Current URL: "
                        + currentUrl
        );

        String marker =
                "/empNumber/";

        if (currentUrl.contains(marker)) {

            String employeeId =
                    currentUrl.substring(
                            currentUrl.indexOf(marker)
                                    + marker.length()
                    );

            employeeId =
                    employeeId
                            .split("[/?#]")[0]
                            .trim();

            if (employeeId.matches("\\d+")) {

                System.out.println(
                        "Employee Number captured successfully: "
                                + employeeId
                );

                return employeeId;
            }
        }

        // -----------------------------------------------------
        // FALLBACK - EMPLOYEE ID FIELD
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

            String employeeId =
                    idField
                            .getAttribute("value")
                            .trim();

            if (!employeeId.isEmpty()) {

                System.out.println(
                        "Employee ID captured from field: "
                                + employeeId
                );

                return employeeId;
            }

        } catch (Exception e) {

            System.out.println(
                    "Employee ID field fallback failed."
            );
        }

        throw new RuntimeException(
                "Unable to capture generated Employee Number. "
                        + "Current URL: "
                        + driver.getCurrentUrl()
        );
    }

    // =========================================================
    // VERIFY EMPLOYEE EXISTS BY ID
    // =========================================================
    //
    // NOTE:
    // This method is retained for existing tests.
    //
    // Direct personal-details URL requires EMP NUMBER,
    // not Employee ID.
    //
    // For delete test use:
    // verifyEmployeeExistsByEmpNumber()
    //
    // =========================================================

    public boolean verifyEmployeeExistsById(
            String employeeId) {

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "VERIFYING EMPLOYEE EXISTS BY ID"
        );
        System.out.println(
                "================================================"
        );

        System.out.println(
                "Employee ID: "
                        + employeeId
        );

        try {

            if (employeeId == null
                    || employeeId.trim().isEmpty()) {

                return false;
            }

            employeeId =
                    employeeId.trim();

            // This method should only be used when the
            // supplied value is actually the internal empNumber.
            openEmployeeById(employeeId);

            String currentUrl =
                    driver.getCurrentUrl();

            System.out.println(
                    "Employee details URL: "
                            + currentUrl
            );

            if (!currentUrl.contains(
                    "/pim/viewPersonalDetails/empNumber/"
                            + employeeId)) {

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

            if (!firstName.isDisplayed()) {
                return false;
            }

            if (!lastName.isDisplayed()) {
                return false;
            }

            System.out.println(
                    "EMPLOYEE FOUND SUCCESSFULLY"
            );

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Employee verification failed."
            );

            System.out.println(
                    "Reason: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // VERIFY EMPLOYEE EXISTS BY EMP NUMBER
    // =========================================================
    //
    // IMPORTANT:
    //
    // OrangeHRM URL:
    //
    // /viewPersonalDetails/empNumber/{empNumber}
    //
    // Example:
    //
    // employeeId  = 0382
    // empNumber   = 179
    //
    // Correct URL:
    //
    // /viewPersonalDetails/empNumber/179
    //
    // =========================================================

    public boolean verifyEmployeeExistsByEmpNumber(
            String empNumber) {

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "VERIFYING EMPLOYEE EXISTS BY EMP NUMBER"
        );
        System.out.println(
                "================================================"
        );

        System.out.println(
                "Employee Number: "
                        + empNumber
        );

        try {

            if (empNumber == null
                    || empNumber.trim().isEmpty()) {

                return false;
            }

            empNumber =
                    empNumber.trim();

            // -------------------------------------------------
            // OPEN USING INTERNAL EMP NUMBER
            // -------------------------------------------------

            openEmployeeById(empNumber);

            String currentUrl =
                    driver.getCurrentUrl();

            System.out.println(
                    "Employee details URL: "
                            + currentUrl
            );

            // -------------------------------------------------
            // VERIFY CORRECT URL
            // -------------------------------------------------

            if (!currentUrl.contains(
                    "/pim/viewPersonalDetails/empNumber/"
                            + empNumber)) {

                return false;
            }

            // -------------------------------------------------
            // VERIFY FIRST NAME FIELD
            // -------------------------------------------------

            WebElement firstName =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    firstNameField
                            )
                    );

            // -------------------------------------------------
            // VERIFY LAST NAME FIELD
            // -------------------------------------------------

            WebElement lastName =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    lastNameField
                            )
                    );

            if (!firstName.isDisplayed()) {

                return false;
            }

            if (!lastName.isDisplayed()) {

                return false;
            }

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

        } catch (Exception e) {

            System.out.println(
                    "Employee verification failed."
            );

            System.out.println(
                    "Reason: "
                            + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // OPEN EMPLOYEE BY EMP NUMBER
    // =========================================================

    public void openEmployeeById(
            String employeeId) {

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "OPEN EMPLOYEE BY INTERNAL EMP NUMBER"
        );
        System.out.println(
                "================================================"
        );

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
                "Current URL: "
                        + currentUrl
        );

        String baseUrl;

        // -----------------------------------------------------
        // EXTRACT BASE URL
        // -----------------------------------------------------

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

        // -----------------------------------------------------
        // DIRECT EMPLOYEE URL
        // -----------------------------------------------------

        String employeeUrl =
                baseUrl
                        + "/web/index.php/pim/"
                        + "viewPersonalDetails/empNumber/"
                        + employeeId;

        System.out.println(
                "Opening employee URL: "
                        + employeeUrl
        );

        driver.get(
                employeeUrl
        );

        // -----------------------------------------------------
        // WAIT FOR CORRECT URL
        // -----------------------------------------------------

        wait.until(
                ExpectedConditions.urlContains(
                        "/pim/viewPersonalDetails/empNumber/"
                                + employeeId
                )
        );

        // -----------------------------------------------------
        // WAIT FOR DETAILS FORM
        // -----------------------------------------------------

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

        System.out.println(
                "Employee ID: "
                        + employeeId
        );

        if (employeeId == null
                || employeeId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee ID cannot be null or empty."
            );
        }

        employeeId =
                employeeId.trim();

        openEmployeeById(
                employeeId
        );

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

        if (!firstName.isDisplayed()) {

            throw new RuntimeException(
                    "First Name field is not displayed "
                            + "for employee ID: "
                            + employeeId
            );
        }

        if (!lastName.isDisplayed()) {

            throw new RuntimeException(
                    "Last Name field is not displayed "
                            + "for employee ID: "
                            + employeeId
            );
        }

        System.out.println(
                "Employee edit page opened successfully."
        );

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

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "SEARCHING EMPLOYEE BY ID"
        );
        System.out.println(
                "================================================"
        );

        if (employeeId == null
                || employeeId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee ID cannot be null or empty."
            );
        }

        if (!driver.getCurrentUrl()
                .contains("/pim/viewEmployeeList")) {

            clickPIM();

            clickEmployeeList();
        }

        waitForLoaderToDisappear();

        WebElement idField =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeIdSearchField
                        )
                );

        scrollIntoView(idField);

        idField.click();

        idField.clear();

        idField.sendKeys(
                employeeId
        );

        idField.sendKeys(
                Keys.TAB
        );

        sleep(500);

        WebElement search =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                );

        scrollIntoView(search);

        search.click();

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

            searchEmployeeById(
                    employeeId
            );

            WebDriverWait shortWait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(15)
                    );

            return shortWait.until(
                    driver -> {

                        List<WebElement> rows =
                                driver.findElements(
                                        tableRows
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

                                if (rowText.contains(
                                        employeeId
                                )) {

                                    return true;
                                }

                            } catch (Exception e) {

                                // Ignore stale row
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

        System.out.println(
                "Employee First Name: "
                        + firstName
        );

        if (firstName == null
                || firstName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee first name cannot be null or empty."
            );
        }

        final String searchFirstName =
                firstName.trim();

        // -----------------------------------------------------
        // OPEN EMPLOYEE LIST
        // -----------------------------------------------------

        if (!driver.getCurrentUrl()
                .contains("/pim/viewEmployeeList")) {

            clickPIM();

            clickEmployeeList();
        }

        // -----------------------------------------------------
        // WAIT FOR EMPLOYEE LIST
        // -----------------------------------------------------

        waitForLoaderToDisappear();

        sleep(1000);

        // -----------------------------------------------------
        // SEARCH EMPLOYEE BY FIRST NAME
        // -----------------------------------------------------

        WebElement employeeNameField =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeNameSearchField
                        )
                );

        scrollIntoView(employeeNameField);

        employeeNameField.click();

        employeeNameField.clear();

        employeeNameField.sendKeys(
                searchFirstName
        );

        employeeNameField.sendKeys(
                Keys.TAB
        );

        sleep(500);

        // -----------------------------------------------------
        // CLICK SEARCH
        // -----------------------------------------------------

        WebElement search =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                );

        scrollIntoView(search);

        search.click();

        System.out.println(
                "Employee search submitted."
        );

        // -----------------------------------------------------
        // WAIT FOR SEARCH RESULT
        // -----------------------------------------------------

        waitForLoaderToDisappear();

        sleep(1000);

        // -----------------------------------------------------
        // FIND EMPLOYEE ROW
        // -----------------------------------------------------

        WebElement employeeRow =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(30)
                ).until(
                        driver -> {

                            List<WebElement> rows =
                                    driver.findElements(
                                            tableRows
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

                                    System.out.println(
                                            "Employee row found: "
                                                    + rowText
                                    );

                                    if (rowText
                                            .toLowerCase()
                                            .contains(
                                                    searchFirstName
                                                            .toLowerCase()
                                            )) {

                                        return row;
                                    }

                                } catch (Exception e) {

                                    // Ignore stale rows
                                }
                            }

                            return null;
                        }
                );

        // -----------------------------------------------------
        // FIND EDIT BUTTON
        // -----------------------------------------------------

        By editButtonLocator =
                By.xpath(
                        ".//button[@type='button']" +
                        "[.//i[contains(@class," +
                        "'bi-pencil-fill')]]"
                );

        WebElement editButton =
                employeeRow.findElement(
                        editButtonLocator
                );

        // -----------------------------------------------------
        // CLICK EDIT
        // -----------------------------------------------------

        scrollIntoView(editButton);

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        editButton
                )
        );

        editButton.click();

        System.out.println(
                "Edit button clicked successfully."
        );

        // -----------------------------------------------------
        // WAIT FOR PERSONAL DETAILS PAGE
        // -----------------------------------------------------

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
    // UPDATE FIRST NAME
    // =========================================================

    public void updateFirstName(
            String newFirstName) {

        WebElement field =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                firstNameField
                        )
                );

        scrollIntoView(field);

        field.click();

        field.sendKeys(
                Keys.CONTROL,
                "a"
        );

        field.sendKeys(
                newFirstName
        );

        field.sendKeys(
                Keys.TAB
        );

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

        WebElement field =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                lastNameField
                        )
                );

        scrollIntoView(field);

        field.click();

        field.sendKeys(
                Keys.CONTROL,
                "a"
        );

        field.sendKeys(
                newLastName
        );

        field.sendKeys(
                Keys.TAB
        );

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
                        "//input[@name='firstName']" +
                        "/ancestor::form" +
                        "//button[normalize-space()='Save']"
                );

        WebElement save = null;

        List<WebElement> saveButtons =
                driver.findElements(
                        personalDetailsSave
                );

        for (WebElement button :
                saveButtons) {

            try {

                if (button.isDisplayed()
                        && button.isEnabled()) {

                    save = button;

                    break;
                }

            } catch (Exception e) {

                // Ignore
            }
        }

        // -----------------------------------------------------
        // FALLBACK
        // -----------------------------------------------------

        if (save == null) {

            List<WebElement> allSaveButtons =
                    driver.findElements(
                            By.xpath(
                                    "//button[normalize-space()='Save']"
                            )
                    );

            for (WebElement button :
                    allSaveButtons) {

                try {

                    if (button.isDisplayed()
                            && button.isEnabled()) {

                        save = button;

                        break;
                    }

                } catch (Exception e) {

                    // Ignore
                }
            }
        }

        if (save == null) {

            throw new RuntimeException(
                    "No usable Save button found."
            );
        }

        // -----------------------------------------------------
        // CLICK
        // -----------------------------------------------------

        scrollIntoView(save);

        sleep(500);

        try {

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            save
                    )
            );

            save.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal Save click failed. "
                            + "Trying JavaScript."
            );

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].click();",
                    save
            );
        }

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

        System.out.println(
                "Expected First Name: "
                        + expectedFirstName
        );

        System.out.println(
                "Expected Last Name: "
                        + expectedLastName
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
                                                    .getAttribute(
                                                            "value"
                                                    )
                                                    .trim();

                                    String actualLastName =
                                            lastName
                                                    .getAttribute(
                                                            "value"
                                                    )
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

        updateFirstName(
                newFirstName
        );

        updateLastName(
                newLastName
        );

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

        if (!driver.getCurrentUrl()
                .contains("/pim/viewEmployeeList")) {

            clickPIM();

            clickEmployeeList();
        }

        By employeeRow =
                By.xpath(
                        "//div[contains(@class,'oxd-table-row')]" +
                        "[.//div[contains(@class,'oxd-table-cell')" +
                        " and normalize-space()='"
                        + firstName
                        + "']]" +
                        "[.//div[contains(@class,'oxd-table-cell')" +
                        " and normalize-space()='"
                        + lastName
                        + "']]"
                );

        WebElement row =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                employeeRow
                        )
                );

        scrollIntoView(row);

        By deleteButton =
                By.xpath(
                        ".//button[contains(@class,'oxd-icon-button')]" +
                        "[.//i[contains(@class,'bi-trash')]]"
                );

        WebElement delete =
                row.findElement(
                        deleteButton
                );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        delete
                )
        );

        delete.click();

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

        yesDelete.click();

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

        if (!driver.getCurrentUrl()
                .contains("/pim/viewEmployeeList")) {

            clickPIM();

            clickEmployeeList();
        }

        List<WebElement> rows =
                driver.findElements(
                        tableRows
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

                if (rowText
                        .toLowerCase()
                        .contains(
                                firstName.toLowerCase()
                        )) {

                    scrollIntoView(row);

                    By deleteButton =
                            By.xpath(
                                    ".//button[contains(@class,'oxd-icon-button')]" +
                                    "[.//i[contains(@class,'bi-trash')]]"
                            );

                    WebElement delete =
                            row.findElement(
                                    deleteButton
                            );

                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    delete
                            )
                    );

                    delete.click();

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

                    yesDelete.click();

                    waitForLoaderToDisappear();

                    sleep(1000);

                    System.out.println(
                            "Employee deleted successfully."
                    );

                    return;
                }

            } catch (Exception e) {

                // Continue
            }
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

        reset.click();

        waitForLoaderToDisappear();

        System.out.println(
                "Employee search reset successfully."
        );
    }

    // =========================================================
    // WAIT FOR LOADER
    // =========================================================

    private void waitForLoaderToDisappear() {

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(10)
            ).until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            loader
                    )
            );

        } catch (Exception e) {

            // Loader can disappear before wait starts.
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
    // SLEEP
    // =========================================================

    private void sleep(
            long milliseconds) {

        try {

            Thread.sleep(
                    milliseconds
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}