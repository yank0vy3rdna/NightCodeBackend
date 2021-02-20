package ru.project.study_platform.service.entityServices.groupRoomService;

import ru.project.study_platform.model.dto.GroupDTO;
import ru.project.study_platform.model.dto.GroupResponseDTO;
import ru.project.study_platform.model.entity.GroupRoom;
import ru.project.study_platform.model.entity.User;
import ru.project.study_platform.service.entityServices.groupRoomService.excaptions.GroupRoomNotFoundExceptions;

import java.util.List;

public interface GroupRoomService {
    List<GroupResponseDTO> findAllForUser(User user);
    List<GroupResponseDTO> findAllForUserWithSearch(User user, String q);
    GroupRoom addNewGroupRoom(GroupDTO groupDTO, User user);
    GroupRoom addNewMember(User user, String groupNameHash) throws GroupRoomNotFoundExceptions;
    GroupRoom getGroupRoom(String HashID);
}
