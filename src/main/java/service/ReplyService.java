package service;

import pojo.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> getRepliesOfTopic(Integer topidId);
    boolean addReply(Reply reply);
    int repliesNum(Integer topicId);
}
