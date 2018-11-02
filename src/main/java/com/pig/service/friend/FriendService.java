package com.pig.service.friend;

import com.pig.dao.pojo.Friend;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author Arthas
 * @create 2018/11/2
 */
public interface FriendService {


    /**
     * 根据朋友id查找
     */
    Friend findByFriendId(Integer friendId);


    /**
     * 查看是否已经是朋友关系
     */
    boolean checkIfFriends(Integer myId, Integer friendId);

    /**
     * 添加好友关系
     */
    boolean addFriend(Integer myId, Integer friendId);


    /**
     * 获取好友id列表
     */
    List<Integer> getFriendIdList(Integer myId, Integer page, Integer size);

}
