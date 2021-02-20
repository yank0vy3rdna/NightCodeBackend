package ru.yank0vy3rdna.study_platform.controller;

import org.springframework.web.bind.annotation.*;
import ru.yank0vy3rdna.study_platform.model.entity.GroupRoom;
import ru.yank0vy3rdna.study_platform.model.entity.User;
import ru.yank0vy3rdna.study_platform.model.dto.GroupDTO;
import ru.yank0vy3rdna.study_platform.model.dto.UrlDTO;
import ru.yank0vy3rdna.study_platform.service.groupChatService.GroupRoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/group/")
public class Group {
    private final GroupRoomService groupRoomService;

    public Group(GroupRoomService groupRoomService) {
        this.groupRoomService = groupRoomService;
    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<GroupRoom> getGroupRooms(HttpServletRequest request, @RequestParam(required = false) String q) {
        User user = (User) request.getAttribute("user");
        if (q != null) {
            return groupRoomService.findAllForUserWithSearch(user, q);
        }
        return groupRoomService.findAllForUser(user);
    }

    @PutMapping(path = "/new", consumes = "application/json", produces = "application/json")
    public UrlDTO createGroup(HttpServletRequest request, @RequestBody GroupDTO groupDTO, HttpServletResponse response) {
        User user = (User) request.getAttribute("user");
        if (!user.isTeacher()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        }
//        groupRoom.getUsers().add(user);
//        groupRoomService.save(groupRoom);
        return new UrlDTO("url");
    }
}
