package com.theinfinity.srpingbootinstagram.security.oidc;

import com.theinfinity.srpingbootinstagram.entity.AuthProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public class OidcUserInfoFactory {
    public static OidcUserInfo getOidcUserInfo(String registrationId, Map<String, Object> claims) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOIdcUserInfo(claims);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
            return new GithubOidcUserInfo(claims);
        } else {
            throw new UsernameNotFoundException("sub");
        }
    }
}
