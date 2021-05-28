package com.anvesh.springsecurity.security;

public enum ApplicationUserPermissions {
    STUDENT_READ("Student:read"),
    STUDENT_WRITE("Student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("curse:write");

    private final String permission;

    ApplicationUserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
