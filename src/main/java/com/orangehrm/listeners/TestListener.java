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

public class TestListener
        implements ITestListener, IAnnotationTransformer {

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
        // FAILURE DETAILS
        // --------------------------------------------------------

        if (result.getThrowable() != null) {

            System.out.println(
                    "Failure Type: "
                            + result.getThrowable()
                                    .getClass()
                                    .getSimpleName()
            );

            System.out.println(
                    "Failure Message: "
                            + result.getThrowable()
                                    .getMessage()
            );
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

            captureScreenshot(
                    driver,
                    result.getName()
            );

            printFailurePageDetails(
                    driver
            );

        } else {

            System.out.println(
                    "WebDriver is null."
            );

            System.out.println(
                    "Screenshot cannot be captured."
            );
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

    private void captureScreenshot(
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

        } catch (Exception e) {

            System.out.println(
                    "Screenshot capture failed."
            );

            e.printStackTrace();
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
    }
}