package yu.cs.spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yu.cs.spring.model.master.entity.LeaveApplication;
import yu.cs.spring.model.master.output.LeaveRequest;
import yu.cs.spring.model.master.service.AccountService;
import yu.cs.spring.model.master.service.LeaveApplicationService;

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
	String home(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
			@RequestParam(required = false) String leaveType, @RequestParam(required = false) String department,
			Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = accountService.getNameByUserName(auth.getName());

		model.addAttribute("name", name);
		model.addAttribute("email", auth.getName());

		Function<String, Boolean> hasAuthority = authority -> SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));

		List<LeaveApplication> allpendingLeaves = leaveApplicationService.searchPendingLeaves(fromDate, toDate,
				leaveType, department);

		List<LeaveRequest> leaveApplications = allpendingLeaves.stream()
				.map(leaveApplication -> new LeaveRequest(leaveApplication.getId(), leaveApplication.getApplyAt(),
						leaveApplication.getEmployee().getAccount().getName(),
						leaveApplication.getDepartment().getName(), leaveApplication.getType().getName(),
						leaveApplication.getStartDate(), leaveApplication.getEndDate(), leaveApplication.getRemark(),
						leaveApplication.getStatus()))
				.collect(Collectors.toList());
		model.addAttribute("leaveApplications", leaveApplications);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("leaveType", leaveType);

		if (hasAuthority.apply("Employee")) {


			List<LeaveRequest> userLeaves = leaveApplicationService.getLeaveApplicationsByUsername(auth.getName())
					.stream().filter(allpendingLeaves::contains)
					.map(leaveApplication -> new LeaveRequest(leaveApplication.getId(), leaveApplication.getApplyAt(),
							leaveApplication.getEmployee().getAccount().getName(),
							leaveApplication.getDepartment().getName(), leaveApplication.getType().getName(),
							leaveApplication.getStartDate(), leaveApplication.getEndDate(),
							leaveApplication.getRemark(), leaveApplication.getStatus()))
					.collect(Collectors.toList());

			model.addAttribute("leaveApplications", userLeaves);

			List<LeaveApplication> pendingUserLeaves = leaveApplicationService
					.getLeaveApplicationsByUsername(auth.getName()).stream().filter(allpendingLeaves::contains)
					.collect(Collectors.toList());
			model.addAttribute("pending", pendingUserLeaves);

			return "leave-request";
		}

		if (hasAuthority.apply("HOD")) {
			
			String departmentList = accountService.getDepartmentByUserName(auth.getName());
			List<LeaveRequest> departmentLeaves = leaveApplicationService
					.getLeaveApplicationsByDepartment(departmentList).stream().filter(allpendingLeaves::contains)
					.map(leaveApplication -> new LeaveRequest(leaveApplication.getId(), leaveApplication.getApplyAt(),
							leaveApplication.getEmployee().getAccount().getName(),
							leaveApplication.getDepartment().getName(), leaveApplication.getType().getName(),
							leaveApplication.getStartDate(), leaveApplication.getEndDate(),
							leaveApplication.getRemark(), leaveApplication.getStatus()))
					.collect(Collectors.toList());

	
			model.addAttribute("leaveApplications", departmentLeaves);
			return "admin-home";
		}

		return "admin-home";

	}

}