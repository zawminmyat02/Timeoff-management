package yu.cs.spring.model.master.validators;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import yu.cs.spring.model.master.repo.EmployeeRepo;

public class EmployeeEmailForUniqueValidator implements ConstraintValidator<EmployeeEmailForUnique, String>{

	@Autowired
	private EmployeeRepo repo;

    @Override
    public void initialize(EmployeeEmailForUnique constraintAnnotation) {
        // Optional initialization, if needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values should be validated separately if required
        }
        
        // Perform the check if the code already exists in the database
        boolean isUnique = repo.countByEmail(value) == 0;
        
        // Return true if the code is unique (valid), false otherwise
        return isUnique;
    }
}
