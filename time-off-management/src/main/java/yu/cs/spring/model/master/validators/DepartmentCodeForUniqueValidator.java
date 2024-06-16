package yu.cs.spring.model.master.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import yu.cs.spring.model.master.repo.DepartmentRepo;

@Component
public class DepartmentCodeForUniqueValidator implements ConstraintValidator<DepartmentCodeForUnique, String>{

	@Autowired
	private DepartmentRepo repo;

    @Override
    public void initialize(DepartmentCodeForUnique constraintAnnotation) {
        // Optional initialization, if needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values should be validated separately if required
        }
        
        // Perform the check if the code already exists in the database
        boolean isUnique = repo.countByCode(value) == 0;
        
        // Return true if the code is unique (valid), false otherwise
        return isUnique;
    }
}
