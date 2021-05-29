package com.anvesh.springsecurity.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.anvesh.springsecurity.security.ApplicationUserRoles.*;


@Repository("fake")
public class ApplicationUSerDaoImpl implements ApplicationUSerDao {
    private final PasswordEncoder passwordEncoder;

    public ApplicationUSerDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> applicationUser.getUsername().equals(username))
                .findFirst();
    }


    public List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> users = new ArrayList<>(List.of(
                new ApplicationUser(
                        ADMIN.getAuthorities(),
                        "anvesh",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMINTRAINEE.getAuthorities(),
                        "ganesh",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        STUDENT.getAuthorities(),
                        "tom",
                        passwordEncoder.encode("1234"),
                        true,
                        true,
                        true,
                        true
                )
        ));
        return users;
    }
}
