package yu.cs.spring.model.transaction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yu.cs.spring.model.BaseRepository;
import yu.cs.spring.model.transaction.entity.LeaveApplication;
import yu.cs.spring.model.transaction.entity.LeaveApplication.Status;

public interface LeaveApplicationRepo extends BaseRepository<LeaveApplication, Long> {

	@Query("SELECT la FROM LeaveApplication la " +
	           "JOIN la.employee e " +
	           "JOIN e.account a " +
	           "WHERE a.username = :username")
	    List<LeaveApplication> findByEmployeeAccountUsername(@Param("username") String username);

	List<LeaveApplication> findByDepartment_Name(String departmentName);

	List<LeaveApplication> findByStatusIn(List<Status> asList);

	List<LeaveApplication> findByStatus(Status pending);
}
