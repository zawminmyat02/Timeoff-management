package yu.cs.spring.model.master.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yu.cs.spring.model.BaseRepository;
import yu.cs.spring.model.master.entity.LeaveApplication;
import yu.cs.spring.model.master.entity.LeaveApplication.Status;

public interface LeaveApplicationRepo extends BaseRepository<LeaveApplication, Long> {

	@Query("SELECT la FROM LeaveApplication la " +
	           "JOIN la.employee e " +
	           "JOIN e.account a " +
	           "WHERE a.username = :username")
	    List<LeaveApplication> findByEmployeeAccountUsername(@Param("username") String username);

	List<LeaveApplication> findByDepartment_Name(String departmentName);

	List<LeaveApplication> findByStatusIn(List<Status> asList);

	List<LeaveApplication> findByStatus(Status pending);
    
    @Query("SELECT la FROM LeaveApplication la WHERE la.employee.code = :employeeCode AND la.status = 'APPROVED'")
    List<LeaveApplication> findApprovedLeaveApplicationsByEmployeeCode(String employeeCode);

    @Query("SELECT la FROM LeaveApplication la WHERE la.employee.code = :employeeCode AND la.id= :id AND la.status = 'APPROVED'")
	List<LeaveApplication> findApprovedLeaveApplicationsByEmployeeCodeAndLeaveApplicationId(@Param("employeeCode") String employeeCode, @Param("id") long id );
    
    @Query("SELECT la FROM LeaveApplication la WHERE la.startDate <= :endDate AND la.endDate >= :startDate AND la.employee.code = :employeeCode AND la.status != 'REJECTED'")
    List<LeaveApplication> findOverlappingLeaves(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,@Param("employeeCode") String employeeCode);

}
