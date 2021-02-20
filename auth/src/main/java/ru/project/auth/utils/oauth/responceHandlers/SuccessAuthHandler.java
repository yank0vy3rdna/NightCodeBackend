package ru.project.auth.utils.oauth.responceHandlers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ru.project.auth.model.entities.AuthType;
import ru.project.auth.model.entities.AuthUser;
import ru.project.auth.model.factories.userFactories.AuthUserFactory;
import ru.project.auth.model.repository.AuthUserRepository;
import ru.project.auth.utils.tokens.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Component
@Log4j2
public class SuccessAuthHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthUserRepository userRepository;
    private final AuthUserFactory userFactory;
    private final JwtUtil jwtUtil;
    private final String REDIRECT_SUCCESS_URI = "http://localhost:8080";

    @Autowired
    public SuccessAuthHandler(AuthUserRepository userRepository, AuthUserFactory userFactory, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.jwtUtil = jwtUtil;
    }


    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, AuthUser user) {

        String targetUrl = REDIRECT_SUCCESS_URI;
        String token = jwtUtil.generateToken(user);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        try {
            OAuth2User auth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();

            String email = auth2User.getAttribute("email");
            String name  = auth2User.getAttribute("name");
            AuthUser user  = userRepository.findByEmail(email);

            if (Objects.isNull(user)) {
                user = userFactory.createOAuthUser(email, name, AuthType.GOOGLE);
                userRepository.saveAndFlush(user);
                httpServletResponse.setStatus(201);
            } else {
                httpServletResponse.setStatus(200);
            }
            String targetUrl = determineTargetUrl(httpServletRequest,httpServletResponse,user);
            getRedirectStrategy().sendRedirect(httpServletRequest,httpServletResponse, targetUrl);

        } catch (Exception exception) {
            log.warn(exception::toString);
            exception.printStackTrace();
            httpServletResponse.setStatus(500);
            httpServletResponse.getWriter().println(exception.toString());
        }
    }

}
