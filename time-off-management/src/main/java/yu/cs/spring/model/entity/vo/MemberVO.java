package yu.cs.spring.model.entity.vo;

import java.util.Objects;

import yu.cs.spring.model.entity.Account;

public class MemberVO {

	private int id;
	private String name;
	private String email;
	private String phone;
	private int salary;
	
	public MemberVO() {
	}
	
	public MemberVO(Account account) {
		
		this.id = account.getId();
		this.name = account.getName();
		this.email = account.getEmail();
		this.phone=account.getPhone();
		this.salary = account.getSalary();
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
	public String getLoginId() {
		return phone;
	}
	public void setLoginId(String loginId) {
		this.phone = loginId;
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

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, phone);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVO other = (MemberVO) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone);
	}
	
	
	
	
	
	
}
