package yu.cs.spring.model.master.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk;
import yu.cs.spring.model.master.entity.PositionPk.PositionCode;

public record PositionFormForCreate(
		@NotBlank(message = "Please select department.")
		String department,
		@NotBlank(message = "Please enter position code.")
		String position,
		@NotNull(message = "Please enter basic salary.")
		BigDecimal basicSalary,
		@NotNull(message = "Please enter sick leaves.")
		Integer sickLeaves,
		@NotNull(message = "Please enter casual leaves.")
		Integer casualLeaves
		) {

	public Position entity() {
		var id = new PositionPk();
		id.setDepartmentCode(department);
		id.setPositionCode(PositionCode.valueOf(position));

		var entity = new Position();
		entity.setId(id);
		entity.setBasicSalary(basicSalary);
		entity.setSickLeaves(sickLeaves);
		entity.setCasualLeaves(casualLeaves);
		return entity;
	}

}
