package ru.project.auth.model.factories.confirmationFactories;

import org.springframework.stereotype.Component;
import ru.project.auth.model.entities.Confirmation;

import java.time.LocalDate;
import java.util.Base64;

@Component
public class ConfirmationLinkFactoryImpl implements ConfirmationLinkFactory {

    @Override
    public Confirmation createConfirmation(String userEmail) {
        Confirmation confirmation = new Confirmation();
        confirmation.setAccountEmail(userEmail);
        confirmation.setUrl(String.valueOf(userEmail.hashCode()));
        confirmation.setExpireDate(LocalDate.now().plusDays(1));
        return confirmation;
    }
}
