package com.orangehrm.api;

public class ApiConstants {

    // ============================================================
    // EMPLOYEE API ENDPOINTS
    // ============================================================

    public static final String GET_EMPLOYEES =
            "/web/index.php/api/v2/pim/employees";

    public static final String GET_EMPLOYEE =
            "/web/index.php/api/v2/pim/employees/{employeeId}";

    public static final String UPDATE_EMPLOYEE =
            "/web/index.php/api/v2/pim/employees/{employeeId}";

    public static final String DELETE_EMPLOYEE =
            "/web/index.php/api/v2/pim/employees";

    private ApiConstants() {
        // Prevent object creation
    }
}