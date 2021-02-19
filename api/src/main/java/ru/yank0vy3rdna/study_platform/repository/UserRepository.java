package ru.yank0vy3rdna.study_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yank0vy3rdna.study_platform.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
