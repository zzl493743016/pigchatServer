package com.pig.service.user;

import com.pig.dao.pojo.User;

import java.util.List;

/**
 * @author Arthas
 * @create 2018/11/2
 */
public interface UserService {

    /**
     * 查看是否有该用户
     */
    User findByUserName(String userName);


    /**
     * 添加用户
     */
    boolean addUser(String userName, String password);


    /**
     * 根据用户名和密码寻找用户
     */
    User findByUserNameAndPsw(String userName, String password);

    /**
     * 根据用户id列表返回用户列表
     */
    List<User> getUsersByIds(List<Integer> ids);

}
