package dao;

import pojo.Reply;

import java.util.List;

public interface ReplyMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Reply reply);
    int insertSelective(Reply record);//未实现
    Reply selectByPrimaryKey(Long id);//no complete
    List<Reply> getRepliesOfTopic(Integer id);
    int updateByPrimaryKeySelective(Reply record);//no
    int updateByPrimaryKeyWithBLOBs(Reply record);//no
    int updateByPrimaryKey(Reply record); //no
    int getRepliesNum(Integer id);
}
