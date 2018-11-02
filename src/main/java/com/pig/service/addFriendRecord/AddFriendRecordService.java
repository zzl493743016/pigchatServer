package com.pig.service.addFriendRecord;

import com.pig.dao.pojo.AddFriendRecord;

import java.util.List;

/**
 * @author Arthas
 * @create 2018/11/2
 */
public interface AddFriendRecordService {

    /**
     * 添加好友申请
     */
    boolean addFriendRequest(Integer senderId, Integer recieverId, String note);

    /**
     * 获取好友请求
     * @param myId
     * @return
     */
    List<AddFriendRecord> findAddFriendRecords(Integer myId);
}
