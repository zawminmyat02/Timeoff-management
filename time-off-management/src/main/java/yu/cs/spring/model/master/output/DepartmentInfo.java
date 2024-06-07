package yu.cs.spring.model.master.output;



import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import yu.cs.spring.model.master.entity.Account_;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Department_;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee_;

public record DepartmentInfo(
		String code,
		String name,
		String managerCode,
		String managerName,
		String managerPhone,
		long employees) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<DepartmentInfo> cq, Root<Department> root) {
		
		var manager = root.join(Department_.headOfDepartment, JoinType.LEFT);
		var employee = root.join(Department_.employees, JoinType.LEFT);
		var account = employee.join(Employee_.account, JoinType.LEFT);
		
		cq.multiselect(
			root.get(Department_.code),
			root.get(Department_.name),
			manager.get(Employee_.code),
			account.get(Account_.name),
			manager.get(Employee_.phone),
			cb.count(employee.get(Employee_.code))
		);
		
		cq.groupBy(
			root.get(Department_.code),
			root.get(Department_.name),
			manager.get(Employee_.code),
			account.get(Account_.name),
			manager.get(Employee_.phone)
		);
	}
	
	  public static DepartmentInfo from(Department department) {
	        Employee manager = department.getHeadOfDepartment();
	        return new DepartmentInfo(
	        		department.getCode(),
	                department.getName(),
	                manager != null ? manager.getCode() : null,
	                manager != null ? manager.getAccount().getName() : null,
	                manager != null ? manager.getPhone() : null,
	                department.getEmployees().size()
	        );
	    }
}
