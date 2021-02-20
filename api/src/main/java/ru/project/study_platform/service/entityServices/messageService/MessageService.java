package ru.project.study_platform.service.entityServices.messageService;

import ru.project.study_platform.model.dto.MessageDTO;
import ru.project.study_platform.model.entity.Message;

import java.util.List;

public interface MessageService {
    void createMessage(MessageDTO messageDTO);
    List<Message> getMessagesByGroupId(Long groupId);
}
