package com.pig.controller.chatList;

import com.pig.dao.pojo.AddFriendRecord;
import com.pig.service.addFriendRecord.AddFriendRecordService;
import com.pig.utils.ResponseCode;
import com.pig.utils.ResponseObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
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

    @ApiOperation(value = "拉取好友请求", notes = "拉取好友请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myId", value = "我的id", paramType = "query", required = true, dataType = "Integer"),
    })
    @GetMapping("searchFriend")
    @ResponseBody
    public ResponseObj searchFriend(Integer myId) {
        List<AddFriendRecord> addFriendRecords = addFriendRecordService.findAddFriendRecords(myId);
        return ResponseObj.createBy(ResponseCode.SUCCESS, addFriendRecords);
    }
}
