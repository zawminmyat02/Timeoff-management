package yu.cs.spring.model.master.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.master.EmployeeCodeGenerator;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk;
import yu.cs.spring.model.master.input.EmployeeFormForCreate;
import yu.cs.spring.model.master.repo.DepartmentRepo;
import yu.cs.spring.model.master.repo.EmployeeRepo;
import yu.cs.spring.model.master.repo.PositionRepo;

@Service
public class EmployeeService {

	 @Autowired
	 private EmployeeRepo employeeRepo;
	 
	 @Autowired
	 private EmployeeCodeGenerator codeGenerator;
	 
	 @Autowired
	 private PasswordEncoder encoder;
	 
	 @Autowired
	 private DepartmentRepo departmentRepo;
	 
	 @Autowired
	 private PositionRepo positionRepo;

	 public void saveEmployee(EmployeeFormForCreate form) {
			
		 var code = codeGenerator.next(form.department());
		 var password = encoder.encode("123456");
			
		 var entity = form.entity(code, password);
		 Optional<Department>  department = departmentRepo.findById(form.department());
		 
		 PositionPk positionPk = new PositionPk();
		 positionPk.setDepartmentCode(form.department());
		 positionPk.setPositionCode(form.position());
		 Optional<Position> position = positionRepo.findById(positionPk);

		 entity.setDepartment(department.get());
		 entity.setPosition(position.get());
		 employeeRepo.saveAndFlush(entity);
	 }

	 public List<Employee> findAll() {
	    return employeeRepo.findAll();
	 }
}
