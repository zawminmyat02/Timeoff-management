package yu.cs.spring.model.master.input;

import jakarta.validation.constraints.NotBlank;
import yu.cs.spring.model.master.entity.LeaveType;

public record LeaveTypeForm(
		@NotBlank(message = "Please enter leave type name.")
		String name,
		int paidDays,
		String remark) {

	public LeaveType entity() {
		var entity = new LeaveType();
		entity.setName(name);
		entity.setPaidDays(paidDays);
		entity.setRemark(remark);
		return entity;
	}
}
