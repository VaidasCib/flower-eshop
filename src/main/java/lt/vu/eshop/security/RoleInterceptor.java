package lt.vu.eshop.security;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@Role
public class RoleInterceptor implements Serializable {
    @Inject
    private UserStorage userStorage;

    @AroundInvoke
    public Object checkRole(InvocationContext context) throws Exception {
        Role role = context.getTarget().getClass().getAnnotation(Role.class);
        if (role == null) {
            role = context.getMethod().getAnnotation(Role.class);
        }
        if (role != null && !role.value().equals(userStorage.getUserRole())) {
            throw new IllegalStateException("User role is not " + role.value());
        }
        return context.proceed();
    }
}