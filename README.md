# OrangeHRM Automation Framework

[![GitHub Actions](https://github.com/anupwasakeqa/orangehrm-automation/actions/workflows/orangehrm-tests.yml/badge.svg)](https://github.com/anupwasakeqa/orangehrm-automation/actions)

A Selenium-based test automation framework for the **OrangeHRM application**, built using **Java, Selenium WebDriver, TestNG, Maven, REST Assured, and Page Object Model (POM)**.

The framework supports both **local test execution** and **CI execution through GitHub Actions**, with automated test reporting, failure screenshots, retry handling, and test artifacts.

---

## Project Overview

This project automates functional UI and API test scenarios for the OrangeHRM application.

The framework is designed with a focus on:

* Maintainability
* Reusability
* Test stability
* Failure diagnostics
* CI/CD execution
* HTML test reporting

### Key Features

* Selenium WebDriver UI automation
* Java-based automation framework
* TestNG test execution and annotations
* Page Object Model (POM)
* Maven dependency and build management
* REST Assured API automation
* Configurable QA environment
* Chrome browser automation
* Headless Chrome execution in GitHub Actions
* Extent HTML reporting
* Automatic failure screenshot capture
* Retry mechanism for failed tests
* Smart explicit wait utilities
* Failure URL and page title logging
* Flaky test detection and mitigation strategy
* Git and GitHub version control
* GitHub Actions CI automation
* Automatic test execution on repository changes
* CI test report and screenshot artifacts

---

## Application Under Test

| Property        | Details                                   |
| --------------- | ----------------------------------------- |
| **Application** | OrangeHRM Open Source Demo                |
| **Base URL**    | https://opensource-demo.orangehrmlive.com |
| **Environment** | QA                                        |
| **Browser**     | Chrome                                    |

---

## Tech Stack

| Technology         | Purpose                         |
| ------------------ | ------------------------------- |
| Java 17            | Programming language            |
| Selenium WebDriver | UI automation                   |
| TestNG             | Test framework and execution    |
| Maven              | Build and dependency management |
| REST Assured       | API automation                  |
| Jackson            | JSON processing                 |
| Extent Reports     | HTML test reporting             |
| Git                | Version control                 |
| GitHub             | Source code repository          |
| GitHub Actions     | CI/CD automation                |
| Eclipse            | Development IDE                 |

---

## Framework Architecture

The framework follows the **Page Object Model (POM)** design pattern.

### Main Components

* **Page Classes** – Store web element locators and page-specific actions
* **Test Classes** – Contain functional and API test scenarios
* **BaseTest** – Handles WebDriver setup and teardown
* **ConfigReader** – Reads environment and test configuration
* **API Classes** – Handle REST API operations
* **Listeners** – Handle TestNG execution events, retry logic, reporting, and failure screenshots
* **Utilities** – Provide reusable automation functions and smart waits
* **TestNG Suite** – Controls test execution
* **GitHub Actions** – Executes automated tests in CI

---

## Test Stability & Reliability

The framework includes multiple mechanisms to improve test stability, reliability, and execution consistency.

### Retry Logic

The framework uses a custom TestNG `RetryAnalyzer` to handle transient test failures.

* Failed tests are automatically retried up to 2 times.
* Retry logic is centrally configured through the TestNG listener.
* Individual test classes do not require separate retry configuration.
* Retry attempts and failure reasons are logged during test execution.
* Retry is used as a resilience mechanism and not as a replacement for fixing unstable tests.

This helps identify whether a failure is transient or consistently reproducible.

### Smart Waiting

The framework uses reusable explicit wait utilities to synchronize test execution with the application state.

The `WaitUtils` utility provides:

* Visibility waits
* Clickability waits
* Element presence waits
* Invisibility waits
* Text validation waits
* URL waits
* Page-load waits
* Reusable click and type operations

Page Objects use these wait mechanisms to reduce timing-related failures and improve test execution reliability.

### Failure Screenshot Capture

When a test fails, the TestNG listener automatically captures failure evidence.

The failure handling process includes:

1. Detecting the failed test
2. Capturing the failure details
3. Retrieving the active WebDriver instance
4. Capturing a screenshot
5. Saving the screenshot under `test-output/screenshots`
6. Logging the failure URL and page title

Screenshots are timestamped to make individual test executions easier to identify and troubleshoot.

### Flaky Test Detection

A flaky test is a test that intermittently passes and fails without a corresponding change in the application or test code.

The framework identifies potential flaky tests using:

* TestNG retry results
* Failure messages
* Stack traces
* Failure screenshots
* Failure URL and page information
* CI execution history

If a test fails initially but passes after a retry, it is treated as a potential flaky test and should be investigated further rather than relying permanently on retries.

### Flaky Test Mitigation Strategy

The framework follows the following practices to reduce flaky test failures:

1. Use explicit waits for dynamic application elements.
2. Wait for loaders and page transitions to complete.
3. Avoid unnecessary fixed delays where possible.
4. Use stable and meaningful element locators.
5. Capture screenshots and failure details for root-cause analysis.
6. Use retry only for transient failures.
7. Investigate repeated failures instead of masking them with retries.
8. Review CI execution history to identify recurring failures.
9. Fix the underlying synchronization, locator, data, or environment issue whenever a flaky test is identified.

The objective is to maintain a stable and deterministic automation suite while using retry functionality only as a controlled resilience mechanism.

---

## Automated Test Scenarios

The framework currently includes test coverage for:

* Login validation
* Employee creation
* Employee update
* Employee deletion
* Employee API validation
* Role validation

### Test Classes

```text
LoginTest.java
EmployeeCreationTest.java
EmployeeUpdateTest.java
EmployeeDeleteTest.java
EmployeeApiTest.java
RoleValidationTest.java
```

---

## Framework Structure

```text
orangehrm-automation
│
├── .github
│   └── workflows
│       └── orangehrm-tests.yml
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.orangehrm
│   │   │       ├── api
│   │   │       ├── base
│   │   │       ├── listeners
│   │   │       ├── pages
│   │   │       └── utils
│   │   │
│   │   └── resources
│   │
│   └── test
│       ├── java
│       │   └── com.orangehrm.tests
│       │       ├── BaseTest.java
│       │       ├── LoginTest.java
│       │       ├── EmployeeCreationTest.java
│       │       ├── EmployeeUpdateTest.java
│       │       ├── EmployeeDeleteTest.java
│       │       ├── EmployeeApiTest.java
│       │       └── RoleValidationTest.java
│       │
│       └── resources
│           ├── config.properties
│           └── config
│               └── qa.properties
│
├── testng.xml
├── pom.xml
├── .gitignore
└── README.md
```

---

## How to Run Locally

### Prerequisites

Make sure the following are installed:

* Java 17
* Maven
* Git
* Chrome browser
* Eclipse or another Java IDE

### Clone the Repository

```bash
git clone https://github.com/anupwasakeqa/orangehrm-automation.git
cd orangehrm-automation
```

### Run Tests

```bash
mvn clean test
```

### Run TestNG Suite

```bash
mvn test
```

---

## Test Reports

After test execution, reports and execution evidence are generated under:

```text
test-output/
```

The framework generates:

* Extent HTML report
* TestNG reports
* JUnit reports
* Failure screenshots
* Test execution result files

### Extent HTML Report

The Extent report provides an HTML-based view of test execution, including:

* Test execution status
* Passed tests
* Failed tests
* Skipped tests
* Execution details
* Failure information

The generated report can be opened directly in a web browser.

---

## GitHub Actions CI

The project uses **GitHub Actions** to automatically execute the automation suite.

The workflow is triggered on:

* Push to `main`
* Push to `master`
* Pull requests targeting `main`
* Pull requests targeting `master`
* Manual workflow execution

### CI Execution Flow

```text
Git Push / Pull Request
        ↓
GitHub Actions
        ↓
Checkout Source Code
        ↓
Setup Java 17
        ↓
Maven Test Execution
        ↓
Generate Test Reports
        ↓
Capture Failure Evidence
        ↓
Upload CI Artifacts
```

### CI Artifacts

The workflow uploads the generated test output as:

```text
orangehrm-test-reports
```

The artifact contains test reports, screenshots, and other execution evidence.

This allows test results to be downloaded and reviewed directly from the GitHub Actions workflow run.

---

## Continuous Integration Status

The GitHub Actions workflow status is displayed using the badge at the top of this README.

A successful workflow indicates that the automated test suite completed successfully in the CI environment.

---

## Reporting & Failure Diagnostics

The framework combines multiple reporting and diagnostic mechanisms:

```text
Test Execution
      ↓
TestNG Listener
      ↓
Extent Report
      ↓
Failure Detection
      ↓
Screenshot Capture
      ↓
Failure URL + Page Title
      ↓
GitHub Actions Artifact
```

This provides useful evidence for debugging failed tests locally as well as in CI.

---

## Future Enhancements

Planned improvements include:

* Cross-browser execution
* Parallel test execution
* Additional API test coverage
* Data-driven testing
* Enhanced logging
* Advanced test data management
* Docker-based test execution
* CI test history and trend analysis
* Additional OrangeHRM functional coverage

---

## Author

**Anup Wasake**

QA Automation Engineer | Selenium | Java | TestNG | REST Assured | CI/CD
