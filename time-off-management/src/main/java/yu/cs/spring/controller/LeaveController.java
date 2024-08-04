package yu.cs.spring.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yu.cs.spring.model.master.input.LeaveApplicationForm;
import yu.cs.spring.model.master.input.LeaveApplicationSearch;
import yu.cs.spring.model.master.service.EmployeeService;
import yu.cs.spring.model.master.service.LeaveApplicationService;
import yu.cs.spring.model.master.service.LeaveTypeService;
import yu.cs.spring.model.transaction.entity.LeaveApplication;

@Controller
@RequestMapping("/leave-application")
public class LeaveController {

	@Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private LeaveApplicationService leaveApplicationService;
    
    @Autowired 
    private EmployeeService employeeService;

    @GetMapping
    public String showLeaveApplicationForm(Model model) {
        model.addAttribute("leaveTypes", leaveTypeService.findAll());
        model.addAttribute("leaveApplicationForm", new LeaveApplicationForm(null, null, null, ""));
        return "leave-application";
    }

    @PostMapping("/submit")
    public String submitLeaveApplication(@ModelAttribute LeaveApplicationForm leaveApplicationForm) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	var employee = employeeService.getEmployeeByUsername(auth.getName());
        leaveApplicationService.createLeaveApplication(leaveApplicationForm, employee);
        return "redirect:/home";
    }
    
  
}
