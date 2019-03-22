package com.czc.friend.pojo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_nofriend")
@Entity
@IdClass(NoFriend.class)
public class  NoFriend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;


    public NoFriend(String userid, String friendid) {
        this.userid = userid;
        this.friendid = friendid;
    }

    public NoFriend() {
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

}
