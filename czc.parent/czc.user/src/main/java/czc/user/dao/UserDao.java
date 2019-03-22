package czc.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import czc.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
	public User findByMobile(String Mobile);

	@Modifying
	@Query(value = "update tb_user set fanscount = fanscount+?1 where id =?2",nativeQuery = true)
    void updateFanscount(int x, String userid);

    @Modifying
    @Query(value = "update tb_user set followcount = followcount+?1 where id =?2",nativeQuery = true)
    void updateFollowcount(int x, String friendid);
}
