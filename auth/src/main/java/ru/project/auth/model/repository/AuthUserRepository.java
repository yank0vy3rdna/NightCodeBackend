package ru.project.auth.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.project.auth.model.entities.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
  AuthUser findByEmail(@Param("email") String email);
}
