package yu.cs.spring.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.repo.EmployeeRepo;

@Controller
public class SalariesController {

	@Autowired
	private EmployeeRepo employeeRepo;


    @GetMapping("/salaries")
    public String getLeaveBalance(Authentication authentication, Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();

		Employee employee = employeeRepo.findByAccountUsername(name);
		model.addAttribute("employee",employee);
		
		BigDecimal monthSalary = employee.getPosition().getBasicSalary();
		BigDecimal salary = employee.getMonthlySalaries(); // This method returns BigDecimal
		BigDecimal dailySalary = monthSalary.divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP); // Assuming a 30-day month for simplicity
		BigDecimal totalDeduction = dailySalary.multiply(BigDecimal.valueOf(employee.getUnpaidLeaves())).multiply(BigDecimal.valueOf(0.80));
		
		
		model.addAttribute("dailySalary",dailySalary );
		model.addAttribute("monthSalary",monthSalary );
		model.addAttribute("totalDeduction",totalDeduction);
		model.addAttribute("newSalary",salary);
		return "salaries";
    }
}
