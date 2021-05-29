package com.anvesh.springsecurity.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ApplicationUSerService implements UserDetailsService {
    private final ApplicationUSerDao applicationUSerDao;

    public ApplicationUSerService(@Qualifier("fake") ApplicationUSerDao applicationUSerDao) {
        this.applicationUSerDao = applicationUSerDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUSerDao
                .selectApplicationUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("UserName not found : " + username));
    }
}
