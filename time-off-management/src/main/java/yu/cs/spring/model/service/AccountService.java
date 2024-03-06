package yu.cs.spring.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yu.cs.spring.model.entity.Account;
import yu.cs.spring.model.entity.vo.MemberVO;
import yu.cs.spring.model.form.MemberForm;
import yu.cs.spring.model.repo.AccountRepo;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;

	@Transactional
	public void save(MemberForm form) {
		form.setPassword(encoder.encode(form.getPassword()));
		repo.save(new Account(form));
	}
	
	public List<MemberVO> findAll() {
		return repo.findAllMembers().stream().map(MemberVO::new).toList();
	}

	public boolean emailAlreadyExists(String email) {
		return repo.existsByEmail(email);
	}

}
