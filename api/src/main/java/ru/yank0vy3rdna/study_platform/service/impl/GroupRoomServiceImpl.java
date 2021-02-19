package ru.yank0vy3rdna.study_platform.service.impl;

import org.springframework.stereotype.Service;
import ru.yank0vy3rdna.study_platform.model.GroupRoom;
import ru.yank0vy3rdna.study_platform.model.User;
import ru.yank0vy3rdna.study_platform.repository.GroupRoomRepository;
import ru.yank0vy3rdna.study_platform.service.GroupRoomService;

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
    public void save(GroupRoom room) {
        groupRoomRepository.save(room);
    }
}
