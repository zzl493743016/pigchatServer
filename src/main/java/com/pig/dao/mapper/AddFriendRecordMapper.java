package com.pig.dao.mapper;

import com.pig.dao.pojo.AddFriendRecord;
import com.pig.dao.pojo.AddFriendRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AddFriendRecordMapper {
    long countByExample(AddFriendRecordExample example);

    int deleteByExample(AddFriendRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AddFriendRecord record);

    int insertSelective(AddFriendRecord record);

    List<AddFriendRecord> selectByExample(AddFriendRecordExample example);

    AddFriendRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AddFriendRecord record, @Param("example") AddFriendRecordExample example);

    int updateByExample(@Param("record") AddFriendRecord record, @Param("example") AddFriendRecordExample example);

    int updateByPrimaryKeySelective(AddFriendRecord record);

    int updateByPrimaryKey(AddFriendRecord record);
}