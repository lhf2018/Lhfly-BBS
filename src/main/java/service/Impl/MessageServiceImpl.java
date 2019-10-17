package service.Impl;

import dao.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Message;
import service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    public boolean insert(Message message) {
        return messageMapper.insert(message)>0;
    }

    public List<Message> selectAllMessageByUserId(Integer userId) {
        return messageMapper.selectAllMessageByUserId(userId);
    }

    public boolean readReplyOfTopic(Integer topidId) {
        return messageMapper.readReplyOfTopic(topidId)>0;
    }

    public List<Message> getAllMessage(Integer userId) {
        return messageMapper.getAllMessage(userId);
    }

    public int getAllMessageNumOfUser(Integer userId) {
        return messageMapper.getAllMessageNumOfUser(userId);
    }

    public int getUnreadMessageNumOfUser(Integer userId) {
        return messageMapper.getUnreadMessageNumOfUser(userId);
    }
}
