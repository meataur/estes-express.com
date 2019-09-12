package com.estes.myestes.profile.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import com.estes.myestes.profile.validation.annotations.ValidSearchTerm;

public class SearchTermValidator implements ConstraintValidator<ValidSearchTerm, Object>{
	
	private String[] fields;
	
	@Override
	public void initialize(ValidSearchTerm constraintAnnotation) {
		fields = constraintAnnotation.fields();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = false;
		
		  
		  try
	      {
			  for(String field : fields){
				  valid = valid || (new BeanWrapperImpl(value).getPropertyValue(field)!=null);
			  }
	      }
	      catch (final Exception ignore)
	      {
	          // ignore
	      }
	      return valid;
	}

}
