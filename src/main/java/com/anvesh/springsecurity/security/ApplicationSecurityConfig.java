package com.anvesh.springsecurity.security;

import com.anvesh.springsecurity.auth.ApplicationUSerService;
import com.anvesh.springsecurity.jwt.JwtConfig;
import com.anvesh.springsecurity.jwt.JwtTokenVerifier;
import com.anvesh.springsecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.anvesh.springsecurity.security.ApplicationUserRoles.STUDENT;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)/* To enable annotation based authorization */
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private final PasswordEncoder encoder;
    private final ApplicationUSerService applicationUSerService;
    private final JwtConfig config;
    private final SecretKey key;

    public ApplicationSecurityConfig(PasswordEncoder encoder, ApplicationUSerService applicationUSerService, JwtConfig config, SecretKey key) {
        this.encoder = encoder;
        this.applicationUSerService = applicationUSerService;
        this.config = config;
        this.key = key;
    }

    //    For Jwt Based Authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), config, key))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index").permitAll()
                .antMatchers("/get").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated();

    }


//    For form based Authentiation
    /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
//                Show index page to everyone irrespective of user

                .antMatchers("/", "index", "home")
                .permitAll()
//               Only users who are students can see all the list of student
*//*
                 .antMatchers("/get/*").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE, "/management/api/v1/student/").hasAnyAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/management/api/v1/student/").hasAnyAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/api/v1/student/").hasAnyAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/management/api/v1/student/").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())

                *//*
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()        //on successful login redirect to this page
                .loginPage(LOGIN_URL).permitAll()
                .defaultSuccessUrl("/courses")
                .and()
                .rememberMe() *//*To Extend user session*//*
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("something very secured")
                .and()
                .logout()
                .logoutUrl(LOGOUT_URL)
                .logoutSuccessUrl(LOGIN_URL)
                .deleteCookies("JSESSIONID", "remember-me")
                .invalidateHttpSession(true)
                .clearAuthentication(true);
    }*/

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
