package ru.project.study_platform.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.project.study_platform.model.dto.MessageDTO;
import ru.project.study_platform.model.entity.Message;
import ru.project.study_platform.service.entityServices.messageService.MessageService;

import java.util.List;

@Controller
public class MessageController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;

    public MessageController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.template = messagingTemplate;
        this.messageService = messageService;
    }
    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam Long groupId){
        return messageService.getMessagesByGroupId(groupId);
    }
    @MessageMapping("/messages")
    public void processMessage(@Payload MessageDTO message) {
        messageService.createMessage(message);
        template.convertAndSendToUser(message.getGroupId().toString(),"/messages", "new message lolb");
    }
}
