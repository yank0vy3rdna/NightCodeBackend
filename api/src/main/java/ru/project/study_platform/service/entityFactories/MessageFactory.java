package ru.project.study_platform.service.entityFactories;

import org.springframework.stereotype.Service;
import ru.project.study_platform.model.entity.GroupChat;
import ru.project.study_platform.model.entity.Message;
import ru.project.study_platform.model.entity.User;

@Service
public class MessageFactory {

    public Message createObject(String content, GroupChat groupChat, User sender){
        Message message = new Message();
        message.setContent(content);
        message.setGroupChat(groupChat);
        message.setSender(sender);
        return message;
    }
}
