package lt.vu.eshop.log;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@LoggingInterceptorBinding
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext ctx) throws Exception {
        System.out.println("LoggingInterceptor: Before method invocation");

        try {
            return ctx.proceed();
        } finally {
            System.out.println("LoggingInterceptor: After method invocation");
        }
    }
}