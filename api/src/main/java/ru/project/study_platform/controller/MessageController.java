package ru.project.study_platform.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.project.study_platform.model.dto.MessageDTO;
import ru.project.study_platform.model.entity.GroupRoom;
import ru.project.study_platform.model.entity.Message;
import ru.project.study_platform.model.entity.User;
import ru.project.study_platform.repository.GroupRoomRepository;
import ru.project.study_platform.service.entityServices.messageService.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MessageController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    private final GroupRoomRepository groupRoomRepository;

    public MessageController(SimpMessagingTemplate messagingTemplate, MessageService messageService, GroupRoomRepository groupRoomRepository) {
        this.template = messagingTemplate;
        this.messageService = messageService;
        this.groupRoomRepository = groupRoomRepository;
    }

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam Long groupId, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getAttribute("user");
        GroupRoom groupRoom = groupRoomRepository.getOne(groupId);
        if (groupRoom.getUsers().contains(user))
            return messageService.getMessagesByGroupId(groupId);
        else {
            response.setStatus(409);
            return null;
        }
    }

    @MessageMapping("/messages")
    public void processMessage(@Payload MessageDTO message, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getAttribute("user");
        GroupRoom groupRoom = groupRoomRepository.getOne(message.getGroupId());
        if (groupRoom.getUsers().contains(user)) {
            messageService.createMessage(message);
            template.convertAndSendToUser(message.getGroupId().toString(), "/messages", "new message lolb");
        } else {
            response.setStatus(409);
        }
    }
}
