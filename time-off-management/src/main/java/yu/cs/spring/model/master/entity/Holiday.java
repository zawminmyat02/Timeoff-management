package yu.cs.spring.model.master.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Holiday{
	
	@Id
	@Column(name = "holiday_date")
	private LocalDate date;
	
	@Column(nullable = false)
	private Type type;
	
	@Column(nullable = false)
	private String holiday;
	
	private String remark;
	
	public enum Type {
		Weekend, Gazetted, Special;
		
		public String getDesplayName() {
			return "%s Holiday".formatted(name());
		}
	}
}
