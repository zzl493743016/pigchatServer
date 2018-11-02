package com.pig.controller.user;

import com.pig.utils.ResponseObj;
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

    /**
     * 注册端口
     * @return
     */
    @PostMapping(value = "register")
    @ResponseBody
    public ResponseObj register() {

        return ResponseObj.createBySuccessMessage("注册成功");
    }

    /**
     * 登陆端口
     * @return
     */
    @PostMapping(value = "login")
    @ResponseBody
    public ResponseObj login() {
        return ResponseObj.createBySuccessMessage("登陆成功");
    }
}
