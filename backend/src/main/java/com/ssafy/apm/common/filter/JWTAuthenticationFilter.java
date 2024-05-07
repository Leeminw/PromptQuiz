package com.ssafy.apm.common.filter;

import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.common.domain.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.user.exceptions.UserNotFoundException;
import com.ssafy.apm.user.repository.RefreshTokenRepository;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Slf4j
@Component
@CrossOrigin
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null || !jwtToken.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        Map<String, Object> result = new HashMap<>();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpStatus.CREATED.value());

        String providerResult = jwtProvider.validateToken(jwtToken);
        result.put("result", providerResult);

        if (providerResult.equals("access")) {
            log.debug("Access Token Filter");
            setAuthenticatedUserFromJwt(jwtToken);
            chain.doFilter(request, response);
            return;
        } else if (providerResult.equals("refresh")) {
            log.debug("refresh token!! ");

            result.put("accessToken", refreshAccessToken(jwtToken));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            log.debug("un authorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        response.getWriter().flush();
    }

    public void setAuthenticatedUserFromJwt(String jwtToken){
        User user = userRepository.findById(jwtProvider.getUserId(jwtToken))
                .orElseThrow(() -> new UserNotFoundException("forbidden"));

        Authentication auth = getAuthentication(user);

        log.debug("auth : {}", auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


    public String refreshAccessToken(String jwtToken){
        String token = jwtToken.replace("Bearer ", "");
        refreshTokenRepository.findById(token)
                .orElseThrow(() -> new NoSuchElementException("invalid token"));

        User user = userRepository.findById(jwtProvider.getUserId(jwtToken))
                .orElseThrow(() -> new UserNotFoundException("forbidden"));

        return jwtProvider.createAccessToken(user.getId(), user.getRole());
    }

    public Authentication getAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().contains("logout");
    }

}
