package com.czc.friend.dao;

import com.czc.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String  > {
    public Friend findByUseridAndFriendid(String userId,String friendId);

    @Modifying
    @Query(value = "UPDATE tb_friend set islike = ?1 where userid = ?2 and friendid =?3",nativeQuery = true)
    public void updateIsLike(String isLike,String userId,String friendId);

    @Modifying
    @Query(value = "delete from  tb_friend where userId = ?1 and friendid = ?2",nativeQuery = true)
    public void deleteFriend(String userId,String friendid);
}

