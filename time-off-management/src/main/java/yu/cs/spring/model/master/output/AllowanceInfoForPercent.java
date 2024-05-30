package yu.cs.spring.model.master.output;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import yu.cs.spring.model.master.CalculationTarget;
import yu.cs.spring.model.master.entity.Allowance;
import yu.cs.spring.model.master.entity.AllowanceForPercent;

@Data
@EqualsAndHashCode(callSuper = false)
public class AllowanceInfoForPercent extends AllowanceInfo{

	private BigDecimal amount;
	private CalculationTarget target;

	public static AllowanceInfo from(Allowance entity) {
		
		if(entity instanceof AllowanceForPercent dto) {
			var info = new AllowanceInfoForPercent();
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
