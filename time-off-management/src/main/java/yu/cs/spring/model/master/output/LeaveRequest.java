package yu.cs.spring.model.master.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import yu.cs.spring.model.master.entity.LeaveApplication;

public record LeaveRequest(  
		long id,
	    LocalDateTime applyAt,
	    String employeeName,
	    String departmentName,
	    String leaveTypeName,
	    LocalDate startDate,
	    LocalDate endDate,
	    String remark,
	    LeaveApplication.Status status) {

}
