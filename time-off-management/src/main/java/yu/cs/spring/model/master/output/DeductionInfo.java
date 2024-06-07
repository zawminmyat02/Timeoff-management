package yu.cs.spring.model.master.output;

import lombok.Data;
import yu.cs.spring.model.master.CalculationType;
import yu.cs.spring.model.master.entity.Deduction;

@Data
public class DeductionInfo {

	private int id;
	private String name;
	private CalculationType type;
	private String description;
	
	
	public static DeductionInfo from(Deduction entity) {
		return switch(entity.getType()) {
		case Fix -> DeductionInfoForFix.from(entity);
		case Percent -> DeductionInfoForPercent.from(entity);
		};
	}

}
