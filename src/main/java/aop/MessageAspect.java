package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import pojo.Message;
import pojo.Reply;
import pojo.Topic;
import pojo.User;
import service.MessageService;
import service.ReplyService;
import service.TopicService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 插入消息列
 */
@Component
@Aspect
public class MessageAspect {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    TopicService topicService;
    //当添加了reply后，获取从后台返回的ModelAndView
    @AfterReturning(returning = "view",pointcut = "execution(* controller.ReplyController.addReply(..))")
    public void insertMessage(Object view){
        HttpServletRequest request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer topicId=Integer.parseInt(request.getParameter("topicId"));
        Topic topic =topicService.selectById(topicId);
        Integer replyUserId=Integer.parseInt(request.getParameter("replyUserId"));
        Reply reply= (Reply) ((ModelAndView)view).getModel().get("reply");
        //创建message
        Message message=new Message();
        message.setAction("1");
        message.setContext(reply.getContent());
        message.setCreate_time(reply.getCreateTime());
        message.setIs_read(0);
        message.setType("0");
        message.setTopic_id(topicId);
        message.setSender_id(replyUserId);
        message.setUser_id(topic.getUserId());
        message.setReply_id(reply.getId());

        messageService.insert(message);

    }
}
