package com.example.auth.constants;

public class Constants {
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60;
    public static final int ACCESS_TOKEN_EXPIRATION_TIME = 24 * 60 * 60;
    public static final String JWT_SECRET = "your-secret-key-your-secret-key-your-secret-key-your-secret-key-your-secret-key";
    public static final String TYPE = "type";
    public static final String FIELD = "field";
    public static final String MESSAGE = "message";
    public static final String VALIDATION_FAILED = "Validation Failed";
    public static final String INVALID_PARAMETER = "Invalid parameter: ";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String UNAUTHORIZED_NO_AUTHENTICATED_USER_FOUND = "Unauthorized: No authenticated user found";
    public static final String PROJECT_NOT_FOUND = "Project not found";
    public static final String ACCESS = "access";
    public static final String REFRESH = "refresh";
    public static final String PERMISSIONS = "permissions";
    public static final String COMMENTS = "comments";
    public static final String EMPLOYEES = "employees";
    public static final String PROJECTS = "projects";
    public static final String ROLES = "roles";
    public static final String TASKS = "tasks";
    public static final String DEFAULT_EMPLOYEE_TYPE = "EMPLOYEE";
    public static final String USER_NOT_FOUND = "User not found";
}
