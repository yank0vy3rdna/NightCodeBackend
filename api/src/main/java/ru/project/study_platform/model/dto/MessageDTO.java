package ru.project.study_platform.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    String content;
    Long senderId;
    Long groupId;
}
