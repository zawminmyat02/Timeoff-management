package yu.cs.spring.model.master.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import yu.cs.spring.model.transaction.entity.LeaveApplication;

@Data
@Entity
public class Employee {

	@Id
	private String code;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private Account account;

	@ManyToOne(optional = false)
	private Department department;

	@ManyToOne(optional = false)
	private Position position;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LeaveApplication> leaveApplications;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@Column(nullable = false)
	private Status status;

	@Column(nullable = false, name = "assign_date")
	private LocalDate assignDate;

	private LocalDate probationPassDate;

	private String remark;

	public enum Gender {
		Male, Female
	}

	public enum Status {
		Probation, Permanent, Retired
	}
}
