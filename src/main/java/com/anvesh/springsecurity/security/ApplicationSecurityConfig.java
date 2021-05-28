package com.anvesh.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;

    public ApplicationSecurityConfig(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","index","home")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails ganesh= User.builder()
                .username("ganesh")
                .password(encoder.encode("1234"))
                .roles("STUDENT")
                .build();
        UserDetails  anvesh=User.builder()
                .username("anvesh")
                .password(encoder.encode("1234"))
                .roles(ApplicationUserRoles.ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(ganesh);
    }
}
