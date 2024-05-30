package yu.cs.spring.model.master.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import yu.cs.spring.model.master.CalculationTarget;
import yu.cs.spring.model.master.CalculationType;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class AllowanceForPercent extends Allowance {

	public AllowanceForPercent() {
		setType(CalculationType.Percent);
	}
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)
	private CalculationTarget target;
}
