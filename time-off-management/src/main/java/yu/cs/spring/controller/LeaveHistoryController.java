package yu.cs.spring.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import yu.cs.spring.model.master.service.AccountService;
import yu.cs.spring.model.master.service.LeaveApplicationService;
import yu.cs.spring.model.transaction.entity.LeaveApplication;

@Controller
public class LeaveHistoryController {

	@Autowired
	private LeaveApplicationService leaveApplicationService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/leave-history")
	public String showLeaveHistory(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = accountService.getNameByUserName(auth.getName());

		model.addAttribute("name", name);
		model.addAttribute("email", auth.getName());

		Function<String, Boolean> hasAuthority = authority -> SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));

		if (hasAuthority.apply("Admin")) {
			List<LeaveApplication> leaveHistory = leaveApplicationService.findAllApprovedOrRejected();
			model.addAttribute("leaveHistory", leaveHistory);
			return "leave-history";
		}

		if (hasAuthority.apply("Employee")) {
			// Assume findAllApprovedOrRejected returns a Set or List of approved/rejected
			// applications
			List<LeaveApplication> approvedOrRejectedLeaves = leaveApplicationService.findAllApprovedOrRejected();

			List<LeaveApplication> leaveHistory = leaveApplicationService.getLeaveApplicationsByUsername(auth.getName())
					.stream()
					.filter(leave -> approvedOrRejectedLeaves != null && approvedOrRejectedLeaves.contains(leave))
					.collect(Collectors.toList());

			model.addAttribute("leaveHistory", leaveHistory);
			return "leave-history";
		}

		if (hasAuthority.apply("HOD")) {
			// Assume findAllApprovedOrRejected returns a Set or List of approved/rejected
			// applications
			List<LeaveApplication> approvedOrRejectedLeaves = leaveApplicationService.findAllApprovedOrRejected();

			String department = accountService.getDepartmentByUserName(auth.getName());
			List<LeaveApplication> leaveHistory = leaveApplicationService
					.getLeaveApplicationsByDepartment(department).stream().filter(approvedOrRejectedLeaves::contains)
					.collect(Collectors.toList());

			model.addAttribute("leaveHistory", leaveHistory);
			return "leave-history";
		}

		return "leave-history";

	}

}
