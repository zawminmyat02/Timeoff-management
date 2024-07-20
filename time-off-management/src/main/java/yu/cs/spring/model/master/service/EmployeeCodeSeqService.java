package yu.cs.spring.model.master.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.EmployeeCodeSeq;
import yu.cs.spring.model.master.repo.EmployeeCodeSeqRepo;
import yu.cs.spring.model.master.repo.EmployeeRepo;

public class EmployeeCodeSeqService {


    @Autowired
    private EmployeeCodeSeqRepo employeeCodeSeqRepository;

    @Autowired
    private EmployeeRepo employeeRepository; // Assuming you have an EmployeeRepository

    @PostConstruct
    public void initSequences() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            String[] parts = employee.getCode().split("-");
            String department = parts[0];
            int seqNumber = Integer.parseInt(parts[1]);

            EmployeeCodeSeq seq = employeeCodeSeqRepository.findById(department).orElse(new EmployeeCodeSeq());
            seq.setDepartment(department);

            if (seqNumber > seq.getSeqNumber()) {
                seq.setSeqNumber(seqNumber);
            }

            employeeCodeSeqRepository.save(seq);
        }
    }
}

