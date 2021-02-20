package ru.project.study_platform.service.entityServices.messageService.impl;

import org.springframework.stereotype.Service;
import ru.project.study_platform.model.dto.MessageDTO;
import ru.project.study_platform.model.entity.Message;
import ru.project.study_platform.repository.GroupRoomRepository;
import ru.project.study_platform.repository.MessageRepository;
import ru.project.study_platform.repository.UserRepository;
import ru.project.study_platform.service.entityFactories.MessageFactory;
import ru.project.study_platform.service.entityServices.messageService.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageFactory messageFactory;
    private final GroupRoomRepository groupRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageFactory messageFactory, GroupRoomRepository groupRoomRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.messageFactory = messageFactory;
        this.groupRoomRepository = groupRoomRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void createMessage(MessageDTO messageDTO) {
        Message message = messageFactory.createObject(
                messageDTO.getContent(),
                groupRoomRepository.getOne(messageDTO.getGroupId()).getGroupChat(),
                userRepository.getOne(messageDTO.getSenderId())
        );
        messageRepository.save(message);
    }
}
