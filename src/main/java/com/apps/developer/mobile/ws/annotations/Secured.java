package com.apps.developer.mobile.ws.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


import javax.ws.rs.NameBinding;

@NameBinding
@Retention(RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Secured {

}
