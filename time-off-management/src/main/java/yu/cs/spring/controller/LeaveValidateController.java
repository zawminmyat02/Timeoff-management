package yu.cs.spring.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yu.cs.spring.model.master.service.LeaveApplicationService;

@RestController
@RequestMapping("/checkOverlap")
public class LeaveValidateController {

	@Autowired
	private LeaveApplicationService leaveApplicationService;

	@PostMapping
	@ResponseBody
	public Map<String, Boolean> checkDateOverlap(@RequestBody Map<String, String> request) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			String startDateStr = request.get("startDate");
	        String endDateStr = request.get("endDate");
	        String employeeCode = request.get("employeeCode");

	        LocalDate startDate = LocalDate.parse(startDateStr);
	        LocalDate endDate = LocalDate.parse(endDateStr);
			boolean isOverlap = leaveApplicationService.checkDateOverlap(startDate, endDate,employeeCode);
			response.put("isOverlap", isOverlap);
		} catch (DateTimeParseException e) {
			response.put("error", true);
		}
		return response;
	}

}
