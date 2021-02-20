package ru.project.auth.services.userServices.impls;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.auth.model.dto.AuthUserDTO;
import ru.project.auth.model.entities.ApiUser;
import ru.project.auth.model.entities.ClientRole;
import ru.project.auth.model.entities.AuthUser;
import ru.project.auth.model.factories.apiUsersFactory.ApiUserFactory;
import ru.project.auth.model.factories.userFactories.AuthUserFactory;
import ru.project.auth.model.repository.ApiUserRepository;
import ru.project.auth.model.repository.AuthUserRepository;
import ru.project.auth.services.userServices.AuthUserService;
import ru.project.auth.services.userServices.exceptions.SamePasswordException;
import ru.project.auth.services.userServices.exceptions.UserAlreadyExistException;
import ru.project.auth.services.userServices.exceptions.UserDoesNotExistException;

import java.util.Objects;

@Log4j2
@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthUserRepository userRepository;
    private final AuthUserFactory userFactory;
    private final ApiUserRepository apiUserRepository;
    private final ApiUserFactory apiUserFactory;


    @Autowired
    public AuthUserServiceImpl(BCryptPasswordEncoder passwordEncoder, AuthUserRepository userRepository, AuthUserFactory userFactory, ApiUserRepository apiUserRepository, ApiUserFactory apiUserFactory) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.apiUserRepository = apiUserRepository;
        this.apiUserFactory = apiUserFactory;
    }

    @Override
    public AuthUser signUp(AuthUserDTO userDTO) throws UserAlreadyExistException {
        log.info(() -> "IN UserService new user try to register!");

        AuthUser checkedUser = userRepository.findByEmail(userDTO.getEmail());

        if(Objects.nonNull(checkedUser)){
            log.warn(() -> "IN UserService (sighUp) user with email" + userDTO.getEmail() + " is already exist! registration was rejected!");
            throw new UserAlreadyExistException();
        }

        checkedUser = userFactory.createDefaultUser(userDTO, ClientRole.STUDENT);
        ApiUser apiUser = apiUserFactory.createUser(userDTO);
        userRepository.saveAndFlush(checkedUser);
        apiUserRepository.saveAndFlush(apiUser);
        System.out.println("LOLOLOLOL");
        log.info(() -> "new user successfully loaded in db!");
        return checkedUser;
    }

    @Override
    public AuthUser changeRole(String email, ClientRole role) throws UserDoesNotExistException {
        AuthUser updateUser = userRepository.findByEmail(email);
        if(Objects.isNull(updateUser)){
            log.warn(() -> "IN UserService (changeRole) user with email" + email + " does not exist!");
            throw new UserDoesNotExistException();
        }

        updateUser.updateRole(role);
        userRepository.saveAndFlush(updateUser);
        log.info("user with name "+updateUser.getUsername() + " now has roles " + updateUser.getAuthorities());
        return updateUser;
    }

    @Override
    public AuthUser changePassword(String email, String newPassword) throws UserDoesNotExistException, SamePasswordException {
        log.info(() -> " User with username "+ email + " try to change password");
        AuthUser updateUser = userRepository.findByEmail(email);
        if(Objects.isNull(updateUser)){
            log.warn(() -> "IN UserService (changePassword) user with email" + email + " does not exist!");
            throw new UserDoesNotExistException();
        }
        if(passwordEncoder.encode(updateUser.getPassword()).equals(newPassword)){
            log.info(() -> "Cannot change password. Passwords are same");
            throw new SamePasswordException();
        }
        updateUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(updateUser);
        log.info(() -> " User " + email + " successfully change password");
        return updateUser;
    }

    @Override
    public AuthUser enableUser(String email) throws UserDoesNotExistException {
        AuthUser enableUser = userRepository.findByEmail(email);

        if(Objects.isNull(enableUser)){
            log.warn(() -> "IN UserService (enableUser) user with email" + email + " does not exist!");
            throw new UserDoesNotExistException();
        }

        enableUser.setEnabled(true);
        userRepository.saveAndFlush(enableUser);
        log.info(() -> "user with email " + enableUser.getEmail() + " now enabled!");
        return enableUser;
    }

    @Override
    public AuthUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
