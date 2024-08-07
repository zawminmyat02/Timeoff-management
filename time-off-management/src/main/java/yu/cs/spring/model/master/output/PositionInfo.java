package yu.cs.spring.model.master.output;

import java.math.BigDecimal;
import java.util.ArrayList;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import yu.cs.spring.model.master.entity.Department_;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk;
import yu.cs.spring.model.master.entity.Position_;

public record PositionInfo(PositionPk id, String departmentCode, String departmentName, BigDecimal basicSalary,
		int sickLeaves, int casualLeaves, int maternityLeaves) {

	public String getCode() {
		return id.getCode();
	}

	public String getPositionCode() {
		return id.getPositionCode().name();
	}

	public String getPositionName() {
		return id.getPositionCode().getValue();
	}

	public static PositionInfo from(Position entity) {
		return new PositionInfo(entity.getId(), entity.getDepartment().getCode(), entity.getDepartment().getName(),
				entity.getBasicSalary(), entity.getSickLeaves(), entity.getCasualLeaves(),
				entity.getMaternityLeaves());
		
	}

	public static Selection<?>[] select(Root<Position> root) {
		var department = root.get(Position_.department);
		var list = new ArrayList<Selection<?>>();

		list.add(root.get(Position_.id));
		list.add(department.get(Department_.code));
		list.add(department.get(Department_.name));
		list.add(root.get(Position_.basicSalary));
		list.add(root.get(Position_.sickLeaves));
		list.add(root.get(Position_.casualLeaves));
		list.add(root.get(Position_.maternityLeaves));

		return list.toArray(size -> new Selection<?>[size]);
	}

}
