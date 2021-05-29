package com.anvesh.springsecurity.jwt;


import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String secretkey;
    private String tokenprefix;
    private String tokenexpirationdate;

    public String getAuthorizationHeader(String secretkey) {
        return HttpHeaders.AUTHORIZATION;
    }
}
