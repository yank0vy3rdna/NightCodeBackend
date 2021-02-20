package ru.project.auth.model.factories.apiUsersFactory;

import org.springframework.stereotype.Service;
import ru.project.auth.model.dto.AuthUserDTO;
import ru.project.auth.model.entities.ApiUser;

@Service
public class ApiUserFactoryImpl implements ApiUserFactory {
    @Override
    public ApiUser createUser(AuthUserDTO authUserDTO) {
        ApiUser apiUser = new ApiUser();
        apiUser.setEmail(apiUser.getEmail());
        apiUser.setGroupNumber(authUserDTO.getGroupNumber());
        apiUser.setSpecialization(authUserDTO.getFaculty());
        apiUser.setEmail(authUserDTO.getEmail());
        apiUser.setName(authUserDTO.getUsername());
        apiUser.setSurname(authUserDTO.getUserlastname());
        return apiUser;
    }
}
