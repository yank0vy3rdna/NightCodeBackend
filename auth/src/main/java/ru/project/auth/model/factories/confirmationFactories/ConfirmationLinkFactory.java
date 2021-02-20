package ru.project.auth.model.factories.confirmationFactories;

import ru.project.auth.model.entities.Confirmation;

public interface ConfirmationLinkFactory {
    Confirmation createConfirmation(String userEmail);
}
