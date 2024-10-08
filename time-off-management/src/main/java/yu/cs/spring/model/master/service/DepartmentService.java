package yu.cs.spring.model.master.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import yu.cs.spring.model.master.entity.Account;
import yu.cs.spring.model.master.entity.Account.Role;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.input.DepartmentFormForManagerChanges;
import yu.cs.spring.model.master.repo.AccountRepo;
import yu.cs.spring.model.master.repo.DepartmentRepo;
import yu.cs.spring.model.master.repo.EmployeeRepo;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private SessionRegistry sessionRegistry;

	private void invalidateUserSessions(String username) {
	    List<Object> principals = sessionRegistry.getAllPrincipals();

	    for (Object principal : principals) {
	        if (principal instanceof UserDetails) {
	            String principalName = ((UserDetails) principal).getUsername();

	            if (principalName.equals(username)) {
	                List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);

	                for (SessionInformation sessionInfo : sessions) {
	                    sessionInfo.expireNow(); // Mark the session as expired
	                }
	            }
	        }
	    }}
	    

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
	    
	    if( entity.getHeadOfDepartment() != null) {
		    var previousAccount = entity.getHeadOfDepartment().getAccount();
	    	previousAccount.setRole(Role.Employee);
	    	accountRepo.save(previousAccount);
	    	invalidateUserSessions(previousAccount.getUsername());

	    }
	   
	    entity.setHeadOfDepartment(manager);
	    
	    departmentRepo.save(entity);
	    
	    invalidateUserSessions(account.getUsername());

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
	
	public boolean isHeadCodeValid(String headCode,String departmentCode) {
	    return employeeRepo.existsByCodeAndDepartment(headCode,departmentCode); // Assuming headCode corresponds to an employee's ID
	}


}
