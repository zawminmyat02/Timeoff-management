package yu.cs.spring.model.master.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.master.repo.EmployeeRepo;
import yu.cs.spring.model.transaction.repo.LeaveApplicationRepo;

@Service
public class LeaveBalanceService {
	

    @Autowired
    private EmployeeRepo employeeRepo;

    
    public Map<String, Integer> getLeaveBalances(String employeeCode) {
    	
    	 // Fetch initial leave balances
        List<Object[]> leaveBalances = employeeRepo.findLeaveBalancesByEmployeeCode(employeeCode);
        int sickLeaves = (int) leaveBalances.get(0)[0];
        int casualLeaves = (int) leaveBalances.get(0)[1];
        int maternityLeaves = (int) leaveBalances.get(0)[2];        
        int unpaidLeaves = (int) leaveBalances.get(0)[3];


       
        // Create a map to return the results
        Map<String, Integer> leaveBalanceMap = new HashMap<>();
        leaveBalanceMap.put("Sick Leaves", sickLeaves);
        leaveBalanceMap.put("Casual Leaves", casualLeaves);
        leaveBalanceMap.put("Maternity Leaves", maternityLeaves);
        leaveBalanceMap.put("Unpaid", unpaidLeaves);


        return leaveBalanceMap;
        
    }
}
