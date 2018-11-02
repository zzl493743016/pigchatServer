package com.pig.controller.user;

import com.pig.service.user.UserService;
import com.pig.utils.ResponseCode;
import com.pig.utils.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@RequestMapping(value = "/user/")
@Controller
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 注册端口
     * @return
     */
    @PostMapping(value = "register")
    @ResponseBody
    public ResponseObj register(String userName,
                                String password) {
        // 1.查看名称是否已经被注册
        if (userService.checkIfUserNameExist(userName)) {
            return ResponseObj.createBy(ResponseCode.ERROR, "用户已经被注册");
        }
        // 2.将数据插入
        if (userService.register(userName, password)) {
            return ResponseObj.createBy(ResponseCode.SUCCESS, "注册成功");
        }
        return ResponseObj.createBy(ResponseCode.ERROR, "注册失败");
    }

    /**
     * 登陆端口
     * @return
     */
    @PostMapping(value = "login")
    @ResponseBody
    public ResponseObj login(String userName,
                             String password) {
        // 1.查看用户是否存在
        if (!userService.checkIfUserNameExist(userName)) {
            return ResponseObj.createBy(ResponseCode.ERROR, "用户不存在");
        }
        // 2.登陆
        if (userService.login(userName, password)) {
            return ResponseObj.createBy(ResponseCode.SUCCESS, "登陆成功");
        }
        return ResponseObj.createBy(ResponseCode.ERROR,"登陆失败");
    }
}
