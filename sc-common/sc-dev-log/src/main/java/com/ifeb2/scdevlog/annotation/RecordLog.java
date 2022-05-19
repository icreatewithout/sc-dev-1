package com.ifeb2.scdevlog.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordLog {

    String value() default "";

    String bizName() default "";

}
