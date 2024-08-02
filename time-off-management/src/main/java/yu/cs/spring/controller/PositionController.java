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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk.PositionCode;
import yu.cs.spring.model.master.input.PositionFormForCreate;
import yu.cs.spring.model.master.input.PositionSearch;
import yu.cs.spring.model.master.output.PositionInfo;
import yu.cs.spring.model.master.service.DepartmentService;
import yu.cs.spring.model.master.service.PositionService;

@Controller
public class PositionController {

	@Autowired
	private PositionService positionService;

	@Autowired
	private DepartmentService deService;

	@GetMapping("/positions")
	public String search(@RequestParam(required = false) String department,
			@RequestParam(required = false) String position, Model model) {
		
		PositionSearch search = new PositionSearch(department, position);
		List<PositionInfo> results = positionService.search(search);
		model.addAttribute("search", search);
		model.addAttribute("positions", results);
	
		return "position-list";
	}

	@GetMapping("/positions/new")
	public String showNewPositionForm(Model model) {
		List<Department> departments = deService.findAll();
		model.addAttribute("departments", departments);

		model.addAttribute("positionCode", Arrays.asList(PositionCode.values()));

		model.addAttribute("positionForm", new PositionFormForCreate("", "", null, null, null));
		return "create-position";
	}

	@GetMapping("/positions/codes")
	@ResponseBody
	public List<String> getPositionCodesByDepartment(@RequestParam("department") String department) {
		
		return positionService.getPositionCodesByDepartment(department);
	}

	@PostMapping("/positions")
	public String savePosition(@Valid @ModelAttribute("positionForm") PositionFormForCreate form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "new-position-form";
		}
		Position entity = form.entity();
		positionService.save(entity);
		return "redirect:/positions";
	}

}
