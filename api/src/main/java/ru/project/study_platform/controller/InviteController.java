package ru.project.study_platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.project.study_platform.model.dto.UrlDTO;
import ru.project.study_platform.model.entity.GroupRoom;
import ru.project.study_platform.model.entity.User;
import ru.project.study_platform.repository.GroupRoomRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class InviteController {
    private final GroupRoomRepository groupRoomRepository;

    public InviteController(GroupRoomRepository groupRoomRepository) {
        this.groupRoomRepository = groupRoomRepository;
    }
    @GetMapping("/group/generate_invite")
    public UrlDTO generate(@RequestParam Long groupId, HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getAttribute("user");
        if (user.isTeacher()) {
            GroupRoom groupRoom = groupRoomRepository.getOne(groupId);
            return new UrlDTO("https://yank0vy3rdna.ru/study_platform/api/group/invite?hashId=" + groupRoom.getHashId());
        }else
            response.setStatus(409);
        return null;
    }

    @GetMapping("/group/invite")
    public void invite(@RequestParam String hashId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        GroupRoom groupRoom = groupRoomRepository.findGroupRoomByHashId(hashId);
        groupRoom.getUsers().add(user);
    }
}
