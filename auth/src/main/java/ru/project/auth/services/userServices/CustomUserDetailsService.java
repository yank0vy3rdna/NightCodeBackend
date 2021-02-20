package ru.project.auth.services.userServices;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.project.auth.model.entities.AuthUser;
import ru.project.auth.model.repository.AuthUserRepository;

import java.util.Objects;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final AuthUserRepository userRepository;

  @Autowired
  public CustomUserDetailsService(AuthUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    AuthUser user = userRepository.findByEmail(email);

    if (Objects.isNull(user)) {
      log.warn(() -> "user with login not " + email +  " found or does not enabled!");
      throw new UsernameNotFoundException("user not found");
    }

    log.info(() -> "IN CustomUserDetailsService user with login " + email +  " upload from DB");
    return user;
  }
}
