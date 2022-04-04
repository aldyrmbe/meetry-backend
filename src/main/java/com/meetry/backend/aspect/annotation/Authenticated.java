package com.meetry.backend.aspect.annotation;

import com.meetry.backend.entity.constant.Role;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Authenticated {
    Role value() default Role.ALL;
}