package yu.cs.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import yu.cs.spring.model.master.input.LeaveApplicationForm;
import yu.cs.spring.model.master.service.EmployeeService;
import yu.cs.spring.model.master.service.LeaveApplicationService;
import yu.cs.spring.model.master.service.LeaveTypeService;

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
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	var employee = employeeService.getEmployeeByUsername(auth.getName());
        model.addAttribute("leaveTypes", leaveTypeService.findAll());
        model.addAttribute("leaveApplicationForm", new LeaveApplicationForm(null, null, null, ""));
        model.addAttribute("employeeCode",employee.getCode());
        return "leave-application";
    }

    @PostMapping("/submit")
    public String submitLeaveApplication(@ModelAttribute LeaveApplicationForm leaveApplicationForm,Model model) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	var employee = employeeService.getEmployeeByUsername(auth.getName());
        model.addAttribute("employeeCode",employee.getCode());

        leaveApplicationService.createLeaveApplication(leaveApplicationForm, employee);
        return "redirect:/home";
    }
    

    
  
}
