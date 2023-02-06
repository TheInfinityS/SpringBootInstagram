package com.theinfinity.srpingbootinstagram.security.oidc;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public class GithubOidcUserInfo extends OidcUserInfo {
    public GithubOidcUserInfo(Map<String, Object> claims) {
        super(claims);
    }
    @Override
    public String getPreferredUsername() {
        return (String) this.getClaims().get("name");
    }

    @Override
    public String getSubject() {
        return (String) this.getClaims().get("id");
    }
    @Override
    public String getPicture() {
        return (String) this.getClaims().get("avatar_url");
    }

    @Override
    public String getEmail() {
        return (String) this.getClaims().get("email");
    }

    @Override
    public String getGender() {
        return (String) this.getClaims().get("gender");
    }


    @Override
    public String getLocale() {
        return (String) this.getClaims().get("locale");
    }

}
