package ru.project.study_platform.service.entityServices.messageService;

import ru.project.study_platform.model.dto.MessageDTO;

public interface MessageService {
    void createMessage(MessageDTO messageDTO);
}
