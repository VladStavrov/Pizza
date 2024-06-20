package modsen.interns.pizza_modsen.auth.filter;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modsen.interns.pizza_modsen.auth.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt = parseJwtFromHeader(authHeader);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                try {
                    String email = jwtUtil.getUsernameFromJwt(jwt);
                    if (StringUtils.isNotEmpty(email)) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(new SimpleGrantedAuthority(jwtUtil.getRoleFromJwt(jwt))));
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                } catch (ExpiredJwtException e) {
                    log.debug("Token lifetime has expired");
                } catch (SignatureException e) {
                    log.debug("The signature is incorrect");
                }
            } catch (Exception e) {
                log.debug("Failed to authenticate user");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwtFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}