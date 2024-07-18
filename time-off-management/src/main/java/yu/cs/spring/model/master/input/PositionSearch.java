package yu.cs.spring.model.master.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import yu.cs.spring.model.master.entity.Department_;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk;
import yu.cs.spring.model.master.entity.PositionPk_;
import yu.cs.spring.model.master.entity.Position_;

public record PositionSearch(
		String department, 
		String position) {
	
	public Predicate[] where(CriteriaBuilder cb, Root<Position> root) {
		var list = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(department)) {
			list.add(
				cb.equal(root.get(Position_.department).get(Department_.code), department)
			);
		}
		
		if(StringUtils.hasLength(position)) {
			try {
                PositionPk.PositionCode positionCode = PositionPk.PositionCode.valueOf(position);
                list.add(cb.equal(root.get(Position_.id).get(PositionPk_.positionCode), positionCode));
            } catch (IllegalArgumentException e) {
                // Log the error and proceed without adding the position code predicate
            }
		}
		
		return list.toArray(size -> new Predicate[size]);
	}

}
