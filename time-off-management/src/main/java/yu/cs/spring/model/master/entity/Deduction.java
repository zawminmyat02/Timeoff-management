package yu.cs.spring.model.master.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import yu.cs.spring.model.master.CalculationType;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Deduction{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private CalculationType type;
	
	private String description;

}
