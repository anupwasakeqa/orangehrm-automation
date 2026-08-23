# OrangeHRM Automation Framework

[![GitHub Actions](https://github.com/anupwasakeqa/orangehrm-automation/actions/workflows/maven.yml/badge.svg)](https://github.com/anupwasakeqa/orangehrm-automation/actions)

A Selenium-based test automation framework for the OrangeHRM application, built using Java, Selenium WebDriver, TestNG, Maven, REST Assured, and Page Object Model.

The framework supports local execution as well as CI execution through GitHub Actions.

---

## Project Overview

This project automates functional and API test scenarios for the OrangeHRM application.

### Key Features

- Selenium WebDriver UI automation
- Java-based automation framework
- TestNG test execution and annotations
- Page Object Model (POM)
- Maven dependency and build management
- REST Assured API automation
- Configurable QA environment
- Chrome browser automation
- Headless Chrome execution in GitHub Actions
- Extent Reports integration
- Git and GitHub version control
- GitHub Actions CI automation
- Automatic test execution on repository changes

---

## Application Under Test

**Application:** OrangeHRM Open Source Demo

**Base URL:**  
https://opensource-demo.orangehrmlive.com

**Environment:** QA

**Browser:** Chrome

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Selenium WebDriver | UI automation |
| TestNG | Test framework |
| Maven | Build & dependency management |
| REST Assured | API automation |
| Jackson | JSON processing |
| Extent Reports | Test reporting |
| Git | Version control |
| GitHub | Source code repository |
| GitHub Actions | CI/CD automation |
| Eclipse | Development IDE |

---

## Framework Design

The framework follows the **Page Object Model (POM)** design pattern.

### Main Components

- **Page Classes** – Store web element locators and page actions
- **Test Classes** – Contain test scenarios
- **BaseTest** – Handles WebDriver setup and teardown
- **ConfigReader** – Reads environment and test configuration
- **API Classes** – Handle REST API operations
- **Listeners** – Handle TestNG execution events
- **Utilities** – Common reusable automation functions
- **TestNG Suite** – Controls test execution
- **GitHub Actions** – Executes automated tests in CI

---

## Framework Structure

```text
orangehrm-automation
│
├── .github
│   └── workflows
│       └── maven.yml
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
