package ru.project.auth.services.userServices.impls;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.auth.model.dto.UserDTO;
import ru.project.auth.model.entities.ClientRole;
import ru.project.auth.model.entities.User;
import ru.project.auth.model.factories.userFactories.UserFactory;
import ru.project.auth.model.repository.UserRepository;
import ru.project.auth.services.userServices.UserService;
import ru.project.auth.services.userServices.exceptions.SamePasswordException;
import ru.project.auth.services.userServices.exceptions.UserAlreadyExistException;
import ru.project.auth.services.userServices.exceptions.UserDoesNotExistException;

import java.util.Objects;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, UserFactory userFactory) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    public User signUp(UserDTO userDTO) throws UserAlreadyExistException {
        log.info(() -> "IN UserService new user try to register!");

        User checkedUser = userRepository.findByEmail(userDTO.getEmail());

        if(Objects.nonNull(checkedUser)){
            log.warn(() -> "IN UserService (sighUp) user with email" + userDTO.getEmail() + " is already exist! registration was rejected!");
            throw new UserAlreadyExistException();
        }

        checkedUser = userFactory.createDefaultUser(userDTO, ClientRole.STUDENT);
        userRepository.saveAndFlush(checkedUser);
        log.info(() -> "new user successfully loaded in db!");
        return checkedUser;
    }

    @Override
    public User changeRole(String email, ClientRole role) throws UserDoesNotExistException {
        User updateUser = userRepository.findByEmail(email);
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
    public User changePassword(String email, String newPassword) throws UserDoesNotExistException, SamePasswordException {
        log.info(() -> " User with username "+ email + " try to change password");
        User updateUser = userRepository.findByEmail(email);
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
    public User enableUser(String email) throws UserDoesNotExistException {
        User enableUser = userRepository.findByEmail(email);

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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
