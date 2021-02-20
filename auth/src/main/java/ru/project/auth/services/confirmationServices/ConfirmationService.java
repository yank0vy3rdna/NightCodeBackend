package ru.project.auth.services.confirmationServices;

import ru.project.auth.model.entities.Confirmation;
import ru.project.auth.services.confirmationServices.exceptions.ConfirmLinkExpireDateException;
import ru.project.auth.services.confirmationServices.exceptions.UnknownLinkException;

public interface ConfirmationService {
    Confirmation createNewConfirmationLink(String username);
    Boolean checkConfirmation (String url) throws UnknownLinkException, ConfirmLinkExpireDateException;
}

