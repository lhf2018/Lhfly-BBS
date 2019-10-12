package service.Impl;

import dao.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Reply;
import service.ReplyService;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyMapper replyMapper;

    public List<Reply> getRepliesOfTopic(Integer topidId) {
        return replyMapper.getRepliesOfTopic(topidId);
    }

    public boolean addReply(Reply reply) {
        return replyMapper.insert(reply)>0;
    }

    public int repliesNum(Integer topicId) {
        return replyMapper.getRepliesNum(topicId);
    }

    public int deleteByTopicId(int id) {
        return replyMapper.deleteByTopicId(id);
    }

    public int deleteByPrimaryKey(Long id) {
        return replyMapper.deleteByPrimaryKey(id);
    }
}
