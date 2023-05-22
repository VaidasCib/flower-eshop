package lt.vu.eshop.common;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
public class AlternativeWelcomeMessage implements IWelcomeMessage {
    @Override
    public String getGreetingsMessage() {
        return "Logged in as: ";
    }
}
