package com.nabiji.ecommerce.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation, when placed on a method, will trigger an AOP aspect
 * to log the execution time of that method.
 */
@Target(ElementType.METHOD) // Can only be used on methods
@Retention(RetentionPolicy.RUNTIME) // Available at runtime for the aspect to find
public @interface LogExecutionTime {
}