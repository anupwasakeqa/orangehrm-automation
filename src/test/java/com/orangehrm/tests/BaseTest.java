package com.orangehrm.tests;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.orangehrm.listeners.TestListener;
import com.orangehrm.utils.ConfigReader;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver driver;

    // ============================================================
    // SETUP
    // ============================================================

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        System.out.println(
                "================================================"
        );

        System.out.println(
                "TEST SETUP STARTED"
        );

        System.out.println(
                "================================================"
        );

        // ========================================================
        // READ CONFIGURATION
        // ========================================================

        String environment =
                ConfigReader.get("environment");

        String browser =
                ConfigReader.get("browser");

        String baseUrl =
                ConfigReader.get("baseUrl");

        int implicitWait =
                ConfigReader.getInt("implicitWait");

        int configuredPageLoadTimeout =
                ConfigReader.getInt("pageLoadTimeout");

        System.out.println(
                "Environment       : "
                        + environment
        );

        System.out.println(
                "Browser            : "
                        + browser
        );

        System.out.println(
                "Base URL           : "
                        + baseUrl
        );

        System.out.println(
                "Implicit Wait      : "
                        + implicitWait
                        + " seconds"
        );

        System.out.println(
                "Configured Page Load Timeout : "
                        + configuredPageLoadTimeout
                        + " seconds"
        );

        // ========================================================
        // CREATE BROWSER
        // ========================================================

        driver = createDriver(browser);

        // ========================================================
        // SELENIUM TIMEOUTS
        // ========================================================

        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(
                                implicitWait
                        )
                );

        int pageLoadTimeout =
                configuredPageLoadTimeout > 60
                        ? 60
                        : configuredPageLoadTimeout;

        driver.manage()
                .timeouts()
                .pageLoadTimeout(
                        Duration.ofSeconds(
                                pageLoadTimeout
                        )
                );

        // ========================================================
        // OPEN APPLICATION
        // ========================================================

        openApplication(baseUrl);

        System.out.println(
                "================================================"
        );
    }

    // ============================================================
    // OPEN APPLICATION
    // ============================================================

    private void openApplication(String baseUrl) {

        System.out.println(
                "Opening OrangeHRM application..."
        );

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {

                System.out.println(
                        "Navigation attempt "
                                + attempt
                                + " of "
                                + maxAttempts
                );

                driver.get(baseUrl);

                try {

                    Thread.sleep(1500);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            "Thread interrupted while waiting for OrangeHRM.",
                            e
                    );
                }

                System.out.println(
                        "Application opened successfully."
                );

                System.out.println(
                        "Current URL: "
                                + driver.getCurrentUrl()
                );

                System.out.println(
                        "Page Title: "
                                + driver.getTitle()
                );

                return;

            } catch (Exception e) {

                System.out.println(
                        "Navigation attempt "
                                + attempt
                                + " failed."
                );

                System.out.println(
                        "Navigation error: "
                                + e.getMessage()
                );

                if (attempt == maxAttempts) {

                    throw e;
                }

                try {

                    Thread.sleep(2000);

                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            "Thread interrupted during navigation retry.",
                            interruptedException
                    );
                }
            }
        }
    }

    // ============================================================
    // CREATE DRIVER
    // ============================================================

    private WebDriver createDriver(String browser) {

        if (browser == null ||
                browser.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Browser configuration is missing."
            );
        }

        switch (browser.trim().toLowerCase()) {

            case "chrome":

                return createChromeDriver();

            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: "
                                + browser
                                + ". Currently supported: chrome"
                );
        }
    }

    // ============================================================
    // CREATE CHROME DRIVER
    // ============================================================

    private WebDriver createChromeDriver() {

        System.out.println(
                "Starting Chrome browser..."
        );

        ChromeOptions options =
                new ChromeOptions();

        // ========================================================
        // PAGE LOAD STRATEGY
        // ========================================================

        options.setPageLoadStrategy(
                org.openqa.selenium.PageLoadStrategy.EAGER
        );

        // ========================================================
        // CI CONFIGURATION
        // ========================================================

        String ci =
                System.getenv("CI");

        String display =
                System.getenv("DISPLAY");

        if ("true".equalsIgnoreCase(ci)) {

            System.out.println(
                    "CI environment detected."
            );

            /*
             * If DISPLAY is available, GitHub Actions is running
             * Chrome on Xvfb virtual display.
             *
             * This allows FFmpeg to record the browser screen.
             */
            if (display != null &&
                    !display.trim().isEmpty()) {

                System.out.println(
                        "Virtual display detected: "
                                + display
                );

                System.out.println(
                        "Running Chrome in headed mode "
                                + "for video recording."
                );

            } else {

                /*
                 * Fallback for CI environments without DISPLAY.
                 */
                System.out.println(
                        "No DISPLAY detected."
                );

                System.out.println(
                        "Running Chrome in headless mode."
                );

                options.addArguments(
                        "--headless=new"
                );
            }

            options.addArguments(
                    "--no-sandbox"
            );

            options.addArguments(
                    "--disable-dev-shm-usage"
            );

            options.addArguments(
                    "--disable-gpu"
            );

            options.addArguments(
                    "--window-size=1920,1080"
            );

            options.addArguments(
                    "--disable-software-rasterizer"
            );

            options.addArguments(
                    "--disable-extensions"
            );

            options.addArguments(
                    "--disable-background-networking"
            );

            options.addArguments(
                    "--disable-background-timer-throttling"
            );

            options.addArguments(
                    "--disable-renderer-backgrounding"
            );

            options.addArguments(
                    "--disable-features=Translate,BackForwardCache"
            );
        }

        // ========================================================
        // COMMON CHROME OPTIONS
        // ========================================================

        options.addArguments(
                "--disable-notifications"
        );

        options.addArguments(
                "--disable-popup-blocking"
        );

        options.addArguments(
                "--remote-allow-origins=*"
        );

        options.addArguments(
                "--disable-infobars"
        );

        return new ChromeDriver(options);
    }

    // ============================================================
    // GET DRIVER
    // ============================================================

    public WebDriver getDriver() {

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is not initialized."
            );
        }

        return driver;
    }

    // ============================================================
    // GET COOKIES
    // ============================================================

    public Map<String, String> getCookies() {

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is not initialized."
            );
        }

        return driver.manage()
                .getCookies()
                .stream()
                .collect(
                        Collectors.toMap(
                                cookie ->
                                        cookie.getName(),

                                cookie ->
                                        cookie.getValue(),

                                (existing, replacement) ->
                                        replacement
                        )
                );
    }

    // ============================================================
    // GET BASE URL
    // ============================================================

    public String getBaseUrl() {

        return ConfigReader.get(
                "baseUrl"
        );
    }

    // ============================================================
    // GET ENVIRONMENT
    // ============================================================

    public String getEnvironment() {

        return ConfigReader.get(
                "environment"
        );
    }

    // ============================================================
    // GET USERNAME
    // ============================================================

    public String getUsername() {

        return ConfigReader.get(
                "username"
        );
    }

    // ============================================================
    // GET PASSWORD
    // ============================================================

    public String getPassword() {

        return ConfigReader.get(
                "password"
        );
    }

    // ============================================================
    // TEARDOWN
    // ============================================================

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        System.out.println(
                "================================================"
        );

        System.out.println(
                "Closing browser..."
        );

        System.out.println(
                "================================================"
        );

        if (driver != null) {

            try {

                driver.quit();

                System.out.println(
                        "Browser closed successfully."
                );

            } catch (Exception e) {

                System.out.println(
                        "Error while closing browser: "
                                + e.getMessage()
                );

            } finally {

                driver = null;
            }
        }
    }
}