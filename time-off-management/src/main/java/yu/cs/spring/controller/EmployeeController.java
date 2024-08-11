package yu.cs.spring.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee.Gender;
import yu.cs.spring.model.master.entity.Employee.Status;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk.PositionCode;
import yu.cs.spring.model.master.input.EmployeeFormForCreate;
import yu.cs.spring.model.master.input.EmployeeFormForUpdate;
import yu.cs.spring.model.master.output.EmployeeInfo;
import yu.cs.spring.model.master.service.AccountService;
import yu.cs.spring.model.master.service.DepartmentService;
import yu.cs.spring.model.master.service.EmployeeService;
import yu.cs.spring.model.master.service.LeaveApplicationService;
import yu.cs.spring.model.master.service.LeaveBalanceService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService deService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private LeaveBalanceService leaveService;

	@GetMapping("/employees/new")
	public String showCreateEmployeeForm(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		model.addAttribute("employeeForm",
				new EmployeeFormForCreate("", "", null, "", "", null, null, null, null, "", null));
		model.addAttribute("genders", Arrays.asList(Gender.values()));
		model.addAttribute("statuses", Arrays.asList(Status.values()));

		// Check if the logged-in user has the HOD role
		boolean isHod = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("HOD"));

		List<Department> departments;
		List<PositionCode> positionCodes = new ArrayList<>();

		if (isHod) {
			// Get the department of the logged-in user
			String userDepartment = accountService.getDepartmentByUserName(username);
			Department department = deService.findByName(userDepartment);
			departments = Arrays.asList(department);

			// Get positions for the department
			List<Position> positions = department.getPositions();
			for (Position position : positions) {
				positionCodes.add(position.getId().getPositionCode());
			}
		} else {
			// Get all departments and positions for non-HOD users
			departments = deService.findAll();
			for (Department department : departments) {
				List<Position> positions = department.getPositions();
				for (Position position : positions) {
					positionCodes.add(position.getId().getPositionCode());
				}
			}
		}

		model.addAttribute("departments", departments);
		model.addAttribute("positionCode", positionCodes);

		return "create-employee";
	}

	@PostMapping("/employees")
	public String createEmployee(@Valid @ModelAttribute("employeeForm") EmployeeFormForCreate employeeForm,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("genders", employeeForm.gender());
			model.addAttribute("statuses", employeeForm.status());
			model.addAttribute("name", employeeForm.name());
			model.addAttribute("phone", employeeForm.phone());
			model.addAttribute("remark", employeeForm.remark());

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();

			// Check if the logged-in user has the HOD role
			boolean isHod = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("HOD"));

			List<Department> departments;
			List<PositionCode> positionCodes = new ArrayList<>();

			if (isHod) {
				// Get the department of the logged-in user
				String userDepartment = accountService.getDepartmentByUserName(username);
				Department department = deService.findByName(userDepartment);
				departments = Arrays.asList(department);

				// Get positions for the department
				List<Position> positions = department.getPositions();
				for (Position position : positions) {
					positionCodes.add(position.getId().getPositionCode());
				}
			} else {
				// Get all departments and positions for non-HOD users
				departments = deService.findAll();
				for (Department department : departments) {
					List<Position> positions = department.getPositions();
					for (Position position : positions) {
						positionCodes.add(position.getId().getPositionCode());
					}
				}
			}

			model.addAttribute("departments", departments);
			model.addAttribute("positionCode", positionCodes);
			return "create-employee";
		}

		employeeService.saveEmployee(employeeForm);
		return "redirect:/employees";
	}

	@GetMapping("/employees")
	public String showEmployeeList(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		model.addAttribute("name", username);

		List<Employee> employeeList;
		if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("HOD"))) {
			String department = accountService.getDepartmentByUserName(username);
			employeeList = employeeService.findByDepartment(department);
		} else {
			employeeList = employeeService.findAll();
		}

		List<EmployeeInfo> employeeInfoList = employeeList.stream().map(EmployeeInfo::from)
				.collect(Collectors.toList());
		model.addAttribute("employees", employeeInfoList);
		return "employee-list";
	}

	@PostMapping("/delete")
	public String deleteEmployees(@RequestParam("employeeIds") String employeeIds, Principal principal,
			RedirectAttributes redirectAttributes) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = accountService.getNameByUserName(auth.getName());

		redirectAttributes.addAttribute("name", name);
		List<String> ids = Arrays.asList(employeeIds.split(","));
		String loggedInUserId = principal.getName();

		List<String> errors = new ArrayList<>();
		for (String id : ids) {
			if (!id.equals(loggedInUserId)) { // Ensure the HOD's own ID is excluded
				try {
					employeeService.deleteById(id);
				} catch (DataIntegrityViolationException e) {
					// Handle the foreign key constraint violation
					errors.add("Cannot delete employee with ID: " + id + " due to HOD employment.");

				}
			} else {
				errors.add("Cannot delete your own record.");

			}
		}

		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", errors);
		}

		return "redirect:/employees";
	}

	@GetMapping("/employees/edit/{id}")
	public String showEditEmployeeForm(@PathVariable("id") String id, Model model) {
		Employee employee = employeeService.findById(id); // Assuming findById now uses the string ID
		EmployeeFormForUpdate updateForm = EmployeeFormForUpdate.from(employee);

		model.addAttribute("employeeForm", updateForm);

		model.addAttribute("status", updateForm.status());
		model.addAttribute("statuses", Arrays.asList(Status.values()));

		return "update-employee";
	}

	@PostMapping("/employees/{id}")
	public String updateEmployee(@PathVariable("id") String id,
			@Valid @ModelAttribute("employeeForm") EmployeeFormForUpdate employeeForm, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "update-employee";
		}

		employeeService.updateEmployee(id, employeeForm);
		return "redirect:/employees";
	}

	@GetMapping("/search")
	public String search(@RequestParam(value = "query", required = false) String query, Model model) {
		List<Employee> employees = employeeService.searchByEmployeeCode(query);
		
		List<EmployeeInfo> employeeInfoList = employees.stream().map(EmployeeInfo::from)
				.collect(Collectors.toList());
		model.addAttribute("employees", employeeInfoList);
		return "employee-list";
	}
	
	@GetMapping("/salary")
	public String getEmployeeSalaries(@RequestParam String employeeCode, Model model) {
	    // Fetch employee data based on employeeCode
	    Employee employee = employeeService.findById(employeeCode);
	    model.addAttribute("employee", employee);
	    

		BigDecimal monthSalary = employee.getPosition().getBasicSalary();
		BigDecimal salary = employee.getMonthlySalaries(); // This method returns BigDecimal
		BigDecimal dailySalary = monthSalary.divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP); // Assuming a 30-day month for simplicity
		BigDecimal totalDeduction = dailySalary.multiply(BigDecimal.valueOf(employee.getUnpaidLeaves())).multiply(BigDecimal.valueOf(0.80));
		 Map<String, Integer> leaveCounts = leaveService.getLeaveBalances(employee.getCode());
	     model.addAttribute("leaveCounts", leaveCounts);
	     model.addAttribute("remainSickLeave",leaveCounts.get("Sick Leaves"));
	     model.addAttribute("remainCasualLeave",leaveCounts.get("Casual Leaves"));
	     model.addAttribute("remainMaternityLeave",leaveCounts.get("Maternity Leaves"));
	     model.addAttribute("unpaid",leaveCounts.get("Unpaid"));
	     
	     if(employee.getGender()== Gender.Male) {
	    	 model.addAttribute("totalMaternity",60);
	     }else {
	    	 model.addAttribute("totalMaternity",90);

	     }

		
		model.addAttribute("dailySalary",dailySalary );
		model.addAttribute("monthSalary",monthSalary );
		model.addAttribute("totalDeduction",totalDeduction);
		model.addAttribute("newSalary",salary);
	    // Add other required attributes for the view
	    return "each-salaries"; // Thymeleaf template name for salary details
	}

	
	

}
