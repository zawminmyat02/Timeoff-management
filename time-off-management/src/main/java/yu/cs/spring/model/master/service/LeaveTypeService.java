package yu.cs.spring.model.master.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.master.entity.LeaveType;
import yu.cs.spring.model.master.repo.LeaveTypeRepo;

@Service
public class LeaveTypeService {
	
	@Autowired
	private LeaveTypeRepo repo;

	public List<LeaveType> findAll() {
		return repo.findAll();
	}

}
