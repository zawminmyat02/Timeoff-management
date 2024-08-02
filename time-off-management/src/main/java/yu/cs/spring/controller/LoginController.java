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
import yu.cs.spring.model.transaction.output.LeaveRequest;

@Controller
public class LoginController {

	@Autowired
	private LeaveApplicationService leaveApplicationService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/")
	String start() {
		return "login";
	}

	@GetMapping("/home")
	String home(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = accountService.getNameByUserName(auth.getName());

		model.addAttribute("name", name);
		model.addAttribute("email", auth.getName());

		Function<String, Boolean> hasAuthority = authority -> SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));

		List<LeaveRequest> leaveApplications = leaveApplicationService.findPendingLeaveApplications().stream()
				.map(leaveApplication -> new LeaveRequest(leaveApplication.getId(), leaveApplication.getApplyAt(),
						leaveApplication.getEmployee().getAccount().getName(),
						leaveApplication.getDepartment().getName(), leaveApplication.getType().getName(),
						leaveApplication.getStartDate(), leaveApplication.getEndDate(), leaveApplication.getRemark(),
						leaveApplication.getStatus()))
				.collect(Collectors.toList());
		model.addAttribute("leaveApplications", leaveApplications);

		if (hasAuthority.apply("Employee")) {
			
			List<LeaveApplication> pendingLeaves = leaveApplicationService.findPendingLeaveApplications();

			List<LeaveRequest> userLeaves = leaveApplicationService.getLeaveApplicationsByUsername(auth.getName())
					.stream().filter(pendingLeaves::contains)
					.map(leaveApplication -> new LeaveRequest(leaveApplication.getId(), leaveApplication.getApplyAt(),
							leaveApplication.getEmployee().getAccount().getName(),
							leaveApplication.getDepartment().getName(), leaveApplication.getType().getName(),
							leaveApplication.getStartDate(), leaveApplication.getEndDate(),
							leaveApplication.getRemark(), leaveApplication.getStatus()))
					.collect(Collectors.toList());

			model.addAttribute("leaveApplications", userLeaves);


			List<LeaveApplication> pendingUserLeaves = leaveApplicationService
					.getLeaveApplicationsByUsername(auth.getName()).stream().filter(pendingLeaves::contains)
					.collect(Collectors.toList());
			model.addAttribute("pending", pendingUserLeaves);

			return "leave-request";
		}

		if (hasAuthority.apply("HOD")) {
			List<LeaveApplication> pendingLeaves = leaveApplicationService.findPendingLeaveApplications();

			String department = accountService.getDepartmentByUserName(auth.getName());
			List<LeaveRequest> departmentLeaves = leaveApplicationService.getLeaveApplicationsByDepartment(department)
					.stream().filter(pendingLeaves::contains)
					.map(leaveApplication -> new LeaveRequest(leaveApplication.getId(), leaveApplication.getApplyAt(),
							leaveApplication.getEmployee().getAccount().getName(),
							leaveApplication.getDepartment().getName(), leaveApplication.getType().getName(),
							leaveApplication.getStartDate(), leaveApplication.getEndDate(),
							leaveApplication.getRemark(), leaveApplication.getStatus()))
					.collect(Collectors.toList());
			
			List<LeaveApplication> pendingUserLeaves = leaveApplicationService
					.getLeaveApplicationsByDepartment(department).stream().filter(pendingLeaves::contains)
					.collect(Collectors.toList());
			model.addAttribute("pending", pendingUserLeaves);

			model.addAttribute("leaveApplications", departmentLeaves);
			return "admin-home";
		}

		return "admin-home";

	}

}