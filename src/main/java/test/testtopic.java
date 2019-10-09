package test;

import dao.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Topic;

import java.util.List;

@Service
public class testtopic {
    @Autowired
    TopicMapper topicMapper;

    public List<Topic> testTopic(){
        System.out.println(topicMapper);
        List<Topic> topics=topicMapper.listTopicsAndUsers();
//        List<Topic> topics=new ArrayList<Topic>();
        System.out.println(topics.size());
        return topics;
    }
}
