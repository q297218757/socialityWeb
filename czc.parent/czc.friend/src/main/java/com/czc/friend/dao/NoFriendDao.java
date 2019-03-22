package com.czc.friend.dao;

import com.czc.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {
    public NoFriend findByUseridAndFriendid(String userId,String friendId);
}
