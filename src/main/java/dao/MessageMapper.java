package dao;

import pojo.Message;

import java.util.List;

public interface MessageMapper {
    int insert(Message message);
    List<Message> selectAllMessageByUserId(Integer userId);
    int readReplyOfTopic(Integer topidId);
    List<Message> getAllMessage(Integer userId);
    int getAllMessageNumOfUser(Integer userId);
    int getUnreadMessageNumOfUser(Integer userId);
}
