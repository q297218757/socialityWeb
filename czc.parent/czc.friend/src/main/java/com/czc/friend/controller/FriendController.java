package com.czc.friend.controller;

import com.czc.friend.client.UserClient;
import com.czc.friend.dao.NoFriendDao;
import com.czc.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserClient userClient;

    @RequestMapping(value = "/like/{friend}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friend,@PathVariable String type){
        //验证是否登录，并拿到当前登录用户的id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims ==null ){
            return new Result(false, StatusCode.LOGINERROR,"权限登录");
        }
        String userId = claims.getId();
        //判断是添加好友还是添加非好友
        if(type != null){
            if("1".equals(type)){
                //添加好友
                int flag = friendService.addFriend(userId,friend);
                if(flag == 0){
                    return new Result(false, StatusCode.ERROR,"不能重复添加好友");
                }
                userClient.updatefriend(userId,friend,1);
                return new Result(true, StatusCode.OK,"添加成功");
            }else if("2".equals(type)){
                //添加非好友
                int flag = friendService.addNoFrient(userId,friend);
                if(flag == 0){
                    return new Result(false, StatusCode.ERROR,"不能重复添加非好友");
                }
                return new Result(true, StatusCode.OK,"添加成功");
            }
            return new Result(false, StatusCode.ERROR,"参数异常");
        }else {
            return new Result(false, StatusCode.ERROR,"参数异常");
        }
    }

    public Result deleteFriend(@PathVariable String friendid){
        //验证是否登录，并拿到当前登录用户的id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims ==null ){
            return new Result(false, StatusCode.LOGINERROR,"权限登录");
        }
        String userId = claims.getId();
        friendService.deleteFriend(userId,friendid);
        userClient.updatefriend(userId,friendid,-1);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
