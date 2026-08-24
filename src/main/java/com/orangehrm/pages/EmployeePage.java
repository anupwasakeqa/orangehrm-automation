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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By pimMenu =
            By.xpath("//span[normalize-space()='PIM']/ancestor::a");

    private final By employeeListMenu =
            By.xpath("//a[normalize-space()='Employee List']");

    private final By addEmployeeButton =
            By.xpath("//button[normalize-space()='Add']");

    private final By firstNameField =
            By.name("firstName");

    private final By middleNameField =
            By.name("middleName");

    private final By lastNameField =
            By.name("lastName");

    private final By employeeIdField =
            By.name("employeeId");

    private final By saveButton =
            By.xpath("//button[@type='submit']");

    private final By loader =
            By.cssSelector(".oxd-form-loader");

    private final By searchButton =
            By.xpath("//button[normalize-space()='Search']");

    private final By resetButton =
            By.xpath("//button[normalize-space()='Reset']");

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

        System.out.println("Clicking PIM menu...");

        WebElement pim =
                wait.until(
                        ExpectedConditions.elementToBeClickable(pimMenu)
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
                ExpectedConditions.presenceOfElementLocated(
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

        // -----------------------------------------------------
        // NAVIGATE
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

        middleNameElement.clear();

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

        lastNameElement.clear();
        lastNameElement.sendKeys(lastName);

        System.out.println(
                "Employee details entered successfully."
        );

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        waitForLoaderToDisappear();

        By employeeFormSave =
                By.xpath(
                        "//form" +
                        "//button[@type='submit']"
                );

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

        waitForLoaderToDisappear();

        sleep(1500);

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

        System.out.println("================================================");
    }

    // =========================================================
    // GET CURRENT EMPLOYEE ID
    // =========================================================

    public String getCurrentEmployeeId() {

        System.out.println();
        System.out.println("================================================");
        System.out.println("CAPTURING CREATED EMPLOYEE ID");
        System.out.println("================================================");

        String currentUrl = driver.getCurrentUrl();

        System.out.println(
                "Current URL: " + currentUrl
        );

        String marker = "/empNumber/";

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

    public boolean verifyEmployeeExistsById(
            String employeeId) {

        try {

            if (employeeId == null
                    || employeeId.trim().isEmpty()) {

                return false;
            }

            employeeId = employeeId.trim();

            openEmployeeById(employeeId);

            String currentUrl = driver.getCurrentUrl();

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

            empNumber = empNumber.trim();

            openEmployeeById(empNumber);

            String currentUrl = driver.getCurrentUrl();

            if (!currentUrl.contains(
                    "/pim/viewPersonalDetails/empNumber/"
                            + empNumber)) {

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
                        "Employee Number: " + empNumber
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

        employeeId = employeeId.trim();

        String currentUrl = driver.getCurrentUrl();

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
        System.out.println("================================================");
        System.out.println("OPENING EMPLOYEE FOR EDIT BY ID");
        System.out.println("================================================");

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

        employeeId = employeeId.trim();

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

        try {
            search.click();
        } catch (Exception e) {
            javascriptClick(search);
        }

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
        System.out.println("================================================");
        System.out.println("OPENING EMPLOYEE FOR EDIT");
        System.out.println("================================================");
        System.out.println(
                "Employee First Name: " + firstName
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
        // MAKE SURE EMPLOYEE LIST IS OPEN
        // -----------------------------------------------------

        if (!driver.getCurrentUrl()
                .contains("/pim/viewEmployeeList")) {

            clickPIM();
            clickEmployeeList();
        }

        waitForLoaderToDisappear();

        // -----------------------------------------------------
        // WAIT FOR SEARCH FIELD
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

        employeeNameField.sendKeys(Keys.TAB);

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

        try {
            search.click();
        } catch (Exception e) {
            javascriptClick(search);
        }

        System.out.println(
                "Employee search submitted."
        );

        // -----------------------------------------------------
        // WAIT FOR SEARCH
        // -----------------------------------------------------

        waitForLoaderToDisappear();

        sleep(1500);

        // -----------------------------------------------------
        // IMPORTANT:
        // DO NOT USE presenceOfElementLocated(tableRows)
        //
        // SEARCH CAN RETURN ZERO ROWS.
        // We poll findElements() instead.
        // -----------------------------------------------------

        WebElement employeeRow = null;

        long endTime =
                System.currentTimeMillis()
                        + 30000;

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

                        System.out.println(
                                "Employee row: "
                                        + rowText
                        );

                        if (containsEmployeeName(
                                rowText,
                                searchFirstName
                        )) {

                            employeeRow = row;

                            System.out.println(
                                    "Matching employee row found: "
                                            + rowText
                            );

                            break;
                        }

                    } catch (Exception ignored) {
                    }
                }

                if (employeeRow != null) {
                    break;
                }

            } catch (Exception ignored) {
            }

            sleep(500);
        }

        // -----------------------------------------------------
        // FALLBACK:
        // SEARCH RESULT MAY BE LOADED BUT NAME MATCH MAY HAVE
        // DIFFERENT SPACING / HTML STRUCTURE.
        // -----------------------------------------------------

        if (employeeRow == null) {

            System.out.println(
                    "Normal employee row matching failed."
            );

            System.out.println(
                    "Trying exact cell based lookup..."
            );

            try {

                By exactFirstNameCell =
                        By.xpath(
                                "//div[contains(@class,'oxd-table-body')]" +
                                "//div[contains(@class,'oxd-table-row')]" +
                                "//div[contains(@class,'oxd-table-cell')]" +
                                "[normalize-space()='"
                                + escapeXPathText(searchFirstName)
                                + "']"
                        );

                List<WebElement> cells =
                        driver.findElements(
                                exactFirstNameCell
                        );

                for (WebElement cell : cells) {

                    try {

                        if (!cell.isDisplayed()) {
                            continue;
                        }

                        employeeRow =
                                cell.findElement(
                                        By.xpath(
                                                "./ancestor::div[contains(@class,'oxd-table-row')]"
                                        )
                                );

                        if (employeeRow != null) {
                            break;
                        }

                    } catch (Exception ignored) {
                    }
                }

            } catch (Exception ignored) {
            }
        }

        // -----------------------------------------------------
        // IF STILL NOT FOUND
        // -----------------------------------------------------

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

        // -----------------------------------------------------
        // FIND EDIT BUTTON
        // -----------------------------------------------------

        By editButtonLocator =
                By.xpath(
                        ".//button[@type='button']" +
                        "[.//i[contains(@class,'bi-pencil-fill')]]"
                );

        WebElement editButton = null;

        try {

            editButton =
                    employeeRow.findElement(
                            editButtonLocator
                    );

        } catch (Exception e) {

            // -------------------------------------------------
            // FALLBACK EDIT BUTTON
            // -------------------------------------------------

            By fallbackEditButton =
                    By.xpath(
                            ".//button[contains(@class,'oxd-icon-button')]" +
                            "[.//i[contains(@class,'bi-pencil')]]"
                    );

            editButton =
                    employeeRow.findElement(
                            fallbackEditButton
                    );
        }

        // -----------------------------------------------------
        // CLICK EDIT
        // -----------------------------------------------------

        scrollIntoView(editButton);

        sleep(300);

        try {

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            editButton
                    )
            );

            editButton.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal Edit click failed. "
                            + "Using JavaScript."
            );

            javascriptClick(editButton);
        }

        System.out.println(
                "Edit button clicked successfully."
        );

        // -----------------------------------------------------
        // WAIT FOR PERSONAL DETAILS
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

        field.click();

        field.sendKeys(
                Keys.CONTROL,
                "a"
        );

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

        field.click();

        field.sendKeys(
                Keys.CONTROL,
                "a"
        );

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
        System.out.println("================================================");
        System.out.println("SAVING UPDATED EMPLOYEE DETAILS");
        System.out.println("================================================");

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

                } catch (Exception ignored) {
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

            javascriptClick(save);
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
        System.out.println("================================================");
        System.out.println("VERIFYING UPDATED EMPLOYEE DETAILS");
        System.out.println("================================================");

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

        if (!driver.getCurrentUrl()
                .contains("/pim/viewEmployeeList")) {

            clickPIM();
            clickEmployeeList();
        }

        // -----------------------------------------------------
        // SEARCH BY FIRST NAME
        // -----------------------------------------------------

        WebElement nameField =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeNameSearchField
                        )
                );

        nameField.click();
        nameField.clear();
        nameField.sendKeys(firstName);
        nameField.sendKeys(Keys.TAB);

        sleep(300);

        WebElement search =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                );

        try {
            search.click();
        } catch (Exception e) {
            javascriptClick(search);
        }

        waitForLoaderToDisappear();

        sleep(1000);

        // -----------------------------------------------------
        // FIND ROW
        // -----------------------------------------------------

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
                            );

                    if (rowText
                            .toLowerCase()
                            .contains(
                                    firstName.toLowerCase()
                            )
                            &&
                            rowText
                                    .toLowerCase()
                                    .contains(
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

        // -----------------------------------------------------
        // DELETE
        // -----------------------------------------------------

        scrollIntoView(row);

        By deleteButton =
                By.xpath(
                        ".//button[contains(@class,'oxd-icon-button')]" +
                        "[.//i[contains(@class,'bi-trash')]]"
                );

        WebElement delete =
                row.findElement(deleteButton);

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        delete
                )
        );

        try {
            delete.click();
        } catch (Exception e) {
            javascriptClick(delete);
        }

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

        try {
            yesDelete.click();
        } catch (Exception e) {
            javascriptClick(yesDelete);
        }

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

        WebElement nameField =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeNameSearchField
                        )
                );

        nameField.click();
        nameField.clear();
        nameField.sendKeys(firstName);
        nameField.sendKeys(Keys.TAB);

        sleep(300);

        WebElement search =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                );

        try {
            search.click();
        } catch (Exception e) {
            javascriptClick(search);
        }

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

                        try {
                            delete.click();
                        } catch (Exception e) {
                            javascriptClick(delete);
                        }

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

                        try {
                            yesDelete.click();
                        } catch (Exception e) {
                            javascriptClick(yesDelete);
                        }

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

        try {
            reset.click();
        } catch (Exception e) {
            javascriptClick(reset);
        }

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

        // Exact normalized name
        if (normalizedRow.contains(normalizedName)) {
            return true;
        }

        // Remove spaces and compare
        String rowWithoutSpaces =
                normalizedRow.replaceAll("\\s+", "");

        String nameWithoutSpaces =
                normalizedName.replaceAll("\\s+", "");

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
                new StringBuilder(
                        "concat("
                );

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