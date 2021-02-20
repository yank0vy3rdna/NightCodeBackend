package ru.project.auth.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.client.RestTemplate;
import ru.project.auth.utils.oauth.responceHandlers.FailAuthHandler;
import ru.project.auth.utils.oauth.responceHandlers.SuccessAuthHandler;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource;
    private final SuccessAuthHandler successAuthHandler;

    @Autowired
    public SecurityConfiguration(DataSource dataSource, SuccessAuthHandler successAuthHandler) {
        this.dataSource = dataSource;
        this.successAuthHandler = successAuthHandler;
    }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.authorizeRequests().antMatchers("/api/**").authenticated();
            http.oauth2Login()
                    .successHandler(successAuthHandler)
                    .failureHandler(new FailAuthHandler());
            http.oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/sign-in/auth-via")
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*");


            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }




}


