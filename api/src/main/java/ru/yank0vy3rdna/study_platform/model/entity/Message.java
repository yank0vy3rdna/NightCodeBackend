package ru.yank0vy3rdna.study_platform.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Message extends IdBaseEntity{
    @OneToOne(targetEntity = User.class)
    private User sender;

    @ManyToOne(targetEntity = GroupChat.class)
    private GroupChat groupChat;

    @Column(name = "content")
    private String content;

    @Column(name = "datetime")
    private Date datetime = new Date();
}
