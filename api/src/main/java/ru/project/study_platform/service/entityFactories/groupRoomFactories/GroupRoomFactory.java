package ru.project.study_platform.service.entityFactories.groupRoomFactories;

import org.springframework.stereotype.Service;
import ru.project.study_platform.model.dto.GroupDTO;
import ru.project.study_platform.model.entity.GroupRoom;


public interface GroupRoomFactory {
    GroupRoom createNewGroupRoom(GroupDTO groupDTO);
}
