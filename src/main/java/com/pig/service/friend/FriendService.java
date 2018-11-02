package com.pig.service.friend;

import com.pig.dao.pojo.Friend;

/**
 * @author Arthas
 * @create 2018/11/2
 */
public interface FriendService {


    /**
     * 根据朋友id查找
     */
    Friend findByFriendId(Integer friendId);




}
