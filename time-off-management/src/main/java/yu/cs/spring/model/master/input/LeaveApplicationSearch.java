package yu.cs.spring.model.master.input;

import java.time.LocalDate;

public record LeaveApplicationSearch(
	    LocalDate fromDate,
	    LocalDate toDate,
	    String leaveType,
	    String department
	) {}