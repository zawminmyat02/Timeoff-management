package yu.cs.spring.controller;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee.Gender;
import yu.cs.spring.model.master.entity.Employee.Status;
import yu.cs.spring.model.master.entity.PositionPk.PositionCode;
import yu.cs.spring.model.master.input.EmployeeFormForCreate;
import yu.cs.spring.model.master.output.EmployeeInfo;
import yu.cs.spring.model.master.service.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/new")
    public String showCreateEmployeeForm(Model model) {
    	 model.addAttribute("employeeForm", new EmployeeFormForCreate(
                 "", "", null, "", "", null, null, null, null, ""));       
    	model.addAttribute("positions", Arrays.asList(PositionCode.values()));
        model.addAttribute("genders", Arrays.asList(Gender.values()));
        model.addAttribute("statuses", Arrays.asList(Status.values()));
        return "create-employee";
    }

    @PostMapping("/employees")
    public String createEmployee(@Valid @ModelAttribute("employeeForm") EmployeeFormForCreate employeeForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("positions", Arrays.asList(PositionCode.values()));
            model.addAttribute("genders", Arrays.asList(Gender.values()));
            model.addAttribute("statuses", Arrays.asList(Status.values()));
            return "create-employee";
        }

        employeeService.saveEmployee(employeeForm);
        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String showEmployeeList(Model model) {
        List<Employee> employeeList = employeeService.findAll();
        List<EmployeeInfo> employeeInfoList = employeeList.stream()
                .map(EmployeeInfo::from)
                .collect(Collectors.toList());
        model.addAttribute("employees", employeeInfoList);
        return "employee-list";
    }
}
