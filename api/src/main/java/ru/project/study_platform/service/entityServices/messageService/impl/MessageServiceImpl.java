package ru.project.study_platform.service.entityServices.messageService.impl;

import org.springframework.stereotype.Service;
import ru.project.study_platform.model.dto.MessageDTO;
import ru.project.study_platform.service.entityFactories.MessageFactory;
import ru.project.study_platform.service.entityServices.messageService.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageFactory messageFactory;

    public MessageServiceImpl(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public void createMessage(MessageDTO messageDTO) {
        messageFactory.createObject(messageDTO.)
    }
}
