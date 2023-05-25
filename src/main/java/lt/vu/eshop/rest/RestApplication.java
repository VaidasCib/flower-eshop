package lt.vu.eshop.rest;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@ApplicationPath("/api")
public class RestApplication extends Application {
    public RestApplication(@Context ServletConfig servletConfig) {
        super();
    }
}