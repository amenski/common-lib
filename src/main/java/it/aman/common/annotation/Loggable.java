package it.aman.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import it.aman.common.util.ERPConstants;

/**
 * In cooperation with an aspect, this is a marker to log method inputs
 * 
 * @author Amanuel
 *
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
	String format() default ERPConstants.PARAMETER_2;
	String[] exclusions() default "";
}
