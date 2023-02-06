package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.entity.AuthProvider;
import com.theinfinity.srpingbootinstagram.entity.Role;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.security.oidc.OidcUserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {
    public CustomOidcUserService() {
        super();
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser= super.loadUser(userRequest);
        try {
            return processOAuth2User(userRequest, oidcUser);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }

    }

    private OidcUser processOAuth2User(OidcUserRequest userRequest, OidcUser oidcUser) {
        OidcUserInfo oidcUserInfo = OidcUserInfoFactory.getOidcUserInfo(userRequest.getClientRegistration().getRegistrationId(), oidcUser.getAttributes());
        if(StringUtils.isEmpty(oidcUserInfo.getPreferredUsername())) {
            throw new UsernameNotFoundException("Username not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByUsername(oidcUserInfo.getPreferredUsername());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))) {
                throw new UsernameNotFoundException("sub");
            }
            user = updateExistingUser(user, oidcUserInfo);
        } else {
            user = registerNewUser(userRequest, oidcUserInfo);
        }

        return UserPrincipal.create(user, oidcUser.getAttributes());
    }

    private User registerNewUser(OidcUserRequest userRequest, OidcUserInfo oidcUserInfo) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oidcUserInfo.getSubject());
        user.setUsername(oidcUserInfo.getPreferredUsername());
        user.setEmail(oidcUserInfo.getEmail());
        user.setImageUrl(oidcUserInfo.getPicture());
        user.setLastVisit(LocalDateTime.now());
        user.setEmailVerified(oidcUserInfo.getEmailVerified());
        user.setLocale(oidcUserInfo.getLocale());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OidcUserInfo oidcUserInfo) {
        existingUser.setUsername(oidcUserInfo.getPreferredUsername());
        existingUser.setImageUrl(oidcUserInfo.getPicture());
        existingUser.setLastVisit(LocalDateTime.now());
        return userRepository.save(existingUser);
    }
}
