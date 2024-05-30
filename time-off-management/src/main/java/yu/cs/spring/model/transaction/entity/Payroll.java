package yu.cs.spring.model.transaction.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import yu.cs.spring.model.master.entity.Employee;

@Data
@Entity
public class Payroll {

	@EmbeddedId
	private PayrollPk id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_code", referencedColumnName = "code", insertable = false, updatable = false)
	private Employee employee;
	
	@Column(nullable = false)
	private BigDecimal baseSalary;
	@Column(nullable = false)
	private BigDecimal totalAllowance;
	@Column(nullable = false)
	private BigDecimal totalDeduction;
}
