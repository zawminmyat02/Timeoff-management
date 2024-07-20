package yu.cs.spring.model.master.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EmployeeCodeSeq {

	@Id
	private String department;
	private int seqNumber;
	
	public String next() {
		return "%s-%04d".formatted(department, ++ seqNumber);
	}
	
	public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }
}
