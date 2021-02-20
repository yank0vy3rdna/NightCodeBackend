package ru.project.study_platform.service.entityServices.groupRoomService;

import ru.project.study_platform.model.dto.GroupDTO;
import ru.project.study_platform.model.entity.GroupRoom;
import ru.project.study_platform.model.entity.User;

import java.util.List;

public interface GroupRoomService {
    List<GroupRoom> findAllForUser(User user);
    List<GroupRoom> findAllForUserWithSearch(User user, String q);
    GroupRoom addNewGroupRoom(GroupDTO groupDTO, User user);
    GroupRoom addNewMember(User user, String groupNameHash);
    GroupRoom getGroupRoom(String HashID);
}
