package lt.vu.eshop.common;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultWelcomeMessage implements IWelcomeMessage {
    @Override
    public String getGreetingsMessage() {
        return "Welcome,";
    }
}
