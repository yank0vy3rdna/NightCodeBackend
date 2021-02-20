package ru.project.auth.model.entities;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "auth_users")
public final class AuthUser implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NonNull
    private String email;

    @NotNull
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ClientRole role;

    private boolean isEnabled;

    private boolean isAccountNonLocked;

    private boolean isAccountNonExpired;

    private boolean isCredentialsNonExpired;

    @Column(name = "auth_type")
    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @Column(name = "created_time", insertable = true, updatable = false)
    private LocalDateTime created;

    @Column(name = "modified_time",  insertable = true, updatable = true)
    private LocalDateTime modified;

    @Column(name = "vk_id")
    private String vkId;


    private String facutly;

    private String groupNumber;



    @PrePersist
    private void onCreate(){
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    private void onUpdate(){
        this.setModified(LocalDateTime.now());
    }

    public void updateRole (ClientRole role){
        this.role = role ;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

}
