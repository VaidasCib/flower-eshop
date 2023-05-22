package lt.vu.eshop.common;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

//@Specializes
//@ApplicationScoped
public class TranslatedWelcomeMessage extends DefaultWelcomeMessage {
    @Override
    public String getGreetingsMessage() {
        return "Sveiki,";
    }
}
