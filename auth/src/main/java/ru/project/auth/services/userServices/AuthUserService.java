package ru.project.auth.services.userServices;


import ru.project.auth.model.dto.UserDTO;
import ru.project.auth.model.entities.ClientRole;
import ru.project.auth.model.entities.AuthUser;
import ru.project.auth.services.userServices.exceptions.SamePasswordException;
import ru.project.auth.services.userServices.exceptions.UserAlreadyExistException;
import ru.project.auth.services.userServices.exceptions.UserDoesNotExistException;

public interface AuthUserService {
  AuthUser signUp(UserDTO userDTO) throws UserAlreadyExistException;

  AuthUser changeRole(String email, ClientRole role) throws UserDoesNotExistException;

  AuthUser changePassword(String email, String newPassword) throws UserDoesNotExistException, SamePasswordException;

  AuthUser enableUser(String email) throws UserDoesNotExistException;

  AuthUser findByEmail(String email);
}
