package yu.cs.spring.model.master.output;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import yu.cs.spring.model.master.CalculationTarget;
import yu.cs.spring.model.master.entity.Allowance;
import yu.cs.spring.model.master.entity.AllowanceForFix;

@Data
@EqualsAndHashCode(callSuper = false)
public class AllowanceInfoForFix extends AllowanceInfo{

	private BigDecimal amount;
	private CalculationTarget target;

	public static AllowanceInfo from(Allowance entity) {
		
		if(entity instanceof AllowanceForFix dto) {
			var info = new AllowanceInfoForFix();
			info.setId(dto.getId());
			info.setName(dto.getName());
			info.setDescription(dto.getDescription());
			info.setType(dto.getType());
			info.setAmount(dto.getAmount());
			info.setTarget(dto.getTarget());
			return info;
		}
		
		return null;
	}
}
