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
* Performance testing

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
* k6 performance testing

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
| k6                | Performance testing             |
| Eclipse             | Development IDE                 |

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
* **k6 Scripts** – Execute performance and load testing scenarios

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
