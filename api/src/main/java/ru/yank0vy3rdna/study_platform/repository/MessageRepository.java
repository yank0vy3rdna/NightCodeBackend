package ru.yank0vy3rdna.study_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yank0vy3rdna.study_platform.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
