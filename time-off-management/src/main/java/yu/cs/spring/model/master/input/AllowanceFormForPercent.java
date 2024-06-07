package yu.cs.spring.model.master.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import yu.cs.spring.model.master.CalculationTarget;
import yu.cs.spring.model.master.entity.Allowance;
import yu.cs.spring.model.master.entity.AllowanceForPercent;

@Data
@EqualsAndHashCode(callSuper = false)
public class AllowanceFormForPercent extends AllowanceForm{

	@NotNull(message = "Enter Amount for percent calculation.")
	private BigDecimal amount;
	@NotNull(message = "Enter Amount for calculation target.")
	private CalculationTarget target;
	
	@Override
	public Allowance getEntity() {
		var entity = new AllowanceForPercent();
		entity.setName(getName());
		entity.setType(getType());
		entity.setDescription(getDescription());
		entity.setAmount(amount);
		entity.setTarget(target);
		return entity;
	}

}
