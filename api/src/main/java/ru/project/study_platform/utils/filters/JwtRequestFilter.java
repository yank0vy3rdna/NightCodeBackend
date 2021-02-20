package ru.project.study_platform.utils.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.project.auth.model.entities.AuthUser;
import ru.project.auth.model.repository.AuthUserRepository;
import ru.project.auth.utils.tokens.JwtUtil;



import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AuthUserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(AuthUserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        Long userId = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            userId = Long.valueOf(jwtUtil.extractUserIdFromToken(jwt));
        }

        if (userId != null){
            AuthUser user = authUserRepository.getOne(userId);
            if(jwtUtil.validateToken(jwt, user)){
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }
        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().println("Token not found");
    }
}

