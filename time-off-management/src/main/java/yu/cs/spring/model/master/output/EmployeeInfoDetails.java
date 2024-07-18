package yu.cs.spring.model.master.output;

import java.time.LocalDate;
import java.util.List;

import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee.Gender;
import yu.cs.spring.model.master.entity.Employee.Status;
import yu.cs.spring.model.master.entity.PositionPk.PositionCode;


public record EmployeeInfoDetails(
		String code,
		String name,
		String loginId,
		String phone,
		String email,
		LocalDate dateOfBirth,
		Gender gender,
		String departmentCode,
		String department,
		PositionCode position,
		Status status,
		LocalDate assignAt,
		String remark
		) {
	
	public String getPositionName() {
		return position.getValue();
	}

	public static EmployeeInfoDetails from(Employee entity) {
		return new EmployeeInfoDetails(
			entity.getCode(), 
			entity.getAccount().getName(), 
			entity.getAccount().getUsername(), 
			entity.getPhone(), 
			entity.getEmail(), 
			entity.getDateOfBirth(), 
			entity.getGender(), 
			entity.getDepartment().getCode(),
			entity.getDepartment().getName(), 
			entity.getPosition().getId().getPositionCode(), 
			entity.getStatus(), 
			entity.getAssignDate(), 
			entity.getRemark());
	}
}
