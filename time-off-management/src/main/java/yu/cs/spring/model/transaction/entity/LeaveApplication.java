package yu.cs.spring.model.transaction.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.LeaveType;

@Data
@Entity
public class LeaveApplication {


	@Id
	@GeneratedValue(generator = "leave_application_seq")
	private long id;
	
	@Column(nullable = false)
	private LocalDateTime applyAt;
	
	@ManyToOne
	private Employee employee;
	
	@ManyToOne
	private LeaveType type;

	@Column(nullable = false)
	private LocalDate startDate;
	
	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private String remark;
}
