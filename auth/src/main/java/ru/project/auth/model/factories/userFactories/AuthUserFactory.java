package ru.project.auth.model.factories.userFactories;

import ru.project.auth.model.dto.AuthUserDTO;
import ru.project.auth.model.entities.AuthType;
import ru.project.auth.model.entities.ClientRole;
import ru.project.auth.model.entities.AuthUser;

public interface AuthUserFactory {
    AuthUser createDefaultUser(AuthUserDTO userDTO, ClientRole role);
    AuthUser createOAuthUser (String email, String username, AuthType authType);
}
