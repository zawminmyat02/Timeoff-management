package yu.cs.spring.model.master.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.input.DepartmentFormForManagerChanges;
import yu.cs.spring.model.master.repo.DepartmentRepo;
import yu.cs.spring.model.master.repo.EmployeeRepo;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;

	public void saveDepartment(Department department) {
		departmentRepo.save(department);
	}

	public List<Department> findAll() {
		return departmentRepo.findAll();
	}

	public void update(String departmentCode, DepartmentFormForManagerChanges form) {
	    Department entity = departmentRepo.findById(departmentCode).orElseThrow(() -> new EntityNotFoundException("Department not found"));
	    Employee manager = employeeRepo.findById(form.headCode()).orElseThrow(() -> new EntityNotFoundException("Employee not found"));

	    entity.setHeadOfDepartment(manager);
	    
	    departmentRepo.save(entity);
	}

}
