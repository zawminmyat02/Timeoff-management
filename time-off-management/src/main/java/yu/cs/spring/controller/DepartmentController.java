package yu.cs.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.input.DepartmentFormForCreate;
import yu.cs.spring.model.master.input.DepartmentFormForManagerChanges;
import yu.cs.spring.model.master.output.DepartmentInfo;
import yu.cs.spring.model.master.output.EmployeeInfo;
import yu.cs.spring.model.master.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments/new")
    public String showCreateDepartmentForm(Model model) {
        model.addAttribute("departmentForm", new DepartmentFormForCreate("", "", ""));
        return "create-department";
    }

    @PostMapping("/departments")
    public String createDepartment( @ModelAttribute("departmentForm")  @Valid DepartmentFormForCreate departmentForm, BindingResult result) {
        if (result.hasErrors()) {
            return "create-department";
        }
        Department department = departmentForm.entity();
        departmentService.saveDepartment(department);
        return "redirect:/departments";
    }

    @GetMapping("/departments")
    public String showDepartmentList(Model model) {
        List<Department> departmentList = departmentService.findAll();
        List<DepartmentInfo> departmentInfoList = departmentList.stream()
                .map(DepartmentInfo::from)
                .collect(Collectors.toList());
        model.addAttribute("departments", departmentInfoList);
        
        return "department-list";
    }
    
    @PostMapping("/update-manager")
    public String updateManager(@RequestParam("departmentCode") String departmentCode,
                                DepartmentFormForManagerChanges form,
                                BindingResult bindingResult,
                                Model model) {
      
    	departmentService.update(departmentCode, form);
       
        return "redirect:/departments"; // Replace with the appropriate page
    }
    
    @GetMapping("/find")
	public String search(@RequestParam(value = "query", required = false) String query, Model model) {
		List<Department> departments = departmentService.searchByDepartmentCode(query);
		
		List<DepartmentInfo> departmentInfoList = departments.stream().map(DepartmentInfo::from)
				.collect(Collectors.toList());
		model.addAttribute("departments", departmentInfoList);
		return "department-list";
	}
	
    
    
}
