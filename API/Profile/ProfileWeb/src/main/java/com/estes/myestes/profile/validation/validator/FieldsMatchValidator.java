package com.estes.myestes.profile.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import com.estes.myestes.profile.validation.annotations.FieldsMatch;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

  private String field;
  private String fieldMatch;

  @Override
  public void initialize(FieldsMatch constraintAnnotation) {
      field = constraintAnnotation.field();
      fieldMatch = constraintAnnotation.fieldMatch();
  }
  
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
	  boolean valid = true;
	  try
      {
		  final Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
		  final Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
		  valid =  fieldValue == null && fieldMatchValue == null || fieldValue != null && fieldValue.equals(fieldMatchValue);
      }
      catch (final Exception ignore)
      {
          // ignore
      }
      return valid;
  }
}