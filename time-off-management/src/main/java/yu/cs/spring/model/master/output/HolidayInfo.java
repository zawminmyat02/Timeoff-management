package yu.cs.spring.model.master.output;

import java.time.LocalDate;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import yu.cs.spring.model.master.entity.Holiday;
import yu.cs.spring.model.master.entity.Holiday.Type;
import yu.cs.spring.model.master.entity.Holiday_;

public record HolidayInfo(
		LocalDate date,
		Type type,
		String holiday,
		String remark,
		boolean deleted
		) {

	public static void select(CriteriaQuery<HolidayInfo> cq, Root<Holiday> root) {
		// select h.date, h.type, h.holiday, h.remark, h.deleted
		cq.multiselect(
			root.get(Holiday_.date),
			root.get(Holiday_.type),
			root.get(Holiday_.holiday),
			root.get(Holiday_.remark)
		);
	}
}
