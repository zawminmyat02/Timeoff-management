package yu.cs.spring.model.master.output;

import java.time.LocalDate;



import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import yu.cs.spring.model.master.entity.Account_;
import yu.cs.spring.model.master.entity.Department_;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee.Status;
import yu.cs.spring.model.master.entity.Employee_;
import yu.cs.spring.model.master.entity.PositionPk.PositionCode;
import yu.cs.spring.model.master.entity.PositionPk_;
import yu.cs.spring.model.master.entity.Position_;

public record EmployeeInfo(
		String code,
		String name,
		String phone,
		String department,
		PositionCode position,
		Status status,
		LocalDate assignAt,
		LocalDate probationPassAt,
		LocalDate retiredAt,
		String remark) {
	
	public String getPositionName() {
		return position.getValue();
	}
	
	public static void select(CriteriaQuery<EmployeeInfo> cq, Root<Employee> root) {
		cq.multiselect(
			root.get(Employee_.code),
			root.get(Employee_.account).get(Account_.name),
			root.get(Employee_.phone),
			root.get(Employee_.department).get(Department_.name),
			root.get(Employee_.position).get(Position_.id).get(PositionPk_.positionCode),
			root.get(Employee_.status),
			root.get(Employee_.assignDate),
			root.get(Employee_.probationPassDate),
			root.get(Employee_.retireDate),
			root.get(Employee_.remark)
		);
	}

	public static EmployeeInfo from(Employee entity) {
		return new EmployeeInfo(
			entity.getCode(), 
			entity.getAccount().getName(), 
			entity.getPhone(), 
			entity.getDepartment().getName(), 
			entity.getPosition().getId().getPositionCode(), 
			entity.getStatus(), 
			entity.getAssignDate(), 
			entity.getProbationPassDate(),
			entity.getRetireDate(), 
			entity.getRemark());
	}
}
