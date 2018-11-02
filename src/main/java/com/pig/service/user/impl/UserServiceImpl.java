package com.pig.service.user.impl;

import com.pig.dao.mapper.UserMapper;
import com.pig.dao.pojo.User;
import com.pig.dao.pojo.UserExample;
import com.pig.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean checkIfUserNameExist(String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        long count = userMapper.countByExample(example);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean register(String userName, String password) {
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
    public boolean login(String userName, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        criteria.andPasswordEqualTo(password);
        long count = userMapper.countByExample(example);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
