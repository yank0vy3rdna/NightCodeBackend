package ru.project.study_platform.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.project.study_platform.model.dto.MessageDTO;

@Controller
public class MessageController {
    private final SimpMessagingTemplate template;

    public MessageController(SimpMessagingTemplate messagingTemplate) {
        this.template = messagingTemplate;
    }

    @MessageMapping("/message")
    public void processMessage(@Payload MessageDTO message){

    }
}
