package com.pig.service.user.impl;

import com.pig.dao.mapper.UserMapper;
import com.pig.dao.pojo.User;
import com.pig.dao.pojo.UserExample;
import com.pig.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public boolean addUser(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User findByUserNameAndPsw(String userName, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        criteria.andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        } else {
            return users.get(0);
        }
    }
}
