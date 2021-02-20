package ru.project.study_platform.service.entityFactories;

import org.springframework.stereotype.Service;
import ru.project.study_platform.model.dto.GroupDTO;
import ru.project.study_platform.model.dto.GroupResponseDTO;
import ru.project.study_platform.model.entity.GroupRoom;

@Service
public class GroupResponseFactory {
    public GroupResponseDTO createObject(GroupRoom room) {
        GroupResponseDTO dto = new GroupResponseDTO();
        dto.setGroupNumber(room.getGroupNumber());
        dto.setName(room.getName());
        dto.setId(room.getId());
        return dto;
    }
}
