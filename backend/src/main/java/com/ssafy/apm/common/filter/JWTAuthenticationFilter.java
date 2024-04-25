package com.ssafy.apm.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.apm.common.domain.JwtProvider;
import com.ssafy.apm.user.domain.RefreshToken;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.exceptions.UserNotFoundException;
import com.ssafy.apm.user.repository.RefreshTokenRepository;
import com.ssafy.apm.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@CrossOrigin
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null || !jwtToken.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        Map<String,Object> result = new HashMap<>();
        response.setHeader("Access-Control-Allow-Origin", "https://k10a509.p.ssafy.io/");
        response.setStatus(HttpStatus.CREATED.value());
        String providerResult = jwtProvider.validateToken(jwtToken);
        if (providerResult.equals("access")) {
            log.debug("Access Token Filter");
            User user = userRepository.findById(jwtProvider.getUserId(jwtToken))
                    .orElseThrow(() -> new UserNotFoundException("forbidden"));
            Authentication auth = getAuthentication(user);
            log.debug("auth : {}", auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
            return;
        }else if (providerResult.equals("refresh")){
            log.debug("refresh token!! ");
            String token = jwtToken.replace("Bearer ", "");
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(token);
            if(refreshToken.isEmpty()){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            else{
                User user = userRepository.findById(jwtProvider.getUserId(jwtToken))
                        .orElseThrow(() -> new UserNotFoundException("forbidden"));
                String accessToken = jwtProvider.createAccessToken(user.getId(), user.getRole());
                response.setStatus(HttpServletResponse.SC_OK);
                result.put("accessToken", accessToken);
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        result.put("result", providerResult);
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        response.getWriter().flush();
    }

    public Authentication getAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(),  List.of(new SimpleGrantedAuthority(user.getRole())));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().contains("logout");
    }

}
