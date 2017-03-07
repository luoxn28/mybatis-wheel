package com.intrack.type;

import java.lang.annotation.*;

/**
 * @author intrack
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappedJdbcTypes {
    JdbcType[] value();
    boolean includeNullJdbcType() default false;
}
