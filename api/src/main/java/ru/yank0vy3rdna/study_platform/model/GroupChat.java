package ru.yank0vy3rdna.study_platform.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "group_chat")
@AllArgsConstructor
@NoArgsConstructor
public class GroupChat extends IdBaseEntity{
    @OneToMany(mappedBy="groupChat")
    @OrderBy("datetime")
    private List<Message> messages;

    @OneToOne
    private GroupRoom groupRoom;
}
