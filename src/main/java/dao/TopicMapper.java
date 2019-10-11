package dao;

import pojo.Topic;

import java.util.List;

public interface TopicMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Topic topic);
    int insertSelective(Topic topic);
    //帖子详情
    Topic selectById(Integer id);
    //一个帖子完整的信息
    List<Topic> listTopicsAndUsers();
    //根据tab页查询topic
    List<Topic> listTopicsAndUsersOfTab(Integer tabId);
    //热门帖子
    List<Topic> listMostCommentsTopics();
    int updateByPrimaryKeySelective(Topic topic);
    //更新内容
    int updateByPrimaryKeyWithBLOBs(Topic topic);
    //更新其他
    int updateByPrimaryKey(Topic topic);
    List<Topic> getAllTopics();
    //增加访问流量
    int clickAddOne(Integer id);
    //获取主题总数
    int getTopicsNum();
    //获取活跃主题
    List<Topic> listActiveTopics(Integer tabiId);
    //获取全部活跃主题
    List<Topic> listAllActiveTopics();
    //获取最近主题
    List<Topic> listRecentTopics(Integer tabId);
    //获取全部最近主题
    List<Topic> listAllRecentTopics();
    //获取全部精品帖子
    List<Topic> listAllEssenceTopics();
    //获取部分精品帖子
    List<Topic> listEssenceTopics(Integer tabId);
    //删除主题
    int deleteByPrimaryKey(int topicId);


}
