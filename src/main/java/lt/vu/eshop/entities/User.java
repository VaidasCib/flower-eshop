package lt.vu.eshop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(unique = true)
    private String username;

    @Basic(optional = false)
    private String password;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
