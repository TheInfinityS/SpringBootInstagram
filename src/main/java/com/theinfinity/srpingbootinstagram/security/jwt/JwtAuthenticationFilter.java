package com.theinfinity.srpingbootinstagram.security.jwt;

import com.theinfinity.srpingbootinstagram.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String username;
        System.out.println(HttpHeaders.AUTHORIZATION);
        if(authHeader==null||!authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        username=jwtService.extractUsername(jwt);
        Long userId = jwtService.getUserIdFromToken(jwt);
        System.out.println("Jwt "+request);
        if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {


            UserDetails userDetails = userDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
//        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
//            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
//            if(jwtService.isTokenValid(jwt,userDetails)){
//                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
        filterChain.doFilter(request,response);
    }
}
