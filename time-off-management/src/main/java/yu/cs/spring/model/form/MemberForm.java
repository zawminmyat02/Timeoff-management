package yu.cs.spring.model.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class MemberForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@NotBlank(message = "Enter Member Name.")
	private String name;
	@NotBlank(message = "Enter Email.")
	private String email;
	@NotBlank(message = "Enter Password.")
	private String password;
	@NotBlank(message = "Enter Phone.")
	private String phone;
	@NotBlank(message = "Enter Phone.")
	private int salary;	
	
	public MemberForm() {
		// TODO Auto-generated constructor stub
	}
	
	public MemberForm(String name,String email,String phone,int salary) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.salary=salary;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String loginId) {
		this.email = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "SignUpForm [name=" + name + ", loginId=" + email + ", password=" + password + "]";
	}

	
	

}
