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

import yu.cs.spring.model.master.service.AccountService;
import yu.cs.spring.model.master.service.DepartmentService;
import yu.cs.spring.model.master.service.LeaveApplicationService;
import yu.cs.spring.model.transaction.entity.LeaveApplication;

@Controller
public class LeaveHistoryController {

	@Autowired
	private LeaveApplicationService leaveApplicationService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private DepartmentService departmentService;
	

	@GetMapping("/leave-history")
	public String showLeaveHistory(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
			@RequestParam(required = false) String leaveType, @RequestParam(required = false) String status,
			@RequestParam(required = false) String department, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
		  String name = accountService.getNameByUserName(auth.getName());

		model.addAttribute("name", name);
	
		model.addAttribute("email", auth.getName());
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("leaveType", leaveType);
		model.addAttribute("status", status);
		model.addAttribute("department", department);
		

	    List<String> departments = departmentService.getAllDepartmentNames();
	    model.addAttribute("departments", departments);

		Function<String, Boolean> hasAuthority = authority -> SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));


		if (hasAuthority.apply("Admin")) {
			List<LeaveApplication> leaveHistory = leaveApplicationService.searchLeaves(fromDate,toDate,leaveType,status,department);
			model.addAttribute("leaveHistory", leaveHistory);
			return "leave-history";
		}

		if (hasAuthority.apply("Employee")) {
			
			List<LeaveApplication> leaveHistory = leaveApplicationService.searchLeavesForEmployee(fromDate,toDate,leaveType,status);

		
			List<LeaveApplication> employeeLeave = leaveApplicationService.getLeaveApplicationsByUsername(auth.getName())
					.stream()
					.filter(leave -> leaveHistory != null && leaveHistory.contains(leave))
					.collect(Collectors.toList());

			model.addAttribute("leaveHistory", employeeLeave);
			return "leave-history";
		}

		if (hasAuthority.apply("HOD")) {
			
			List<LeaveApplication> leaveHistory = leaveApplicationService.searchLeavesForEmployee(fromDate,toDate,leaveType,status);

	
			String departmentList = accountService.getDepartmentByUserName(auth.getName());
			List<LeaveApplication> hodleave = leaveApplicationService.getLeaveApplicationsByDepartment(departmentList)
					.stream().filter(leaveHistory::contains).collect(Collectors.toList());

			model.addAttribute("leaveHistory", hodleave);
			return "leave-history";
		}

		return "leave-history";

	}

}
