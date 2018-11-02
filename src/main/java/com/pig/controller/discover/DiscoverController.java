package com.pig.controller.discover;

import com.pig.dao.pojo.User;
import com.pig.service.addFriendRecord.AddFriendRecordService;
import com.pig.service.friend.FriendService;
import com.pig.service.user.UserService;
import com.pig.utils.ResponseCode;
import com.pig.utils.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@RequestMapping(value = "/discover/")
@Controller
@Api("发现接口")
public class DiscoverController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private AddFriendRecordService addFriendRecordService;

    @ApiOperation(value = "搜索朋友", notes = "搜索朋友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myId", value = "我的id", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "friendName", value = "朋友的名称", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("searchFriend")
    @ResponseBody
    public ResponseObj searchFriend(Integer myId,
                                    String friendName) {
        // 1.查找是否存在该好友
        User friend = userService.findByUserName(friendName);
        if (ObjectUtils.isEmpty(friend)) {
            return ResponseObj.createBy(ResponseCode.ERROR, "查无此人");
        }
        // 2.查看是否与自己是同一人
        if (myId.equals(friend.getId())) {
            return ResponseObj.createBy(ResponseCode.ERROR, "查到的是自己啊傻逼");
        }
        // 3.查看是否已经是朋友
        if (!ObjectUtils.isEmpty(friendService.findByFriendId(friend.getId()))) {
            return ResponseObj.createBy(ResponseCode.ERROR, "该用户已经是你的好友了");
        }
        return ResponseObj.createBy(ResponseCode.SUCCESS, friend);
    }


    @ApiOperation(value = "添加好友", notes = "添加好友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myId", value = "我的id", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "friendName", value = "朋友的名称", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "note", value = "留言", paramType = "query", required = false, dataType = "String")
    })
    @PostMapping("addFriend")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseObj addFriend(Integer myId,
                                 String friendName,
                                 String note) {
        // 1.查找是否存在该好友
        User friend = userService.findByUserName(friendName);
        if (ObjectUtils.isEmpty(friend)) {
            return ResponseObj.createBy(ResponseCode.ERROR, "查无此人");
        }
        // 2.查看是否与自己是同一人
        if (myId.equals(friend.getId())) {
            return ResponseObj.createBy(ResponseCode.ERROR, "查到的是自己啊傻逼");
        }
        // 3.查看是否已经是朋友
        if (!ObjectUtils.isEmpty(friendService.findByFriendId(friend.getId()))) {
            return ResponseObj.createBy(ResponseCode.ERROR, "该用户已经是你的好友了");
        }
        // 4.发送添加好友请求
        if (addFriendRecordService.addFriendRequest(myId, friend.getId(), note)) {
            return ResponseObj.createBy(ResponseCode.SUCCESS, "发送好友请求成功");
        }
        return ResponseObj.createBy(ResponseCode.ERROR, "发送好友请求失败");
    }


}
