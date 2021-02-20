package ru.project.auth.services.userServices;


import ru.project.auth.model.dto.UserDTO;
import ru.project.auth.model.entities.ClientRole;
import ru.project.auth.model.entities.User;
import ru.project.auth.services.userServices.exceptions.SamePasswordException;
import ru.project.auth.services.userServices.exceptions.UserAlreadyExistException;
import ru.project.auth.services.userServices.exceptions.UserDoesNotExistException;

public interface UserService {
  User signUp(UserDTO userDTO) throws UserAlreadyExistException;

  User changeRole(String email, ClientRole role) throws UserDoesNotExistException;

  User changePassword(String email, String newPassword) throws UserDoesNotExistException, SamePasswordException;

  User enableUser(String email) throws UserDoesNotExistException;

  User findByEmail(String email);
}
