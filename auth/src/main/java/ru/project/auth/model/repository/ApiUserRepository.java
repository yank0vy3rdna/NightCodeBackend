package ru.project.auth.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.auth.model.entities.ApiUser;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
    ApiUser findApiUserByEmail(String email);
}
