package ru.project.auth.model.factories.userFactories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.project.auth.model.dto.UserDTO;
import ru.project.auth.model.entities.AuthType;
import ru.project.auth.model.entities.ClientRole;
import ru.project.auth.model.entities.AuthUser;

@Component
public class UserFactoryImpl implements UserFactory {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthUser createDefaultUser(UserDTO userDTO, ClientRole role) {
        AuthUser user = createStandardUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(false);
        user.setAuthType(AuthType.DEFAULT);
        user.setRole(role);
        return user;
    }

    @Override
    public AuthUser createOAuthUser(String email, String username, AuthType authType) {
        AuthUser user = createStandardUser(new UserDTO(username,email));
        user.setRole(ClientRole.STUDENT);
        user.setEnabled(true);
        user.setAuthType(authType);
        return user;
    }

    private AuthUser createStandardUser(UserDTO userDTO){
        return AuthUser.builder()
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .facutly(userDTO.getFaculty())
                .vkId(userDTO.getVkId())
                .build();
    }
}
