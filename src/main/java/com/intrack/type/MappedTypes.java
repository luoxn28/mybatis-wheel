package com.intrack.type;

import java.lang.annotation.*;

/**
 * @author intrack
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappedTypes {
    Class<?>[] value();
}
