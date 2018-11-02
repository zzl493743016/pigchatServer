package com.pig.service.addFriendRecord.impl;

import com.pig.dao.mapper.AddFriendRecordMapper;
import com.pig.dao.pojo.AddFriendRecord;
import com.pig.dao.pojo.AddFriendRecordExample;
import com.pig.service.addFriendRecord.AddFriendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Arthas
 * @create 2018/11/2
 */
@Service
public class AddFriendRecordServiceImpl implements AddFriendRecordService {

    @Autowired
    private AddFriendRecordMapper addFriendRecordMapper;

    @Override
    public boolean addFriendRequest(Integer senderId, Integer recieverId, String note) {
        AddFriendRecord record = new AddFriendRecord();
        record.setSenderId(senderId);
        record.setRecieverId(recieverId);
        record.setNote(note);
        int insert = addFriendRecordMapper.insert(record);
        if (insert > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AddFriendRecord> findAddFriendRecords(Integer myId) {
        AddFriendRecordExample example = new AddFriendRecordExample();
        AddFriendRecordExample.Criteria criteria = example.createCriteria();
        criteria.andRecieverIdEqualTo(myId);
        return addFriendRecordMapper.selectByExample(example);
    }

    @Override
    public void removeRecord(Integer myId, Integer friendId) {
        AddFriendRecordExample example = new AddFriendRecordExample();
        AddFriendRecordExample.Criteria criteria = example.createCriteria();
        criteria.andSenderIdIn(Arrays.asList(myId, friendId))
                .andRecieverIdIn(Arrays.asList(myId, friendId));
        addFriendRecordMapper.deleteByExample(example);
    }

}
