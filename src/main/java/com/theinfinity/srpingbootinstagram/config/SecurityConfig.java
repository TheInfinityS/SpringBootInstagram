package com.theinfinity.srpingbootinstagram.config;

import com.theinfinity.srpingbootinstagram.security.RestAuthenticationEntryPoint;
import com.theinfinity.srpingbootinstagram.security.cookie.HttpCookieOAuth2AuthorizationRequestRepository;
import com.theinfinity.srpingbootinstagram.security.handler.OidcAuthenticationFailureHandler;
import com.theinfinity.srpingbootinstagram.security.jwt.JwtAuthenticationFilter;
import com.theinfinity.srpingbootinstagram.security.handler.OidcAuthenticationSuccessHandler;
import com.theinfinity.srpingbootinstagram.service.CustomOidcUserService;
import com.theinfinity.srpingbootinstagram.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Autowired
    private CustomOidcUserService oidcUserService;

    @Autowired
    private OidcAuthenticationSuccessHandler oidcAuthenticationSuccessHandler;

    @Autowired
    private OidcAuthenticationFailureHandler oidcAuthenticationFailureHandler;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
                .authorizeRequests(request->{
                    request.requestMatchers("/", "/login**", "/js/**", "/error**","/signup").permitAll();
                    request.anyRequest().authenticated();
                })
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {

                    httpSecurityOAuth2LoginConfigurer.userInfoEndpoint(userInfoEndpointConfig ->
                            userInfoEndpointConfig.oidcUserService(oidcUserService));

//                    httpSecurityOAuth2LoginConfigurer.authorizationEndpoint(authorizationEndpointConfig ->
//                            authorizationEndpointConfig.authorizationRequestRepository(cookieAuthorizationRequestRepository()));

//                    httpSecurityOAuth2LoginConfigurer.successHandler(oidcAuthenticationSuccessHandler);
//
//                    httpSecurityOAuth2LoginConfigurer.failureHandler(oidcAuthenticationFailureHandler);
                })
//                .sessionManagement(httpSecuritySessionManagementConfigurer ->
//                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
