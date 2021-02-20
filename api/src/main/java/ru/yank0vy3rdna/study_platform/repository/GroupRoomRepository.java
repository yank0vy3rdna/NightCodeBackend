package ru.yank0vy3rdna.study_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yank0vy3rdna.study_platform.model.entity.GroupRoom;
import ru.yank0vy3rdna.study_platform.model.entity.User;

import java.util.List;

@Repository
public interface GroupRoomRepository extends JpaRepository<GroupRoom, Long> {
    List<GroupRoom> findAllByUsersContains(User user);
    List<GroupRoom> findAllByUsersContainsAndNameContains(User user, String query);
}
