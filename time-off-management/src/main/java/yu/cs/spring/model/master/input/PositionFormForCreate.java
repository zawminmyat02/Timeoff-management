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
		@NotNull(message = "Please enter OT fees.")
		BigDecimal otPerHour,
		@NotNull(message = "Please enter anual leaves.")
		Integer anualLeaves
		) {

	public Position entity() {
		var id = new PositionPk();
		id.setDepartmentCode(department);
		id.setPositionCode(PositionCode.valueOf(position));

		var entity = new Position();
		entity.setId(id);
		entity.setBasicSalary(basicSalary);
		entity.setOtFeesPerHour(otPerHour);
		entity.setAnualLeaves(anualLeaves);
		return entity;
	}

}
