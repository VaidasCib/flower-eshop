package lt.vu.eshop.common;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class HeaderBean {

    @Inject
    private IWelcomeMessage welcomeMessage;

    public String getGreetingsMessage() {
        return welcomeMessage.getGreetingsMessage();
    }
}
