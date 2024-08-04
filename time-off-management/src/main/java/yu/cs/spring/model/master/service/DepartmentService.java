package yu.cs.spring.model.master.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import yu.cs.spring.model.master.entity.Account;
import yu.cs.spring.model.master.entity.Account.Role;
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
	    Account account = manager.getAccount();
	    account.setRole(Role.HOD);

	    entity.setHeadOfDepartment(manager);
	    
	    departmentRepo.save(entity);
	}

	public Department findByName(String userDepartment) {
		// TODO Auto-generated method stub
		return departmentRepo.findByName(userDepartment);
	}
	
	public List<Department> searchByDepartmentCode(String query) {
		if (query == null || query.isEmpty()) {
			return Collections.emptyList();
		}
		return departmentRepo.findByCodeContaining(query);
	}
	
	public List<String> getAllDepartmentNames() {
        return departmentRepo.findAll().stream()
                                   .map(Department::getName)
                                   .collect(Collectors.toList());
    }


}
