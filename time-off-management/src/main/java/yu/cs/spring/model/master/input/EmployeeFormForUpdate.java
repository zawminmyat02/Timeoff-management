package yu.cs.spring.model.master.input;

import jakarta.validation.constraints.NotBlank;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee.Status;
import yu.cs.spring.model.master.validators.EmployeeEmailForUnique;

public record EmployeeFormForUpdate(
		String code,
		@NotBlank(message = "Please enter employee name.")
		String name,
		@NotBlank(message = "Please enter phone.")
		String phone,
		Status status,
		String remark) {

	public static EmployeeFormForUpdate from(Employee entity) {
		return new EmployeeFormForUpdate(
				entity.getCode(),
				entity.getAccount().getName(), 
				entity.getPhone(), 
				entity.getStatus(),
				entity.getRemark());
	}
}

