package com.anvesh.springsecurity.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.anvesh.springsecurity.security.ApplicationUserPermissions.*;

public enum ApplicationUserRoles {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE));

    private final Set<ApplicationUserPermissions> permissionsSet;

    ApplicationUserRoles(Set<ApplicationUserPermissions> permissionsSet) {
        this.permissionsSet = permissionsSet;
    }

    public Set<ApplicationUserPermissions> getPermissionsSet() {
        return permissionsSet;
    }
}
