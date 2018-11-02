package com.pig.dao.mapper;

import com.pig.dao.pojo.ChatLog;
import com.pig.dao.pojo.ChatLogExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatLogMapper {
    long countByExample(ChatLogExample example);

    int deleteByExample(ChatLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChatLog record);

    int insertSelective(ChatLog record);

    List<ChatLog> selectByExample(ChatLogExample example);

    ChatLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChatLog record, @Param("example") ChatLogExample example);

    int updateByExample(@Param("record") ChatLog record, @Param("example") ChatLogExample example);

    int updateByPrimaryKeySelective(ChatLog record);

    int updateByPrimaryKey(ChatLog record);
}