package yu.cs.spring.model.master.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import yu.cs.spring.model.master.entity.Holiday.Type;

public record HolidayFormForUpdate(
		@NotNull(message = "Please select holiday type.")
		Type type,
		@NotBlank(message = "Please enter holiday name.")
		String holiday,
		String remark) {

}
