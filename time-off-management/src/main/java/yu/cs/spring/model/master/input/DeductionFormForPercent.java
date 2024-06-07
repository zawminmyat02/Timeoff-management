package yu.cs.spring.model.master.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import yu.cs.spring.model.master.CalculationTarget;
import yu.cs.spring.model.master.entity.Deduction;
import yu.cs.spring.model.master.entity.DeductionForPercent;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeductionFormForPercent extends DeductionForm{

	@NotNull(message = "Enter Amount for fix calculation.")
	private BigDecimal amount;
	@NotNull(message = "Enter Amount for calculation target.")
	private CalculationTarget target;

	@Override
	public Deduction getEntity() {
		var entity = new DeductionForPercent();
		entity.setName(getName());
		entity.setType(getType());
		entity.setDescription(getDescription());
		entity.setAmount(amount);
		entity.setTarget(target);
		return entity;
	}

}
