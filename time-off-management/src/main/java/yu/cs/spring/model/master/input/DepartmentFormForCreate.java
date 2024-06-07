package yu.cs.spring.model.master.input;

import jakarta.validation.constraints.NotBlank;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.validators.DepartmentCodeForUnique;

public record DepartmentFormForCreate(
		@DepartmentCodeForUnique(message = "Department code is already used.")
		String code,
		@NotBlank(message = "Please enter department name.")
		String name,
		String description
		) {

	public Department entity() {
		var entity = new Department();
		entity.setCode(code);
		entity.setName(name);
		entity.setDescription(description);
		return entity;
	}
}
