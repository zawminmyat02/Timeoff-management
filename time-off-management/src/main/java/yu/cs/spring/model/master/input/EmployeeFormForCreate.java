package yu.cs.spring.model.master.input;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import yu.cs.spring.model.master.entity.Account;
import yu.cs.spring.model.master.entity.Account.Role;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee.Gender;
import yu.cs.spring.model.master.entity.Employee.Status;
import yu.cs.spring.model.master.entity.PositionPk.PositionCode;
import yu.cs.spring.model.master.validators.DepartmentCodeForValid;


public record EmployeeFormForCreate(
		@NotBlank(message = "Please enter employee name.")
		String name,
		@DepartmentCodeForValid
		String department,
		@NotNull(message = "Please select position.")
		PositionCode position,
		@NotBlank(message = "Please enter phone.")
		String phone,
		@NotBlank(message = "Please enter email.")
		String email,
		@NotNull(message = "Please select gender.")
		Gender gender,
		@NotNull(message = "Please enter date of birth.")
		LocalDate dob,
		@NotNull(message = "Please enter assign date.")
		LocalDate assignDate,
		@NotNull(message = "Please select status.")
		Status status,
		String remark
		) {

	
	public Employee entity(String code, String password) {
		var employee = new Employee();
		employee.setCode(code);
		
		var account = new Account();
		account.setUsername(code);
		account.setRole(Role.Employee);
		account.setPassword(password);
		account.setName(name);
		
		employee.setAccount(account);
		employee.setPhone(phone);
		employee.setEmail(email);
		employee.setGender(gender);
		employee.setDateOfBirth(dob);
		employee.setStatus(status);
		employee.setAssignDate(assignDate);
		
		if(status == Status.Permanent) {
			employee.setProbationPassDate(assignDate);
		}
		
		employee.setRemark(remark);
		
		return employee;
	}
}
