package yu.cs.spring.model.master.output;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import yu.cs.spring.model.master.entity.LeaveType;
import yu.cs.spring.model.master.entity.LeaveType_;

public record LeaveTypeInfo(
		int id,
		String name,
		int paidDays,
		String remark) {

	public static void select(CriteriaQuery<LeaveTypeInfo> cq, Root<LeaveType> root) {
		cq.multiselect(
			root.get(LeaveType_.id),
			root.get(LeaveType_.name),
			root.get(LeaveType_.paidDays),
			root.get(LeaveType_.remark)
		);
	}
	
	public static LeaveTypeInfo from(LeaveType entity) {
		return new LeaveTypeInfo(
				entity.getId(), 
				entity.getName(), 
				entity.getPaidDays(), 
				entity.getRemark());
	}
}
