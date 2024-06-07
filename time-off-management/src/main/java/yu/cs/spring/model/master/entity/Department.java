package yu.cs.spring.model.master.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Department {

	@Id
	private String code;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	private String description;
	
	@OneToOne
	private Employee headOfDepartment;
	
	@OneToMany(mappedBy = "department")
	private List<Employee> employees;
	
	@OneToMany(mappedBy = "department")
	private List<Position> positions;
}
