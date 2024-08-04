package yu.cs.spring.model.master.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
	
    @PersistenceContext
    private EntityManager entityManager;

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

	public List<LeaveApplication> getLeaveApplicationsByDepartment(String departmentName) {
		return leaveApplicationRepository.findByDepartment_Name(departmentName);
	}

	public List<LeaveApplication> findAllApprovedOrRejected() {
		return leaveApplicationRepository.findByStatusIn(Arrays.asList(Status.APPROVED, Status.REJECTED));
	}

	public List<LeaveApplication> findPendingLeaveApplications() {
		return leaveApplicationRepository.findByStatus(LeaveApplication.Status.PENDING);
	}
	
	public List<LeaveApplication> searchPendingLeaves(LocalDate fromDate, LocalDate toDate, String leaveType, String department) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LeaveApplication> query = cb.createQuery(LeaveApplication.class);
        Root<LeaveApplication> leave = query.from(LeaveApplication.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filter by status (pending)
        predicates.add(cb.equal(leave.get("status"), LeaveApplication.Status.PENDING));

        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(leave.get("startDate"), fromDate));
        }

        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(leave.get("endDate"), toDate));
        }

        if (leaveType != null && !leaveType.isEmpty()) {
            predicates.add(cb.equal(leave.get("type").get("name"), leaveType));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }

	public List<LeaveApplication> searchLeaves(LocalDate fromDate, LocalDate toDate, String leaveType, String status, String department) {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<LeaveApplication> query = cb.createQuery(LeaveApplication.class);
	    Root<LeaveApplication> leave = query.from(LeaveApplication.class);
	    List<Predicate> predicates = new ArrayList<>();
	    
	    // Filter by status (not pending)
        predicates.add(cb.notEqual(leave.get("status"), LeaveApplication.Status.PENDING));

	    if (fromDate != null) {
	        predicates.add(cb.greaterThanOrEqualTo(leave.get("startDate"), fromDate));
	    }

	    if (toDate != null) {
	        predicates.add(cb.lessThanOrEqualTo(leave.get("endDate"), toDate));
	    }

	    if (leaveType != null && !leaveType.isEmpty()) {
	        predicates.add(cb.equal(leave.get("type").get("name"), leaveType));
	    }

	    if (status != null && !status.isEmpty()) {
	        predicates.add(cb.equal(leave.get("status"), LeaveApplication.Status.valueOf(status.toUpperCase())));
	    }

	    if (department != null && !department.isEmpty()) {
	        predicates.add(cb.equal(leave.get("department").get("name"), department));
	    }

	    query.where(predicates.toArray(new Predicate[0]));
	    return entityManager.createQuery(query).getResultList();
	}

	public List<LeaveApplication> searchLeavesForEmployee(LocalDate fromDate, LocalDate toDate, String leaveType, String status) {
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<LeaveApplication> query = cb.createQuery(LeaveApplication.class);
		    Root<LeaveApplication> leave = query.from(LeaveApplication.class);
		    List<Predicate> predicates = new ArrayList<>();
		    
		    // Filter by status (not pending)
	        predicates.add(cb.notEqual(leave.get("status"), LeaveApplication.Status.PENDING));

		    if (fromDate != null) {
		        predicates.add(cb.greaterThanOrEqualTo(leave.get("startDate"), fromDate));
		    }

		    if (toDate != null) {
		        predicates.add(cb.lessThanOrEqualTo(leave.get("endDate"), toDate));
		    }

		    if (leaveType != null && !leaveType.isEmpty()) {
		        predicates.add(cb.equal(leave.get("type").get("name"), leaveType));
		    }

		    if (status != null && !status.isEmpty()) {
		        predicates.add(cb.equal(leave.get("status"), LeaveApplication.Status.valueOf(status.toUpperCase())));
		    }

		    query.where(predicates.toArray(new Predicate[0]));
		    return entityManager.createQuery(query).getResultList();
	}

	public List<LeaveApplication> searchLeavesForHOD(String department, LocalDate fromDate, LocalDate toDate, String leaveType, String status) {
	    return searchLeaves(fromDate, toDate, leaveType, status, department);
	}


}


