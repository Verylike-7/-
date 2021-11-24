package com.hema.hmfresh.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModelProp {
    String name();

    int colIndex() default -1;

    boolean nullable() default true;

    String interfaceXmlName() default "";
}