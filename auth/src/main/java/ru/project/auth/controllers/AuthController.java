package ru.project.auth.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.project.auth.model.dto.UserDTO;
import ru.project.auth.model.entities.AuthUser;
import ru.project.auth.services.confirmationServices.ConfirmationService;
import ru.project.auth.services.confirmationServices.exceptions.ConfirmLinkExpireDateException;
import ru.project.auth.services.confirmationServices.exceptions.UnknownLinkException;
import ru.project.auth.services.userServices.AuthUserService;
import ru.project.auth.services.userServices.exceptions.UserAlreadyExistException;
import ru.project.auth.utils.tokens.JwtUtil;

import javax.servlet.http.HttpServletResponse;

@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserService userService;
    private final ConfirmationService confirmationService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthUserService userService, ConfirmationService confirmationService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.confirmationService = confirmationService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @RequestMapping(path = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<String> register(@ModelAttribute UserDTO userDTO) {
        log.info(() -> "new user try to register. User name: " + userDTO.getEmail());
        try {
            userService.signUp(userDTO);
            confirmationService.createNewConfirmationLink(userDTO.getEmail());
            return ResponseEntity.status(200).body("The link send to email");
        } catch (UserAlreadyExistException e){
            return ResponseEntity.status(409).body("The user already registered!");
        } catch (Exception exception){
            log.warn(exception::toString);
            return ResponseEntity.status(500).body("unknown server exception");
        }

    }

    @RequestMapping(path = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<String> createToken(@ModelAttribute UserDTO userDTO)  {
        log.info(() -> "start authenticate user with email "+ userDTO.getEmail());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
            final AuthUser user = userService.findByEmail(userDTO.getEmail());
            return ResponseEntity.status(200).body(jwtUtil.generateToken(user));
        } catch (BadCredentialsException e){
            log.warn(e.getMessage());
            return ResponseEntity.status(403).body("Bad credentials");
        } catch (Exception exception){
            log.warn(exception::toString);
            return ResponseEntity.status(500).body("unknown server exception! Exception info: " + exception.toString());
        }

    }

    @RequestMapping("/confirm/{id}")
    public ResponseEntity<String> activateAccount(@PathVariable String id, HttpServletResponse httpResponse) {
        try {
            log.info(() -> "got new confirmation id: "+ id);
            confirmationService.checkConfirmation(id);
            httpResponse.sendRedirect("/"); // for debugging
            return ResponseEntity.status(200).body("account activate!");
        } catch (UnknownLinkException e) {
            log.warn(() -> "This link is not valid");
            return ResponseEntity.status(411).body("Invalid link");
        } catch (ConfirmLinkExpireDateException e){
            log.warn(() -> "Link date is expired!");
            return ResponseEntity.status(410).body("Link date is expired");
        } catch (Exception exception) {
            log.warn(() -> "/confirm/ throws exception: " + exception.toString());
            return ResponseEntity.status(500).body("unknown server exception! Exception info: " + exception.toString());
        }
    }
}
