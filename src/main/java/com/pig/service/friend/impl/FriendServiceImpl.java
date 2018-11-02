package com.pig.service.friend.impl;

import com.pig.dao.mapper.FriendMapper;
import com.pig.dao.pojo.Friend;
import com.pig.dao.pojo.FriendExample;
import com.pig.service.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public Friend findByFriendId(Integer friendId) {
        FriendExample example = new FriendExample();
        FriendExample.Criteria criteria = example.createCriteria();
        criteria.andFriendIdEqualTo(friendId);
        List<Friend> friends = friendMapper.selectByExample(example);
        if (ObjectUtils.isEmpty(friends)) {
            return null;
        } else {
            return friends.get(0);
        }
    }

}
