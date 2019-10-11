package service.Impl;

import dao.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Topic;
import service.TopicService;

import java.util.List;
@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    public TopicMapper topicMapper;
    //获取全部主题
    public List<Topic> getAllTopics() {
        return topicMapper.getAllTopics();
    }

    public List<Topic> listTopicsAndUsers() {
        return topicMapper.listTopicsAndUsers();
    }

    public List<Topic> listMostCommentsTopics() {
        return topicMapper.listMostCommentsTopics();
    }

    public List<Topic> listTopicsAndUsersOfTab(Integer tabId) {
        return topicMapper.listTopicsAndUsersOfTab(tabId);
    }
    //获取指定id主题
    public Topic selectById(Integer id) {
        Topic topic=topicMapper.selectById(id);
        return topic;
    }

    public boolean addTopic(Topic topic) {
        return topicMapper.insert(topic)>0;
    }

    public boolean clickAddOne(Integer id) {
        return topicMapper.clickAddOne(id)>0;
    }

    public int getTopicsNum() {
        return topicMapper.getTopicsNum();
    }

    public List<Topic> listActiveTopics(Integer tabId) {
        return topicMapper.listActiveTopics(tabId);
    }

    public List<Topic> listAllActiveTopics() {
        return topicMapper.listAllActiveTopics();
    }

    public List<Topic> listRecentTopics(Integer tabId) {
        return topicMapper.listRecentTopics(tabId);
    }

    public List<Topic> listAllRecentTopics() {
        return topicMapper.listAllRecentTopics();
    }

    public int updateByPrimaryKeySelective(Topic topic) {
        return topicMapper.updateByPrimaryKeySelective(topic);
    }

    public List<Topic> listAllEssenceTopics() {
        return topicMapper.listAllEssenceTopics();
    }

    public List<Topic> listEssenceTopics(Integer tabId) {
        return topicMapper.listEssenceTopics(tabId);
    }

    public int deleteByPrimaryKey(int topicId) {
        return topicMapper.deleteByPrimaryKey(topicId);
    }
}
