package com.anvesh.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.concurrent.TimeUnit;

import static com.anvesh.springsecurity.security.ApplicationUserRoles.*;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)/* To enable annotation based authorization */
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;

    public ApplicationSecurityConfig(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
//                Show index page to everyone irrespective of user

                .antMatchers("/", "index", "home")
                .permitAll()
//               Only users who are students can see all the list of student
/*
                 .antMatchers("/get/*").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE, "/management/api/v1/student/").hasAnyAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/management/api/v1/student/").hasAnyAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/api/v1/student/").hasAnyAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/management/api/v1/student/").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())

                */
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();
                .formLogin().loginPage("/login").permitAll()
//                on successful login redirect to this page
                .defaultSuccessUrl("/courses")
                .and()
//                To Extend user session
                .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("something very secured");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails ganesh = User.builder()
                .username("ganesh")
                .password(encoder.encode("1234"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getAuthorities())
                .build();
        UserDetails anvesh = User.builder()
                .username("anvesh")
                .password(encoder.encode("1234"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getAuthorities())
                .build();
        UserDetails adminTrainee = User.builder()
                .username("anveshTrainee")
                .password(encoder.encode("1234"))
//                .roles(ADMIN_TRAINEE.name())
                .authorities(ADMINTRAINEE.getAuthorities())
                .build();
        return new InMemoryUserDetailsManager(ganesh, anvesh, adminTrainee);
    }
}
