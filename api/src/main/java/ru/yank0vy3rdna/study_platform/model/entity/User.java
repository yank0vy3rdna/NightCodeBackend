package ru.yank0vy3rdna.study_platform.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

enum Type {
    STUDENT, TEACHER;
}

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends IdBaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "group_number", nullable = false)
    private String groupNumber;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "vk_id")
    private String vkId;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private Type userType;

    @ManyToMany
    @JoinTable(
            name = "grouprooms_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "grouproom_id"))
    private Set<GroupRoom> groupRooms;

    public boolean isTeacher(){
        return this.getUserType() == Type.TEACHER;
    }
}
