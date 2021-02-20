package ru.project.study_platform.service.entityFactories.groupRoomFactories;

import org.springframework.stereotype.Service;
import ru.project.study_platform.model.dto.GroupDTO;
import ru.project.study_platform.model.entity.GroupChat;
import ru.project.study_platform.model.entity.GroupRoom;

import java.util.Base64;

@Service
public class GroupRoomFactoryImpl implements GroupRoomFactory {
    @Override
    public GroupRoom createNewGroupRoom(GroupDTO groupDTO) {
        GroupRoom groupRoom = new GroupRoom();
        GroupChat groupChat = new GroupChat();
        groupRoom.setGroupNumber(groupDTO.getGroupNumber());
        groupRoom.setName(groupDTO.getName());
        groupRoom.setHashId(String.valueOf(groupDTO.getName().hashCode()));
        groupRoom.setGroupChat(groupChat);
        groupChat.setGroupRoom(groupRoom);
        return groupRoom;
    }
}
