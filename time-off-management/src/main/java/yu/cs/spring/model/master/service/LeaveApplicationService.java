package yu.cs.spring.model.master.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import yu.cs.spring.model.master.repo.EmployeeRepo;
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

	@Autowired
	private EmployeeRepo employeeRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void createLeaveApplication(LeaveApplicationForm form, Employee employee) {
		LeaveType leaveType = leaveTypeRepository.findById(form.leaveTypeId().intValue())
				.orElseThrow(() -> new IllegalArgumentException("Invalid leave type ID"));

		LeaveApplication leaveApplication = form.toEntity(employee, leaveType);
		leaveApplicationRepository.save(leaveApplication);
	}

	public boolean checkDateOverlap(LocalDate startDate, LocalDate endDate, String employeeCode) {
		List<LeaveApplication> overlappingLeaves = leaveApplicationRepository.findOverlappingLeaves(startDate, endDate,
				employeeCode);
		boolean decision = !overlappingLeaves.isEmpty();
		return decision;
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

			if (status == "APPROVED" || status.equals("APPROVED")) {
				createLeaveBalance(leaveApplication.getEmployee().getCode(), leaveApplication.getId());

			}
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

	public List<LeaveApplication> searchPendingLeaves(LocalDate fromDate, LocalDate toDate, String leaveType,
			String department) {
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

	public List<LeaveApplication> searchLeaves(LocalDate fromDate, LocalDate toDate, String leaveType, String status,
			String department) {
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

	public List<LeaveApplication> searchLeavesForEmployee(LocalDate fromDate, LocalDate toDate, String leaveType,
			String status) {
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

	public List<LeaveApplication> searchLeavesForHOD(String department, LocalDate fromDate, LocalDate toDate,
			String leaveType, String status) {
		return searchLeaves(fromDate, toDate, leaveType, status, department);
	}

	public void createLeaveBalance(String employeeCode, long id) {
		Employee employee = employeeRepository.findByCode(employeeCode);

		// Fetch initial leave balances
		List<Object[]> leaveBalances = employeeRepository
				.findLeaveBalancesByEmployeeCodeAndLeaveApplicationId(employeeCode, id);
		int sickLeaves = (int) leaveBalances.get(0)[0];
		int casualLeaves = (int) leaveBalances.get(0)[1];
		int maternityLeaves = (int) leaveBalances.get(0)[2];
		int unpaidLeaves = (int) leaveBalances.get(0)[3];

		// Fetch approved leave applications
		List<LeaveApplication> approvedLeaveApplications = leaveApplicationRepository
				.findApprovedLeaveApplicationsByEmployeeCodeAndLeaveApplicationId(employeeCode, id);

		for (LeaveApplication leaveApplication : approvedLeaveApplications) {
			long daysBetween = ChronoUnit.DAYS.between(leaveApplication.getStartDate(), leaveApplication.getEndDate())
					+ 1; // inclusive of both start and end date
			switch (leaveApplication.getType().getName()) {
			case "Sick Leave":
				if (daysBetween > sickLeaves) {
					unpaidLeaves += (daysBetween - sickLeaves);
					sickLeaves = 0;
				} else {
					sickLeaves -= daysBetween;
				}
				break;
			case "Casual Leave":
				if (daysBetween > casualLeaves) {
					unpaidLeaves += (daysBetween - casualLeaves);
					casualLeaves = 0;
				} else {
					casualLeaves -= daysBetween;
				}
				break;
			case "Maternity Leave":
				if (daysBetween > maternityLeaves) {
					unpaidLeaves += (daysBetween - maternityLeaves);
					maternityLeaves = 0;
				} else {
					maternityLeaves -= daysBetween;
				}
				break;
			}
		}
		BigDecimal salary = employee.getMonthlySalaries(); // This method returns BigDecimal
		BigDecimal dailySalary = salary.divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP); // Assuming a 30-day month for simplicity
		BigDecimal totalDeduction = dailySalary.multiply(BigDecimal.valueOf(unpaidLeaves)).multiply(BigDecimal.valueOf(0.80));

		// Deduct the amount from the current salary
		BigDecimal newSalary = salary.subtract(totalDeduction);

		// Update the employee's leave balances and salary
		employee.setSickLeaves(sickLeaves);
		employee.setCasualLeaves(casualLeaves);
		employee.setMaternityLeaves(maternityLeaves);
		employee.setUnpaidLeaves(unpaidLeaves);
		employee.setMonthlySalaries(newSalary); // Update the salary

		employee.setUnpaidLeaves(unpaidLeaves);
		employeeRepository.save(employee);
	}

}
