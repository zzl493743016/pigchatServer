package com.pig.service.user;

/**
 * @author Arthas
 * @create 2018/11/2
 */
public interface UserService {

    /**
     * 查看是否已注册
     */
    boolean checkIfUserNameExist(String userName);


    /**
     * 注册
     */
    boolean register(String userName, String password);


    /**
     * 登陆
     */
    boolean login(String userName, String password);
}
