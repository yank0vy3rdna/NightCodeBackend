package ru.yank0vy3rdna.study_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yank0vy3rdna.study_platform.model.entity.GroupChat;

public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {
}
