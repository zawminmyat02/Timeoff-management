package yu.cs.spring.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.repo.EmployeeRepo;
import yu.cs.spring.model.master.service.AccountService;
import yu.cs.spring.model.master.service.LeaveBalanceService;

@Controller
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveService;
    

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private EmployeeRepo employeeRepo;


    @GetMapping("/leave-balance")
    public String getLeaveBalance(Authentication authentication, Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();

		Employee employee = employeeRepo.findByAccountUsername(name);
        Map<String, Integer> leaveCounts = leaveService.getLeaveBalances(employee.getCode());
        model.addAttribute("leaveCounts", leaveCounts);
        return "leave-balance";
    }
 
    
}