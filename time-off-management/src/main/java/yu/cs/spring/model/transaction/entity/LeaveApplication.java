package yu.cs.spring.model.transaction.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.LeaveType;

@Data
@Entity
@SequenceGenerator(name = "leave_application_seq", allocationSize = 1)
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	public enum Status {
		APPROVED, PENDING, REJECTED
	}
}
