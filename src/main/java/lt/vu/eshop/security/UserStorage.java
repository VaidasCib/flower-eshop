package lt.vu.eshop.security;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.entities.User;
import lt.vu.eshop.entities.UserRole;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserStorage implements Serializable {
    @Getter
    @Setter
    private User user;

    public UserRole getUserRole() {
        return user == null ? UserRole.PUBLIC : user.getRole();
    }
}
