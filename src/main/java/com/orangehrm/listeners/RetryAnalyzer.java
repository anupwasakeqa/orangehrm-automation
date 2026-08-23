package com.orangehrm.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;

    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < MAX_RETRY_COUNT) {

            retryCount++;

            System.out.println(
                    "================================================"
            );

            System.out.println(
                    "RETRYING FAILED TEST"
            );

            System.out.println(
                    "Test Name: "
                            + result.getName()
            );

            System.out.println(
                    "Retry Attempt: "
                            + retryCount
                            + " / "
                            + MAX_RETRY_COUNT
            );

            if (result.getThrowable() != null) {

                System.out.println(
                        "Failure Reason: "
                                + result.getThrowable().getMessage()
                );
            }

            System.out.println(
                    "================================================"
            );

            return true;
        }

        System.out.println(
                "Maximum retry attempts reached for test: "
                        + result.getName()
        );

        return false;
    }
}