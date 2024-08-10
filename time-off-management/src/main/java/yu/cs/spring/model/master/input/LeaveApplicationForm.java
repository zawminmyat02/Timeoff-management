package yu.cs.spring.model.master.input;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.LeaveApplication;
import yu.cs.spring.model.master.entity.LeaveType;

public record LeaveApplicationForm(@NotNull(message = "Leave type ID is required.") Long leaveTypeId,

		@NotNull(message = "Start date is required.") LocalDate startDate,

		@NotNull(message = "End date is required.") LocalDate endDate,

		@NotBlank(message = "Remark is required.") String remark) {

	public LeaveApplication toEntity(Employee employee, LeaveType leaveType) {
		LeaveApplication leaveApplication = new LeaveApplication();
		leaveApplication.setEmployee(employee);
		leaveApplication.setDepartment(employee.getDepartment());
		leaveApplication.setType(leaveType);
		leaveApplication.setStartDate(startDate);
		leaveApplication.setEndDate(endDate);
		leaveApplication.setRemark(remark);
		leaveApplication.setApplyAt(LocalDateTime.now());
		leaveApplication.setStatus(LeaveApplication.Status.PENDING);
		return leaveApplication;
	}
}