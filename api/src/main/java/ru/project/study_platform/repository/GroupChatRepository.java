package ru.project.study_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.study_platform.model.entity.GroupChat;

public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {
}
