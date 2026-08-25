# OrangeHRM Automation Framework

[![GitHub Actions](https://github.com/anupwasakeqa/orangehrm-automation/actions/workflows/orangehrm-tests.yml/badge.svg)](https://github.com/anupwasakeqa/orangehrm-automation/actions)

A scalable test automation framework for the **OrangeHRM application**, built using **Java, Selenium WebDriver, TestNG, Maven, REST Assured, k6, and Page Object Model (POM)**.

The framework covers UI functional testing, API testing, test stability, failure diagnostics, CI/CD execution, HTML reporting, screenshots, video recording, and basic performance validation.

---

## Project Objective

The objective of this project is to automate an end-to-end employee lifecycle in OrangeHRM while demonstrating maintainable automation architecture, API validation, CI/CD integration, test reliability, reporting, and observability.

### Covered Areas

- Authentication
- Employee creation
- Role validation
- Employee update
- API-level verification
- Employee deletion
- UI and API test automation
- CI/CD execution
- Test reporting
- Failure screenshots
- CI screen recording
- Retry handling
- Smart waits
- Basic k6 performance validation

---

## Application Under Test

| Property | Details |
|---|---|
| Application | OrangeHRM Open Source Demo |
| Base URL | https://opensource-demo.orangehrmlive.com |
| Environment | QA |
| Browser | Chrome |

---

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Selenium WebDriver | UI automation |
| TestNG | Test execution and assertions |
| Maven | Build and dependency management |
| REST Assured | API automation |
| Jackson | JSON processing |
| k6 | Performance testing |
| Extent Reports | HTML reporting |
| Git | Version control |
| GitHub | Source repository |
| GitHub Actions | CI/CD |
| Eclipse | Development IDE |

---

## Framework Architecture

The framework follows the **Page Object Model (POM)** design pattern.

```text
orangehrm-automation/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/orangehrm/
│   │           ├── api/
│   │           │   ├── ApiConstants.java
│   │           │   └── EmployeeApi.java
│   │           │
│   │           ├── base/
│   │           │   └── DriverFactory.java
│   │           │
│   │           ├── listeners/
│   │           │   ├── RetryAnalyzer.java
│   │           │   └── TestListener.java
│   │           │
│   │           ├── pages/
│   │           │   ├── DashboardPage.java
│   │           │   ├── EmployeePage.java
│   │           │   ├── LoginPage.java
│   │           │   ├── PIMPage.java
│   │           │   └── RoleValidationPage.java
│   │           │
│   │           └── utils/
│   │               ├── ConfigReader.java
│   │               └── WaitUtils.java
│   │
│   └── test/
│       ├── java/
│       │   └── com/orangehrm/tests/
│       │       ├── BaseTest.java
│       │       ├── EmployeeApiTest.java
│       │       ├── EmployeeCreationTest.java
│       │       ├── EmployeeDeleteTest.java
│       │       ├── EmployeeUpdateTest.java
│       │       ├── LoginTest.java
│       │       └── RoleValidationTest.java
│       │
│       └── resources/
│           ├── config.properties
│           └── config/qa.properties
│
├── performance/
│   └── k6/
│       └── login.js
│
├── screenshots/
├── test-output/
├── target/
├── pom.xml
├── testng.xml
└── README.md
