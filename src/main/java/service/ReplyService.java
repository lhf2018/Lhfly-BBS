package service;

import pojo.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> getRepliesOfTopic(Integer topidId);
    boolean addReply(Reply reply);
    int repliesNum(Integer topicId);
    //删除某个主题下面的回复
    int deleteByTopicId(int id);
}
