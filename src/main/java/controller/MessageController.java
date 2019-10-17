package controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pojo.Message;
import pojo.Topic;
import pojo.User;
import service.*;
import util.Page;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    UserService userService;
    @Autowired
    LoginLogService loginLogService;
    @Autowired
    TopicService topicService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    MessageService messageService;

    @RequestMapping("/message/{userId}")
    public ModelAndView getMessage(@PathVariable("userId")Integer userId
            , HttpSession session, Page page){
        ModelAndView mv=new ModelAndView("message");
        //获取第1页，10条内容，默认查询总数count
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Message> messages=messageService.getAllMessage(userId);

        //分页
        int total= (int) new PageInfo<Message>(messages).getTotal();
        page.setTotal(total);
        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();

        //获取用户信息
        Integer uid= (Integer) session.getAttribute("userId");
        User user=userService.getUserById(uid);
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();
        //最热主题
        List<Topic> hotestTopics=topicService.listMostCommentsTopics();
        //把user查出来赋值给message中的sender
        for(int i=0;i<messages.size();i++){
            User sender=userService.getUserById(messages.get(i).getSender_id());
            messages.get(i).setSender(sender);
        }
        //把topic查出来赋值给message中的topic_name
        for(int i=0;i<messages.size();i++){
            Topic topic=topicService.selectById(messages.get(i).getTopic_id());
            messages.get(i).setTopic_name(topic.getTitle());
        }
        //消息数量
        int messNum=messageService.getAllMessageNumOfUser(userId);
        mv.addObject("hotestTopics",hotestTopics);
        mv.addObject("visitorNum", visitorNum);
        mv.addObject("todayVisitor", visitorService.todayVisitor());
        mv.addObject("unreadMessage", messageService.getUnreadMessageNumOfUser(uid));
        mv.addObject("messages", messages);
        mv.addObject("user",user);
        mv.addObject("page", page);
        mv.addObject("topicsNum",topicsNum);
        mv.addObject("usersNum",usersNum);
        mv.addObject("messNum",messNum);
        return mv;
    }
    @RequestMapping("/message/read/{topicId}")
    public String readMessage(@PathVariable("topicId")Integer topicId){
        messageService.readReplyOfTopic(topicId);
        return "redirect:/t/"+topicId;
    }
}
