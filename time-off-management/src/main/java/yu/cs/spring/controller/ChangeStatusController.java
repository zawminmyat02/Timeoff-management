package yu.cs.spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yu.cs.spring.model.master.service.LeaveApplicationService;

@RestController
@RequestMapping("/changeStatus")
public class ChangeStatusController {

	 @Autowired
	    private LeaveApplicationService leaveApplicationService;

	    @PutMapping("/{id}")
	    public ResponseEntity<Map<String, Object>> changeStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
	        String newStatus = payload.get("status");
	        boolean success = leaveApplicationService.updateStatus(id, newStatus);
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", success);
	        return ResponseEntity.ok(response);
	    }
}
