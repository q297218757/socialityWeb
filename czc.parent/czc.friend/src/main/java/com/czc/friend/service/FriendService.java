package com.czc.friend.service;

import com.czc.friend.dao.FriendDao;
import com.czc.friend.dao.NoFriendDao;
import com.czc.friend.pojo.Friend;
import com.czc.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userId, String friendId) {
        //判断userid到friend是否有数据，有就是重复添加好友
        Friend friend  = friendDao.findByUseridAndFriendid(userId,friendId);
        if(friend != null){
            return 0;
        }
        //直接添加好友,让好友表中userid到friend方向type为0
        friend = new Friend();
        friend.setuserid(userId);
        friend.setfriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断从friend到userid是否也喜欢
        if(friendDao.findByUseridAndFriendid(friendId,userId) != null){
            //双方互相喜欢
            friendDao.updateIsLike("1",friendId,userId);
            friendDao.updateIsLike("1",userId,friendId);
        }
        return 1;
    }

    public int addNoFrient(String userId, String friend) {
        //先判断是否已经是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId,friend);
        if(noFriend != null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setuserid(userId);
        noFriend.setfriendid(friend);
        noFriendDao.save(noFriend);
        return 1;
    }


    public void deleteFriend(String userId, String friendid) {
        //删除friend表中userid到friendid这条数据
        friendDao.deleteFriend(userId,friendid);
        //更新friendid到userid数据like为0
        friendDao.updateIsLike("0",friendid,userId);
        //向非好友表添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setuserid(userId);
        noFriend.setfriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
