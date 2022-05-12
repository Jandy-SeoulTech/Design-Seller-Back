package jandy3.DesignSeller.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    @Setter
    private String password;
    private String email;
    @Setter
    private String role;

    @CreationTimestamp
    private Timestamp createDate;

}
