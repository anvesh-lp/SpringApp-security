package com.anvesh.springsecurity.security;

import com.anvesh.springsecurity.auth.ApplicationUSerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)/* To enable annotation based authorization */
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private final PasswordEncoder encoder;
    private final ApplicationUSerService applicationUSerService;

    public ApplicationSecurityConfig(PasswordEncoder encoder, ApplicationUSerService applicationUSerService) {
        this.encoder = encoder;
        this.applicationUSerService = applicationUSerService;
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
                .formLogin()        //on successful login redirect to this page
                .loginPage(LOGIN_URL).permitAll()
                .defaultSuccessUrl("/courses")
                .and()
                .rememberMe() /*To Extend user session*/
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("something very secured")
                .and()
                .logout()
                .logoutUrl(LOGOUT_URL)
                .logoutSuccessUrl(LOGIN_URL)
                .deleteCookies("JSESSIONID", "remember-me")
                .invalidateHttpSession(true)
                .clearAuthentication(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(applicationUSerService);
        return provider;
    }
}
