package com.czc.friend.pojo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_friend")
@Entity
@IdClass(Friend.class)
public class Friend  implements Serializable {
    @Id
    private String userid;
    @Id
    private String  friendid;

    private String islike;

    public Friend(String userid, String friendid, String islike) {
        this.userid = userid;
        this.friendid = friendid;
        this.islike = islike;
    }

    public Friend() {
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid) {
        this.userid = userid;
    }

    public String getfriendid() {
        return friendid;
    }

    public void setfriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }
}
