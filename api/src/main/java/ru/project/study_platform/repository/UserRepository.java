package ru.project.study_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.study_platform.model.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
