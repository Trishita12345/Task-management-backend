package com.example.auth.filter;

import com.example.auth.constants.Constants;
import com.example.auth.model.dto.auth.LoginRequestDto;
import com.example.auth.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("JWTAuthenticationFilter");
        if(!request.getServletPath().equals("/generate-token")){
            filterChain.doFilter(request, response);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // Validate
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginRequestDto);

        final Map<String, String> errors = new HashMap<>();
        // Handle violations
        if (!violations.isEmpty()) {
            violations.forEach(
                    v -> errors.put(
                        v.getPropertyPath().toString(), v.getMessage()
                ));
        }
        if(!errors.isEmpty()){
            // Return 400 with error details
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(),
                    Map.of(
                            "timestamp", LocalDateTime.now().toString(),
                            Constants.MESSAGE, "Validation Failed",
                            "errors", errors
                    )
            );
        } else {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            try {
                Authentication authResult = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                String token = JWTUtil.generateToken(authResult, Constants.ACCESS);
                response.setHeader("Authorization", "Bearer " +token);
                // access token in cookie
                /* Cookie accessCookie = new Cookie(Constants.ACCESS, token);
                accessCookie.setHttpOnly(false); //javascript can access it
                accessCookie.setSecure(false); // sent only over HTTPS
                accessCookie.setPath("/"); // Cookie available for all endpoints
                accessCookie.setMaxAge(Constants.ACCESS_TOKEN_EXPIRATION_TIME); // 1 days expiry
                response.addCookie(accessCookie); */
                //Refresh token
                String refreshToken = JWTUtil.generateToken(authResult, Constants.REFRESH);
                Cookie refreshCookie = new Cookie(Constants.REFRESH, refreshToken);
                refreshCookie.setHttpOnly(true); //prevent javascript from accessing it
                refreshCookie.setSecure(false); // sent only over HTTPS
                refreshCookie.setPath("/refresh-token"); // Cookie available only for refresh endpoint
                refreshCookie.setMaxAge(Constants.REFRESH_TOKEN_EXPIRATION_TIME); // 7 days expiry
                response.addCookie(refreshCookie);
                response.setContentType("application/json");
                objectMapper.writeValue(response.getWriter(),
                        Map.of(
                                "timestamp", LocalDateTime.now().toString(),
                                Constants.MESSAGE, "Logged in Successfully",
                                Constants.ACCESS, token
                        )
                );

            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                objectMapper.writeValue(response.getWriter(),
                        Map.of(
                                "timestamp", LocalDateTime.now().toString(),
                                Constants.MESSAGE, "Email or Password is invalid"
                        )
                );
            }

        }
    }


}
