package ru.project.auth.model.factories.apiUsersFactory;

import ru.project.auth.model.dto.AuthUserDTO;
import ru.project.auth.model.entities.ApiUser;

public interface ApiUserFactory {
    ApiUser createUser(AuthUserDTO authUserDTO);
}
