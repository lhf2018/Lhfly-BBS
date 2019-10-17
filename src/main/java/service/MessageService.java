package service;

import pojo.Message;

import java.util.List;

public interface MessageService {
    boolean insert(Message message);
    List<Message> selectAllMessageByUserId(Integer userId);
    boolean readReplyOfTopic(Integer topidId);
    List<Message> getAllMessage(Integer userId);
    int getAllMessageNumOfUser(Integer userId);
    int getUnreadMessageNumOfUser(Integer userId);
}
