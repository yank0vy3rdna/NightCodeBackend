package ru.project.study_platform.utils.parsers;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VkWallParser {

    private final String TEACHER_WALL_URL = "https://api.vk.com/method/wall.get?v=5.130&access_token=ced2144fced2144fced2144f92cea490cacced2ced2144faee2191d730796b0453b41d8&owner_id=";


    public String extractClassRecordFromWall(String teacherVkId){
        final RestTemplate restTemplate = new RestTemplate();
        final String stringPosts = restTemplate.getForObject(TEACHER_WALL_URL+teacherVkId, String.class);
        System.out.println(stringPosts);
        return stringPosts;
    }




}
