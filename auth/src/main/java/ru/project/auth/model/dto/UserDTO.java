package ru.project.auth.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UserDTO {
    private String email;
    private String username;
    private String userlastname;
    private String password;
    private String vkId;
    private String faculty;
    private String groupNumber;

    public UserDTO(String username, String email){
        this.email = email;
        this.username = username;
    }
}
