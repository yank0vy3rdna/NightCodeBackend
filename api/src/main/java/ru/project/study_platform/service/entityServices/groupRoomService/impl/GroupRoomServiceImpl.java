package ru.project.study_platform.service.entityServices.groupRoomService.impl;

import org.springframework.stereotype.Service;
import ru.project.study_platform.model.dto.GroupDTO;
import ru.project.study_platform.model.entity.GroupRoom;
import ru.project.study_platform.model.entity.User;
import ru.project.study_platform.repository.GroupRoomRepository;
import ru.project.study_platform.service.entityServices.groupRoomService.GroupRoomService;

import java.util.List;

@Service
public class GroupRoomServiceImpl implements GroupRoomService {
    private final GroupRoomRepository groupRoomRepository;

    public GroupRoomServiceImpl(GroupRoomRepository groupRoomRepository) {
        this.groupRoomRepository = groupRoomRepository;
    }

    @Override
    public List<GroupRoom> findAllForUser(User user) {
        return groupRoomRepository.findAllByUsersContains(user);
    }

    @Override
    public List<GroupRoom> findAllForUserWithSearch(User user, String q) {
        return groupRoomRepository.findAllByUsersContainsAndNameContains(user, q);
    }

    @Override
    public GroupRoom addNewGroupRoom(GroupDTO groupDTO, User user) {
        return null;
    }

    @Override
    public GroupRoom addNewMember(User user) {
        return null;
    }

    @Override
    public GroupRoom getGroupRoom(String HashID) {
        return null;
    }

}
