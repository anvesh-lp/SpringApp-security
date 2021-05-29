package com.anvesh.springsecurity.auth;

import java.util.Optional;

public interface ApplicationUSerDao {
    Optional<ApplicationUser> selectApplicationUserByUserName(String username);
}
