package com.pig.controller.user;

import com.pig.dao.pojo.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@RequestMapping(value = "/user/")
@Controller
@Api("用户相关接口")
public class UserController {


    @Autowired
    private UserService userService;


    @ApiOperation(value = "注册接口", notes = "注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping(value = "register")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseObj register(String userName,
                                String password) {
        // 1.查看名称是否已经被注册
        if (!ObjectUtils.isEmpty(userService.findByUserName(userName))) {
            return ResponseObj.createBy(ResponseCode.ERROR, "用户已经被注册");
        }
        // 2.将数据插入
        if (userService.addUser(userName, password)) {
            return ResponseObj.createBy(ResponseCode.SUCCESS, "注册成功");
        }
        return ResponseObj.createBy(ResponseCode.ERROR, "注册失败");
    }

    @ApiOperation(value = "登陆接口", notes = "登陆接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping(value = "login")
    @ResponseBody
    public ResponseObj login(String userName,
                             String password) {
        // 1.查看用户是否存在
        if (ObjectUtils.isEmpty(userService.findByUserName(userName))) {
            return ResponseObj.createBy(ResponseCode.ERROR, "用户不存在");
        }
        // 2.登陆
        User user = userService.findByUserNameAndPsw(userName, password);
        if (ObjectUtils.isEmpty(user)) {
            return ResponseObj.createBy(ResponseCode.ERROR,"登陆失败");
        } else {
            return ResponseObj.createBy(ResponseCode.SUCCESS,"登陆成功", user);
        }
    }
}
