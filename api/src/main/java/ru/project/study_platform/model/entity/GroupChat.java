package ru.project.study_platform.model.entity;

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
    @OneToMany(mappedBy="groupChat", fetch = FetchType.LAZY)
    @OrderBy("datetime")
    private List<Message> messages;

    @OneToOne
    private GroupRoom groupRoom;
}
