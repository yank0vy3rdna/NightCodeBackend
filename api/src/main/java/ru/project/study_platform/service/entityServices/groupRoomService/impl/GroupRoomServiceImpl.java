package ru.project.study_platform.service.entityServices.groupRoomService.impl;

import org.springframework.stereotype.Service;
import ru.project.study_platform.model.dto.GroupDTO;
import ru.project.study_platform.model.entity.GroupRoom;
import ru.project.study_platform.model.entity.User;
import ru.project.study_platform.repository.GroupRoomRepository;
import ru.project.study_platform.service.entityFactories.groupRoomFactories.GroupRoomFactory;
import ru.project.study_platform.service.entityServices.groupRoomService.GroupRoomService;
import ru.project.study_platform.service.entityServices.groupRoomService.excaptions.GroupRoomNotFoundExceptions;

import java.util.List;

@Service
public class GroupRoomServiceImpl implements GroupRoomService {
    private final GroupRoomRepository groupRoomRepository;
    private final GroupRoomFactory groupRoomFactory;

    public GroupRoomServiceImpl(GroupRoomRepository groupRoomRepository, GroupRoomFactory groupRoomFactory) {
        this.groupRoomRepository = groupRoomRepository;
        this.groupRoomFactory = groupRoomFactory;
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
        GroupRoom groupRoom = groupRoomFactory.createNewGroupRoom(groupDTO);
        groupRoom.getUsers().add(user);
        return groupRoomRepository.save(groupRoom);
    }

    @Override
    public GroupRoom addNewMember(User user,  String groupNameHash) throws GroupRoomNotFoundExceptions {
        GroupRoom groupRoom = groupRoomRepository.findGroupRoomByHashId(groupNameHash);
        if(groupRoom == null){
            throw new GroupRoomNotFoundExceptions();
        }
        groupRoom.getUsers().add(user);
        return groupRoomRepository.save(groupRoom);
    }

    @Override
    public GroupRoom getGroupRoom(String HashID) {
        return null;
    }

}
