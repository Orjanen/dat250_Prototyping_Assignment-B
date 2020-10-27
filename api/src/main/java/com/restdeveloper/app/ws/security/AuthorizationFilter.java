package com.restdeveloper.app.ws.security;

import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.service.implementation.UserPrincipals;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        };

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null){
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (user != null){
                UserEntity userEntity = userRepository.findByEmail(user);
                if (userEntity == null) return null;
                UserPrincipals userPrincipals = new UserPrincipals(userEntity);
                return new UsernamePasswordAuthenticationToken(userPrincipals, null, userPrincipals.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
