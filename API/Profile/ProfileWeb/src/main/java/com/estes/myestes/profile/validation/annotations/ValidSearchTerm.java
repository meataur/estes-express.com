package com.estes.myestes.profile.validation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.estes.myestes.profile.validation.validator.SearchTermValidator;

@Documented
@Constraint(validatedBy = SearchTermValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSearchTerm {

	String message() default "Invalid Search";
	String[] fields();
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}
