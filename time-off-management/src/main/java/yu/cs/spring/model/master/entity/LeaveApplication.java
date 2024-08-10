package yu.cs.spring.model.master.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class LeaveApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private LocalDateTime applyAt;

	@ManyToOne
	private Employee employee;
	
	@ManyToOne
    private Department department;

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
