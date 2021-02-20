package ru.project.auth.services.confirmationServices;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.project.auth.model.entities.Confirmation;
import ru.project.auth.model.entities.AuthUser;
import ru.project.auth.model.factories.confirmationFactories.ConfirmationLinkFactory;
import ru.project.auth.model.repository.ConfirmationRepository;
import ru.project.auth.model.repository.AuthUserRepository;
import ru.project.auth.services.confirmationServices.exceptions.ConfirmLinkExpireDateException;
import ru.project.auth.services.confirmationServices.exceptions.UnknownLinkException;
import ru.project.auth.utils.mails.MailSender;

import java.time.LocalDate;
import java.util.Objects;

@Log4j2
@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationLinkFactory linkFactory;
    private final ConfirmationRepository repository;
    private final AuthUserRepository userRepository;
    private final MailSender mailSender;

    @Autowired
    public ConfirmationServiceImpl(ConfirmationLinkFactory linkFactory, ConfirmationRepository repository, AuthUserRepository userRepository, MailSender mailSender) {
        this.linkFactory = linkFactory;
        this.repository = repository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public Confirmation createNewConfirmationLink(String email) {
        Confirmation confirmation = linkFactory.createConfirmation(email);
        repository.save(confirmation);
        log.info(() -> "new confirmation link create!");
        mailSender.sendActivateLink(email, "activation code", confirmation.getUrl());
        log.info(() -> "confirmation link send to email " + email + "!");
        return confirmation;
    }

    @Override
    public Boolean checkConfirmation(String url) throws UnknownLinkException, ConfirmLinkExpireDateException {
        Confirmation confirmation = repository.findByUrl(url);
        if (Objects.isNull(confirmation)){
            throw new UnknownLinkException();
        }

        if(confirmation.getExpireDate().isBefore(LocalDate.now())){
            throw new ConfirmLinkExpireDateException();
        }

        AuthUser user = userRepository.findByEmail(confirmation.getAccountEmail());
        user.setEnabled(true);
        userRepository.saveAndFlush(user);

        log.info(() -> "Account with email " + user.getEmail() + " activated!");
        return true;
    }
}
