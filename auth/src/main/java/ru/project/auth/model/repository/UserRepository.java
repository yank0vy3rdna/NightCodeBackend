package ru.project.auth.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.project.auth.model.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(@Param("email") String email);
}
