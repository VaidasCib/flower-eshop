package lt.vu.eshop.common;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

@Specializes
@ApplicationScoped
public class SpecializedWelcomeMessage extends DefaultWelcomeMessage {
    @Override
    public String getGreetingsMessage() {
        return "Logged in as: ";
    }
}
