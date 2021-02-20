package ru.project.auth.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.project.auth.model.entities.Confirmation;

import javax.xml.transform.sax.SAXTransformerFactory;


public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation getByAccountEmail(@Param("accountEmail") String email);
    Confirmation findByUrl(@Param("url") String url);
}
