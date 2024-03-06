package yu.cs.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import yu.cs.spring.model.entity.vo.MemberVO;
import yu.cs.spring.model.form.MemberForm;
import yu.cs.spring.model.service.AccountService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private AccountService service;
	
	@GetMapping
	String index(ModelMap model) {
		
		var memberList = service.findAll();
	
		model.put("list", memberList);

		return "member";
	}
	
	@GetMapping("edit")
	String edit(){
		return "member-edit";
	}
	
	@PostMapping
	String save(@Valid @ModelAttribute(name = "form") MemberForm form, BindingResult result){
		
		if (service.emailAlreadyExists(form.getEmail())) {
	        result.rejectValue("email", "error.email", "Email already exists");
	    }
		
		if(result.hasErrors()) {
			return "member-edit";
		}
		service.save(form);
		return "redirect:/member";
	}
	
	
	@ModelAttribute(name="form")
	MemberForm form(){
		return new MemberForm();
	}
}
