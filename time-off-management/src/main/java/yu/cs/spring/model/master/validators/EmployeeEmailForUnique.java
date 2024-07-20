package yu.cs.spring.model.master.validators;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

@Documented
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Constraint(validatedBy = EmployeeEmailForUniqueValidator.class)
@NotBlank
public @interface EmployeeEmailForUnique {
	String message() default "Email has been taken.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
