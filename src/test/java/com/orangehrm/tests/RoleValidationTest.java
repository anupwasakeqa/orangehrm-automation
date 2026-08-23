package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.RoleValidationPage;

public class RoleValidationTest extends BaseTest {

    @Test
    public void validateAdminUserRoleTest() {

        System.out.println(
                "================================================"
        );

        System.out.println(
                "ROLE VALIDATION TEST STARTED"
        );

        System.out.println(
                "================================================"
        );

        // ========================================================
        // LOGIN
        // ========================================================

        LoginPage loginPage =
                new LoginPage(driver);

        String username =
                getUsername();

        String password =
                getPassword();

        System.out.println(
                "Login username: "
                        + username
        );

        loginPage.login(
                username,
                password
        );

        System.out.println(
                "Login successful."
        );

        // ========================================================
        // ROLE VALIDATION PAGE
        // ========================================================

        RoleValidationPage roleValidationPage =
                new RoleValidationPage(driver);

        // ========================================================
        // OPEN ADMIN MODULE
        // ========================================================

        System.out.println(
                "Opening Admin module..."
        );

        roleValidationPage.openAdminModule();

        System.out.println(
                "Admin module opened."
        );

        // ========================================================
        // OPEN USER MANAGEMENT
        // ========================================================

        System.out.println(
                "Opening User Management..."
        );

        roleValidationPage.openUserManagement();

        System.out.println(
                "User Management opened."
        );

        // ========================================================
        // OPEN USERS
        // ========================================================

        System.out.println(
                "Opening Users..."
        );

        roleValidationPage.openUsers();

        System.out.println(
                "Users page opened."
        );

        // ========================================================
        // USER DATA
        // ========================================================

        String expectedRole =
                "Admin";

        System.out.println(
                "Username: "
                        + username
        );

        System.out.println(
                "Expected Role: "
                        + expectedRole
        );

        // ========================================================
        // SEARCH USER
        // ========================================================

        System.out.println(
                "Searching user..."
        );

        roleValidationPage.searchUser(
                username
        );

        // ========================================================
        // VERIFY USER EXISTS
        // ========================================================

        boolean userExists =
                roleValidationPage.isUserDisplayed(
                        username
                );

        Assert.assertTrue(
                userExists,
                "User '"
                        + username
                        + "' was not found in User Management."
        );

        System.out.println(
                "User found successfully."
        );

        // ========================================================
        // GET ACTUAL ROLE
        // ========================================================

        String actualRole =
                roleValidationPage.getActualRole(
                        username
                );

        System.out.println(
                "Actual Role: "
                        + actualRole
        );

        // ========================================================
        // VERIFY ROLE
        // ========================================================

        Assert.assertEquals(
                actualRole,
                expectedRole,
                "Role mismatch for user '"
                        + username
                        + "'. Expected: "
                        + expectedRole
                        + ", Actual: "
                        + actualRole
        );

        System.out.println(
                "Role validation passed."
        );

        // ========================================================
        // FINAL SUCCESS
        // ========================================================

        System.out.println(
                "================================================"
        );

        System.out.println(
                "ROLE VALIDATION TEST PASSED"
        );

        System.out.println(
                "Username: "
                        + username
        );

        System.out.println(
                "Expected Role: "
                        + expectedRole
        );

        System.out.println(
                "Actual Role: "
                        + actualRole
        );

        System.out.println(
                "================================================"
        );
    }
}