package yu.cs.spring.model.master.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import yu.cs.spring.model.master.EmployeeCodeGenerator;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee.Gender;
import yu.cs.spring.model.master.entity.EmployeeCodeSeq;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk;
import yu.cs.spring.model.master.input.EmployeeFormForCreate;
import yu.cs.spring.model.master.input.EmployeeFormForUpdate;
import yu.cs.spring.model.master.repo.DepartmentRepo;
import yu.cs.spring.model.master.repo.EmployeeCodeSeqRepo;
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

	@Autowired
	private EmployeeCodeSeqRepo employeeCodeSeqRepository;


	public void saveEmployee(EmployeeFormForCreate form) {

		String departmentCode = form.department();

		// Fetch or create the sequence for the department
		EmployeeCodeSeq seq = employeeCodeSeqRepository.findByDepartment(departmentCode).orElseGet(() -> {
			EmployeeCodeSeq newSeq = new EmployeeCodeSeq();
			newSeq.setDepartment(departmentCode);
			return newSeq;
		});

		// Generate the next code
		var code = codeGenerator.next(form.department());
		var password = encoder.encode("123456");

		var entity = form.entity(code, password);
		Optional<Department> department = departmentRepo.findById(form.department());

		PositionPk positionPk = new PositionPk();
		positionPk.setDepartmentCode(form.department());
		PositionPk.PositionCode positionCode = PositionPk.fromString(form.positionCode());

		positionPk.setPositionCode(positionCode);
		Optional<Position> position = positionRepo.findById(positionPk);

		entity.setDepartment(department.get());
		entity.setPosition(position.get());
		
		entity.setSickLeaves(position.get().getSickLeaves());
		entity.setCasualLeaves(position.get().getCasualLeaves());
		
		if(entity.getGender() == Gender.Male) {
			entity.setMaternityLeaves(60);

		}
		else {
			entity.setMaternityLeaves(180);

		}
		
		entity.setMonthlySalaries(position.get().getBasicSalary());

		employeeRepo.saveAndFlush(entity);
	}

	public List<Employee> findAll() {
		return employeeRepo.findAll();
	}

	public Employee getEmployeeByUsername(String username) {
		return employeeRepo.findByAccountUsername(username);
	}

	public void deleteById(String id) {
		employeeRepo.deleteById(id);
	}

	public Employee findById(String id) {
		return employeeRepo.findByCode(id);
	}

	public void updateEmployee(String id, @Valid EmployeeFormForUpdate employeeForm) {
		Employee existingEmployee = employeeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		existingEmployee.getAccount().setName(employeeForm.name());
		existingEmployee.setPhone(employeeForm.phone());
		existingEmployee.setStatus(employeeForm.status());
		existingEmployee.setRemark(employeeForm.remark());

		employeeRepo.save(existingEmployee);
	}

	public List<Employee> findByDepartment(String department) {
		return employeeRepo.findByDepartmentName(department);
	}

	public List<Employee> searchByEmployeeCode(String query) {
		if (query == null || query.isEmpty()) {
			return Collections.emptyList();
		}
		return employeeRepo.findByCodeContaining(query);
	}

}
