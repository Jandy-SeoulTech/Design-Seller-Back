package jandy3.DesignSeller.domain;

import jandy3.DesignSeller.domain.embed.Address;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
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
    @UpdateTimestamp
    private Timestamp updateDate;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Request> requests = new ArrayList<>();
}
