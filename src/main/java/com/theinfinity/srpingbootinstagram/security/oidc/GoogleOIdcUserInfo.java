package com.theinfinity.srpingbootinstagram.security.oidc;

import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;

public class GoogleOIdcUserInfo extends OidcUserInfo {


    public GoogleOIdcUserInfo(Map<String, Object> claims) {
        super(claims);
    }

    @Override
    public String getPreferredUsername() {
        return (String) this.getClaims().get("name");
    }

    @Override
    public String getSubject() {
        return (String) this.getClaims().get("sub");
    }


    @Override
    public String getPicture() {
        return (String) this.getClaims().get("picture");
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
    public String getPhoneNumber() {
        return (String) this.getClaims().get("phoneNumber");
    }

    @Override
    public String getLocale() {
        return (String) this.getClaims().get("locale");
    }
}
