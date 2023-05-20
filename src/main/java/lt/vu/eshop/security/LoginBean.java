package lt.vu.eshop.security;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.entities.User;
import lt.vu.eshop.repositories.impl.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
@RequestScoped
public class LoginBean {
    @Inject
    private UserRepository userRepository;

    @Inject
    private UserStorage userStorage;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Transactional
    public String login() {
        User user = userRepository.findByUsername(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            userStorage.setUser(user);
            return "index.xhtml?faces-redirect=true";
        } else {
            FacesMessage message = new FacesMessage("Invalid username/password");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
    }

    public String logout() {
        userStorage.setUser(null);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
}
