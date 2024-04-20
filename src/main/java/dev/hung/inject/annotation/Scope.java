package dev.hung.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    String PROTOTYPE = "prototype";
    String SINGLETON = "singleton";

    String value() default PROTOTYPE;
}
