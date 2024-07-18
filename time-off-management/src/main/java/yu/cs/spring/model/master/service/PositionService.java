package yu.cs.spring.model.master.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk_;
import yu.cs.spring.model.master.entity.Position_;
import yu.cs.spring.model.master.input.PositionSearch;
import yu.cs.spring.model.master.output.PositionInfo;
import yu.cs.spring.model.master.repo.PositionRepo;

@Service
public class PositionService {

	@Autowired
	private PositionRepo repo;

	public List<Position> findAll() {
		return repo.findAll();

	}

	public void save(Position entity) {
		repo.save(entity);
	}

	public List<String> getPositionCodesByDepartment(String department) {
		List<Position> positions = repo.findByDepartmentCode(department);
		return positions.stream().map(position -> position.getId().getPositionCode().getValue())
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PositionInfo> search(PositionSearch search) {
		return repo.search(queryFunc(search));
	}

	private Function<CriteriaBuilder, CriteriaQuery<PositionInfo>> queryFunc(PositionSearch search) {
		return cb -> {
			var cq = cb.createQuery(PositionInfo.class);

			var root = cq.from(Position.class);
			cq.multiselect(PositionInfo.select(root));
			cq.where(search.where(cb, root));
			cq.orderBy(cb.asc(root.get(Position_.id).get(PositionPk_.departmentCode)),
					cb.asc(root.get(Position_.id).get(PositionPk_.positionCode)));
			return cq;
		};
	}

}
