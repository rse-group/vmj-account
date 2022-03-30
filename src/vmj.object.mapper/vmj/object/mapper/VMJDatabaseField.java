package vmj.object.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VMJDatabaseField {
    public String foreignTableName() default "null";
    public String foreignColumnName() default "null";
    public boolean primaryKey() default false;
    public boolean isDelta() default false;
}