package lt.vu.eshop.security;

import lt.vu.eshop.entities.UserRole;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Role {
    UserRole value() default UserRole.PUBLIC;
}