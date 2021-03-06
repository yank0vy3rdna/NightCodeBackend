package ru.project.study_platform.utils.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.project.study_platform.repository.UserRepository;
import ru.project.study_platform.utils.jwtUtils.JwtUtil;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String email = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractUserEmailFromToken(jwt);
        }
        if (email != null){
            if(jwtUtil.validateToken(jwt)){
                httpServletRequest.setAttribute("user", userRepository.findUserByEmail(email));
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }
        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().println("Token not found");
    }
}

