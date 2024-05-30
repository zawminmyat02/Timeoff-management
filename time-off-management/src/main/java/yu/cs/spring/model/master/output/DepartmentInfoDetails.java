package yu.cs.spring.model.master.output;

import java.util.List;

import org.apache.groovy.parser.antlr4.PositionInfo;

import yu.cs.spring.model.master.entity.Department;

public record DepartmentInfoDetails(
		String code,
		String name,
		String description, 
		EmployeeInfo hod,
		List<EmployeeInfo> employees) {
	
	public static DepartmentInfoDetails from(Department entity) {
		return new DepartmentInfoDetails(
				entity.getCode(), 
				entity.getName(), 
				entity.getDescription(), 
				null != entity.getHeadOfDepartment() ? EmployeeInfo.from(entity.getHeadOfDepartment()) : null, 
				entity.getEmployees().stream().map(EmployeeInfo::from).toList());
	}

}
