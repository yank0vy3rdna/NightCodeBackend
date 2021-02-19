package ru.yank0vy3rdna.study_platform.service;

import ru.yank0vy3rdna.study_platform.model.GroupRoom;
import ru.yank0vy3rdna.study_platform.model.User;

import java.util.List;

public interface GroupRoomService {
    List<GroupRoom> findAllForUser(User user);
    List<GroupRoom> findAllForUserWithSearch(User user, String q);
    void save(GroupRoom room);
}
