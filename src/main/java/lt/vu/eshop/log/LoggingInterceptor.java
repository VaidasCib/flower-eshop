package lt.vu.eshop.log;

import lt.vu.eshop.security.UserStorage;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@LoggingInterceptorBinding
public class LoggingInterceptor implements Serializable {

    @Inject
    private UserStorage userStorage;

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext ctx) throws Exception {
        try {
            System.out.println("LoggingInterceptor: Before method invocation");
            if (userStorage.getUser() == null) {
                System.out.println("No user logged in");
            } else {
                System.out.println("Logged in user: " + userStorage.getUser().getUsername());
            }
            System.out.println("Current user role: " + userStorage.getUserRole());
            System.out.println("Method called: " + ctx.getMethod().getName());
            return ctx.proceed();
        } finally {
            System.out.println("LoggingInterceptor: After method invocation");
        }
    }
}