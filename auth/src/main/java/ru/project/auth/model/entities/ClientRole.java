package ru.project.auth.model.entities;

import org.springframework.security.core.GrantedAuthority;

public enum ClientRole implements GrantedAuthority {
    STUDENT,
    TEACHER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
