package ru.yank0vy3rdna.study_platform.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "group_room")
@AllArgsConstructor
@NoArgsConstructor
public class GroupRoom extends IdBaseEntity{
    @OneToOne
    private GroupChat groupChat;

    @ManyToMany
    @JoinTable(
            name = "grouprooms_users",
            joinColumns = @JoinColumn(name = "grouproom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @Column(name = "hash_id")
    private String hashId;

    @Column(name = "name")
    private String name;

    @Column(name = "group_number")
    private String groupNumber;

}
