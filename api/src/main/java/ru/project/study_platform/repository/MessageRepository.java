package ru.project.study_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.study_platform.model.entity.GroupChat;
import ru.project.study_platform.model.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByGroupChat(GroupChat groupChat);
}
