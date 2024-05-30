package yu.cs.spring.model.master.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import yu.cs.spring.model.master.entity.Holiday;
import yu.cs.spring.model.master.entity.Holiday.Type;

public record HolidayInfoDetails(
		LocalDate date,
		Type type,
		String holiday,
		String remark) {

	public static HolidayInfoDetails from(Holiday entity) {
		return new HolidayInfoDetails(entity.getDate(), 
				entity.getType(), 
				entity.getHoliday(), 
				entity.getRemark()
				);
	}

}
