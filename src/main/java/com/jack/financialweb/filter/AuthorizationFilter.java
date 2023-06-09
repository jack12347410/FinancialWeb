package com.jack.financialweb.filter;

import com.jack.financialweb.exception.InvalidRequestException;
import com.jack.financialweb.exception.NotFoundException;
import com.jack.financialweb.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.tomcat.websocket.Constants.UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        String header = request.getHeader(AUTHORIZATION);
//        String bearer = "Bearer ";
//        if (header != null && header.startsWith(bearer)) {
//            String token = header.substring(bearer.length());
//            JwtUtil.ValidToken(token);
//        }
//        filterChain.doFilter(request, response);

        if (!request.getServletPath().contains("/api/auth/")) {
            String header = request.getHeader(AUTHORIZATION);
            String bearer = "Bearer ";
            if (header != null && header.startsWith(bearer)) {
                String token = header.substring(bearer.length());
                if(JwtUtil.ValidToken(token)){
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            response.setStatus(UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
        return;
    }
}
