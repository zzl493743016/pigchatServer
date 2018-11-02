package com.pig.controller.chatList;

import com.pig.dao.pojo.AddFriendRecord;
import com.pig.service.addFriendRecord.AddFriendRecordService;
import com.pig.service.friend.FriendService;
import com.pig.utils.ResponseCode;
import com.pig.utils.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@RequestMapping(value = "/chatList/")
@Controller
@Api("聊天列表接口")
public class ChatListController {


    @Autowired
    private AddFriendRecordService addFriendRecordService;
    @Autowired
    private FriendService friendService;

    @ApiOperation(value = "拉取好友请求", notes = "拉取好友请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myId", value = "我的id", paramType = "query", required = true, dataType = "Integer"),
    })
    @GetMapping("getFriendRequest")
    @ResponseBody
    public ResponseObj getFriendRequest(Integer myId) {
        List<AddFriendRecord> addFriendRecords = addFriendRecordService.findAddFriendRecords(myId);
        return ResponseObj.createBy(ResponseCode.SUCCESS, addFriendRecords);
    }


    @ApiOperation(value = "通过好友请求", notes = "通过好友请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myId", value = "我的id", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "friendId", value = "朋友的id", paramType = "query", required = true, dataType = "Integer")
    })
    @PostMapping("submitFriendRequest")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseObj submitFriendRequest(Integer myId,
                                           Integer friendId) {
        // 0.删除好友请求记录
        addFriendRecordService.removeRecord(myId, friendId);
        // 1.检查双方是否已经是好友
        if (friendService.checkIfFriends(myId, friendId)) {
            return ResponseObj.createBy(ResponseCode.ERROR, "已经是朋友");
        }
        // 2.添加好友关系
        if (friendService.addFriend(myId, friendId)) {
            return ResponseObj.createBy(ResponseCode.SUCCESS);
        }
        return ResponseObj.createBy(ResponseCode.ERROR, "添加好友失败");
    }

}
