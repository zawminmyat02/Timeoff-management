package yu.cs.spring.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import yu.cs.spring.model.form.MemberForm;

@Entity
public class Account implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true )
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String phone;
	@Column(nullable = false)
	private int salary;
	@Column(nullable = false)
	private Role role;
	
	public Account() {
	}
	
	public Account(MemberForm form) {
		this.name= form.getName();
		this.email= form.getEmail();
		this.password= form.getPassword();
		this.role= Role.Member;
		this.phone= form.getPhone();
		this.salary= form.getSalary();
	}
	
	public enum Role {
		Admin, Member
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
	
}
