package yu.cs.spring.model.master.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.LeaveType;
import yu.cs.spring.model.master.input.LeaveApplicationForm;
import yu.cs.spring.model.master.repo.LeaveTypeRepo;
import yu.cs.spring.model.transaction.entity.LeaveApplication;
import yu.cs.spring.model.transaction.entity.LeaveApplication.Status;
import yu.cs.spring.model.transaction.repo.LeaveApplicationRepo;

@Service
public class LeaveApplicationService {

	@Autowired
	private LeaveApplicationRepo leaveApplicationRepository;

	@Autowired
	private LeaveTypeRepo leaveTypeRepository;

	@Transactional
	public void createLeaveApplication(LeaveApplicationForm form, Employee employee) {
		LeaveType leaveType = leaveTypeRepository.findById(form.leaveTypeId().intValue())
				.orElseThrow(() -> new IllegalArgumentException("Invalid leave type ID"));

		LeaveApplication leaveApplication = form.toEntity(employee, leaveType);
		leaveApplicationRepository.save(leaveApplication);
	}

	public List<LeaveApplication> getAllLeaveApplications() {
		return leaveApplicationRepository.findAll();
	}

	public List<LeaveApplication> getLeaveApplicationsByUsername(String username) {
		return leaveApplicationRepository.findByEmployeeAccountUsername(username);
	}

	public boolean updateStatus(Long id, String status) {
		Optional<LeaveApplication> optionalLeaveApplication = leaveApplicationRepository.findById(id);
		if (optionalLeaveApplication.isPresent()) {
			LeaveApplication leaveApplication = optionalLeaveApplication.get();
			leaveApplication.setStatus(Status.valueOf(status));
			leaveApplicationRepository.save(leaveApplication);
			return true;
		}
		return false;
	}

}
