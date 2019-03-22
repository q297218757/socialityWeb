package czc.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import czc.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
	@Query(value = "SELECT * FROM tb_problem ,tb_pl WHERE id = problemid AND  labelid = ?1 order by replytime desc ",nativeQuery = true )
    public Page<Problem> newlist(String labelid, Pageable pageable);
    @Query(value = "SELECT * FROM tb_problem ,tb_pl WHERE id = problemid AND  labelid = ?1 order by reply desc ",nativeQuery = true )
    public Page<Problem> hotlist(String labelid, Pageable pageable);
    @Query(value = "SELECT * FROM tb_problem ,tb_pl WHERE id = problemid AND  labelid = ?1 AND reply = 0 order by reply desc ",nativeQuery = true )
    public Page<Problem> waitlist(String labelid, Pageable pageable);


}
