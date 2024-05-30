package yu.cs.spring.model.master.output;


import lombok.Data;
import yu.cs.spring.model.master.CalculationType;
import yu.cs.spring.model.master.entity.Allowance;

@Data
public abstract class AllowanceInfo {

	private int id;
	private String name;
	private CalculationType type;
	private String description;
	
	
	public static AllowanceInfo from(Allowance entity) {
		return switch(entity.getType()) {
		case Fix -> AllowanceInfoForFix.from(entity);
		case Percent -> AllowanceInfoForPercent.from(entity);
		};
	}
}
