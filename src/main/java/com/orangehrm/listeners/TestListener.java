package com.orangehrm.listeners;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestListener
        implements ITestListener, IAnnotationTransformer {

    // ============================================================
    // EXTENT REPORT
    // ============================================================

    private static ExtentReports extent;

    private static ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    // ============================================================
    // RETRY ANALYZER
    // ============================================================

    @Override
    public void transform(
            ITestAnnotation annotation,
            Class testClass,
            java.lang.reflect.Constructor constructor,
            java.lang.reflect.Method method) {

        annotation.setRetryAnalyzer(
                RetryAnalyzer.class
        );
    }

    // ============================================================
    // SUITE START
    // ============================================================

    @Override
    public void onStart(
            ITestContext context) {

        System.out.println(
                "================================================"
        );

        System.out.println(
                "TEST SUITE STARTED: "
                        + context.getName()
        );

        System.out.println(
                "================================================"
        );

        try {

            String reportDirectory =
                    "test-output";

            Path reportPath =
                    Paths.get(
                            reportDirectory
                    );

            Files.createDirectories(
                    reportPath
            );

            String reportFile =
                    reportDirectory
                            + "/ExtentReport.html";

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(
                            reportFile
                    );

            sparkReporter.config()
                    .setDocumentTitle(
                            "OrangeHRM Automation Report"
                    );

            sparkReporter.config()
                    .setReportName(
                            "OrangeHRM Automation Test Report"
                    );

            extent =
                    new ExtentReports();

            extent.attachReporter(
                    sparkReporter
            );

            extent.setSystemInfo(
                    "Application",
                    "OrangeHRM"
            );

            extent.setSystemInfo(
                    "Environment",
                    "QA"
            );

            extent.setSystemInfo(
                    "Browser",
                    "Chrome"
            );

            extent.setSystemInfo(
                    "Framework",
                    "Selenium + TestNG"
            );

            extent.setSystemInfo(
                    "Java Version",
                    System.getProperty(
                            "java.version"
                    )
            );

            extent.setSystemInfo(
                    "OS",
                    System.getProperty(
                            "os.name"
                    )
            );

            System.out.println(
                    "Extent HTML Report initialized."
            );

            System.out.println(
                    "Report Path: "
                            + Paths.get(
                                    reportFile
                            ).toAbsolutePath()
            );

        } catch (Exception e) {

            System.out.println(
                    "Extent Report initialization failed."
            );

            e.printStackTrace();
        }
    }

    // ============================================================
    // TEST START
    // ============================================================

    @Override
    public void onTestStart(
            ITestResult result) {

        System.out.println(
                "------------------------------------------------"
        );

        System.out.println(
                "TEST STARTED: "
                        + result.getName()
        );

        System.out.println(
                "------------------------------------------------"
        );

        if (extent != null) {

            ExtentTest test =
                    extent.createTest(
                            result.getMethod()
                                    .getQualifiedName()
                    );

            extentTest.set(
                    test
            );

            test.log(
                    Status.INFO,
                    "Test execution started."
            );

            test.log(
                    Status.INFO,
                    "Test Name: "
                            + result.getName()
            );
        }
    }

    // ============================================================
    // TEST SUCCESS
    // ============================================================

    @Override
    public void onTestSuccess(
            ITestResult result) {

        System.out.println(
                "------------------------------------------------"
        );

        System.out.println(
                "TEST PASSED: "
                        + result.getName()
        );

        System.out.println(
                "------------------------------------------------"
        );

        ExtentTest test =
                extentTest.get();

        if (test != null) {

            test.log(
                    Status.PASS,
                    "Test Passed Successfully."
            );
        }
    }

    // ============================================================
    // TEST FAILURE
    // ============================================================

    @Override
    public void onTestFailure(
            ITestResult result) {

        System.out.println(
                "================================================"
        );

        System.out.println(
                "TEST FAILED: "
                        + result.getName()
        );

        System.out.println(
                "================================================"
        );

        // --------------------------------------------------------
        // EXTENT TEST
        // --------------------------------------------------------

        ExtentTest test =
                extentTest.get();

        if (test != null) {

            test.log(
                    Status.FAIL,
                    "Test Failed."
            );
        }

        // --------------------------------------------------------
        // FAILURE DETAILS
        // --------------------------------------------------------

        if (result.getThrowable() != null) {

            String failureType =
                    result.getThrowable()
                            .getClass()
                            .getSimpleName();

            String failureMessage =
                    result.getThrowable()
                            .getMessage();

            System.out.println(
                    "Failure Type: "
                            + failureType
            );

            System.out.println(
                    "Failure Message: "
                            + failureMessage
            );

            if (test != null) {

                test.log(
                        Status.FAIL,
                        "Failure Type: "
                                + failureType
                );

                test.log(
                        Status.FAIL,
                        "Failure Message: "
                                + failureMessage
                );

                test.log(
                        Status.FAIL,
                        result.getThrowable()
                );
            }
        }

        // --------------------------------------------------------
        // GET DRIVER
        // --------------------------------------------------------

        WebDriver driver =
                getDriverFromTestInstance(
                        result
                );

        // --------------------------------------------------------
        // SCREENSHOT
        // --------------------------------------------------------

        if (driver != null) {

            String screenshotPath =
                    captureScreenshot(
                            driver,
                            result.getName()
                    );

            if (screenshotPath != null
                    && test != null) {

                try {

                    test.addScreenCaptureFromPath(
                            screenshotPath
                    );

                    test.log(
                            Status.INFO,
                            "Failure screenshot attached."
                    );

                } catch (Exception e) {

                    System.out.println(
                            "Unable to attach screenshot "
                                    + "to Extent Report."
                    );

                    e.printStackTrace();
                }
            }

            printFailurePageDetails(
                    driver
            );

            if (test != null) {

                try {

                    test.log(
                            Status.INFO,
                            "Failure URL: "
                                    + driver.getCurrentUrl()
                    );

                    test.log(
                            Status.INFO,
                            "Failure Page Title: "
                                    + driver.getTitle()
                    );

                } catch (Exception e) {

                    System.out.println(
                            "Unable to add page details "
                                    + "to Extent Report."
                    );
                }
            }

        } else {

            System.out.println(
                    "WebDriver is null."
            );

            System.out.println(
                    "Screenshot cannot be captured."
            );

            if (test != null) {

                test.log(
                        Status.WARNING,
                        "WebDriver is null. "
                                + "Screenshot cannot be captured."
                );
            }
        }

        System.out.println(
                "================================================"
        );
    }

    // ============================================================
    // GET DRIVER
    // ============================================================

    private WebDriver getDriverFromTestInstance(
            ITestResult result) {

        try {

            Object testInstance =
                    result.getInstance();

            Class<?> testClass =
                    testInstance.getClass();

            while (testClass != null) {

                try {

                    java.lang.reflect.Field driverField =
                            testClass.getDeclaredField(
                                    "driver"
                            );

                    driverField.setAccessible(
                            true
                    );

                    Object driverObject =
                            driverField.get(
                                    testInstance
                            );

                    if (driverObject instanceof WebDriver) {

                        return (WebDriver) driverObject;
                    }

                } catch (NoSuchFieldException e) {

                    testClass =
                            testClass.getSuperclass();

                    continue;
                }

                break;
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve WebDriver."
            );

            e.printStackTrace();
        }

        return null;
    }

    // ============================================================
    // CAPTURE SCREENSHOT
    // ============================================================

    private String captureScreenshot(
            WebDriver driver,
            String testName) {

        try {

            String timestamp =
                    new SimpleDateFormat(
                            "yyyyMMdd_HHmmss_SSS"
                    ).format(
                            new Date()
                    );

            String directory =
                    "test-output/screenshots";

            Path screenshotDirectory =
                    Paths.get(
                            directory
                    );

            Files.createDirectories(
                    screenshotDirectory
            );

            String fileName =
                    testName
                            + "_"
                            + timestamp
                            + ".png";

            Path destination =
                    screenshotDirectory.resolve(
                            fileName
                    );

            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );

            Files.copy(
                    source.toPath(),
                    destination
            );

            System.out.println(
                    "Screenshot captured successfully."
            );

            System.out.println(
                    "Screenshot Path: "
                            + destination
                                    .toAbsolutePath()
            );

            return destination
                    .toAbsolutePath()
                    .toString();

        } catch (Exception e) {

            System.out.println(
                    "Screenshot capture failed."
            );

            e.printStackTrace();

            return null;
        }
    }

    // ============================================================
    // FAILURE PAGE DETAILS
    // ============================================================

    private void printFailurePageDetails(
            WebDriver driver) {

        try {

            System.out.println(
                    "Failure URL: "
                            + driver.getCurrentUrl()
            );

            System.out.println(
                    "Failure Page Title: "
                            + driver.getTitle()
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to capture failure page details."
            );
        }
    }

    // ============================================================
    // TEST SKIPPED
    // ============================================================

    @Override
    public void onTestSkipped(
            ITestResult result) {

        System.out.println(
                "TEST SKIPPED: "
                        + result.getName()
        );

        ExtentTest test =
                extentTest.get();

        if (test != null) {

            test.log(
                    Status.SKIP,
                    "Test Skipped."
            );

            if (result.getThrowable() != null) {

                test.log(
                        Status.SKIP,
                        result.getThrowable()
                );
            }
        }
    }

    // ============================================================
    // SUITE FINISH
    // ============================================================

    @Override
    public void onFinish(
            ITestContext context) {

        System.out.println(
                "================================================"
        );

        System.out.println(
                "TEST SUITE FINISHED: "
                        + context.getName()
        );

        System.out.println(
                "================================================"
        );

        if (extent != null) {

            extent.flush();

            System.out.println(
                    "Extent HTML Report generated successfully."
            );

            System.out.println(
                    "Report Location: "
                            + Paths.get(
                                    "test-output/ExtentReport.html"
                            )
                            .toAbsolutePath()
            );
        }

        extentTest.remove();
    }
}