package yu.cs.spring.model.transaction.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import yu.cs.spring.model.transaction.entity.LeaveApplication;

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
