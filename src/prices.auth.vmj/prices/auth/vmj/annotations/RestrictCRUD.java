package prices.auth.vmj.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import prices.auth.vmj.enums.CRUDMethod;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestrictCRUD {
    public String permissionName();
    public CRUDMethod[] allowedMethods() default {
        CRUDMethod.GET, CRUDMethod.LIST, CRUDMethod.POST,
        CRUDMethod.PUT, CRUDMethod.DELETE};
    public String customPermissionMethod() default "";
}
