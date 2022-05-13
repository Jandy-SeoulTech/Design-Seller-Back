package jandy3.DesignSeller.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Data
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    @Setter
    private String password;
    private String email;
    private String nickname;
    @Setter
    private String role;
    private String provider;
    private String providerId;
    private String profileImage;
    @CreationTimestamp
    private Timestamp createDate;

}
