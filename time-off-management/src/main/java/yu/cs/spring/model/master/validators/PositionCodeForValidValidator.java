package yu.cs.spring.model.master.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import yu.cs.spring.model.master.entity.PositionPk;
import yu.cs.spring.model.master.repo.PositionRepo;

@Component
@Transactional(readOnly = true)
public class PositionCodeForValidValidator implements ConstraintValidator<PositionCodeForValid, String>{

	@Autowired
	private PositionRepo repo;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(StringUtils.hasLength(value)) {
			var id = PositionPk.parse(value);
			return repo.countById(id) == 1;
		}
		
		return false;
	}

}
