package yu.cs.spring.model.master.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Employee {

	@Id
	private String code;
	
	@OneToOne(optional = false, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private Account account;
	
	@ManyToOne(optional = false)
	private Department department;
	
	@ManyToOne(optional = false)
	private Position position;
	
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
	private LocalDate retireDate;
	
	private String remark;
	
	public enum Gender{
		Male, Female
	}
	
	public enum Status{
		Probation, Permanent, Retired
	}
}
