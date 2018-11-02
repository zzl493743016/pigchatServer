package com.pig.controller.contact;

import com.pig.dao.pojo.User;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@RequestMapping(value = "/discover/")
@Controller
@Api("通讯录接口")
public class ContactController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;

    @ApiOperation(value = "获取好友列表", notes = "获取好友列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myId", value = "我的id", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "起始页", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "页大小", paramType = "query", required = false, dataType = "Integer")
    })
    @GetMapping("getFriendList")
    @ResponseBody
    public ResponseObj getFriendList(Integer myId,
                                     Integer page,
                                     Integer size) {
        page = ObjectUtils.isEmpty(page) ? 0 : page;
        size = ObjectUtils.isEmpty(size) ? 10 : size;
        List<Integer> friendIdList = friendService.getFriendIdList(myId, page, size);
        if (ObjectUtils.isEmpty(friendIdList)) {
            return ResponseObj.createBy(ResponseCode.SUCCESS);
        } else {
            List<User> friends = userService.getUsersByIds(friendIdList);
            if (ObjectUtils.isEmpty(friends)) {
                return ResponseObj.createBy(ResponseCode.SUCCESS);
            } else {
                return ResponseObj.createBy(ResponseCode.SUCCESS, friends);
            }
        }
    }
}
