package yu.cs.spring.model.master.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.repo.DepartmentRepo;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	public void saveDepartment(Department department) {
		departmentRepo.save(department);
	}

	public List<Department> findAll() {
		return departmentRepo.findAll();
	}
}
