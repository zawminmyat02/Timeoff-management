package yu.cs.spring.model.master.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

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
	private List<LeaveApplication> leaveApplications = new ArrayList<>();

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
	
	@Column(nullable = false)
	private int sickLeaves;
	
	@Column(nullable = false)
	private int casualLeaves;
	
	@Column(nullable = false)
	private int maternityLeaves;
	
	@Column
	private int unpaidLeaves;
	
	@Column
	private BigDecimal monthlySalaries;

	private String remark;

	public enum Gender {
		Male, Female
	}

	public enum Status {
		Probation, Permanent
	}
	
}
