package ru.yank0vy3rdna.study_platform.service.groupChatService;

import ru.yank0vy3rdna.study_platform.model.dto.GroupDTO;
import ru.yank0vy3rdna.study_platform.model.entity.GroupRoom;
import ru.yank0vy3rdna.study_platform.model.entity.User;

import java.util.List;

public interface GroupRoomService {
    List<GroupRoom> findAllForUser(User user);
    List<GroupRoom> findAllForUserWithSearch(User user, String q);
    GroupRoom addNewGroupRoom(GroupDTO groupDTO, User user);
    GroupRoom addNewMember(User user);
    GroupRoom getGroupRoom(String HashID);
}
