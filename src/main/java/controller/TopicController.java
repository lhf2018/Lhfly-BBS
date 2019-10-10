package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pojo.Reply;
import pojo.Tab;
import pojo.Topic;
import pojo.User;
import service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class TopicController {
    @Autowired
    TopicService topicService;
    @Autowired
    ReplyService replyService;
    @Autowired
    UserService userService;
    @Autowired
    TabService tabService;
    @Autowired
    VisitorService visitorService;

    /**
     * 首页
     */
    @RequestMapping("/")
    public ModelAndView toMain(HttpSession session){
        ModelAndView modelAndView=new ModelAndView("cate");
        //首页全部主题
        List<Topic> topics=topicService.listTopicsAndUsers();
        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();

        //获取用户信息
        Integer uid=(Integer)session.getAttribute("userId");
        User user=userService.getUserById(uid);
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();
        //热门主题
        List<Topic> hotestTopics=topicService.listMostCommentsTopics();
        modelAndView.addObject("topics", topics);
        modelAndView.addObject("hotestTopics", hotestTopics);
        modelAndView.addObject("topicsNum", topicsNum);
        modelAndView.addObject("usersNum", usersNum);
        modelAndView.addObject("user", user);
        modelAndView.addObject("visitorNum", visitorNum);
        return modelAndView;
    }
    /**
     * 加载主题详细页面
     */
    @RequestMapping("/t/{id}")
    public ModelAndView toTopic(@PathVariable("id")int id,HttpSession session){
        //点击量+1
        boolean ifSuccess=topicService.clickAddOne(id);
        //获取主题信息
        Topic topic=topicService.selectById(id);
        //获取主题全部评论
        List<Reply> replies=replyService.getRepliesOfTopic(id);
        //获取评论数
        int repliesNum=replyService.repliesNum(id);
        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();
        //获取用户信息
        Integer uid= (Integer) session.getAttribute("userId");
        User user=userService.getUserById(uid);
        //最热主题
        List<Topic> hotestTopics=topicService.listMostCommentsTopics();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();

        ModelAndView topicPage=new ModelAndView("detail");
        topicPage.addObject("topic", topic);
        topicPage.addObject("replies", replies);
        topicPage.addObject("repliesNum",repliesNum);
        topicPage.addObject("topicsNum",topicsNum);
        topicPage.addObject("usersNum",usersNum);
        topicPage.addObject("user",user);
        topicPage.addObject("hotestTopics",hotestTopics);
        topicPage.addObject("visitorNum",visitorNum);
        return topicPage;
    }
    /**
     * 加载指定板块
     */
    @RequestMapping("/tab/{tabNameEn}")
    public ModelAndView toTabPage(@PathVariable("tabNameEn")String tabNameEn,HttpSession session){
        Tab tab=tabService.getByTabNameEn(tabNameEn);
        Integer tabId=tab.getId();

        ModelAndView mv=new ModelAndView("cate");
        //全部主题
        List<Topic> topics=topicService.listTopicsAndUsersOfTab(tabId);

        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();
        //获取用户信息
        Integer uid= (Integer) session.getAttribute("userId");
        User user=userService.getUserById(uid);

        List<Topic> hotestTopics=topicService.listMostCommentsTopics();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();
        mv.addObject("topics", topics);
        mv.addObject("topicsNum", topicsNum);
        mv.addObject("usersNum", usersNum);
        mv.addObject("tab", tab);
        mv.addObject("user", user);
        mv.addObject("hotestTopics", hotestTopics);
        mv.addObject("visitorNum",visitorNum);
        return mv;
    }
    /**
     * 发表主题
     */
    @RequestMapping(value = "/topic/add",method = RequestMethod.POST)
    public ModelAndView addTopic(HttpServletRequest request,HttpSession session){
        ModelAndView mv;
        //未登录
        if(session.getAttribute("userId")==null){
            mv=new ModelAndView("redirect:/signin");
            return mv;
        }
        //处理参数
        Integer userId= (Integer) session.getAttribute("userId");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        Byte tabId=Byte.parseByte(request.getParameter("tab"));
        //新建topic
        Topic topic=new Topic();
        topic.setUserId(userId);
        topic.setTitle(title);
        topic.setContent(content);
        topic.setTabId(tabId);
        topic.setCreateTime(new Date());
        topic.setUpdateTime(new Date());
        //添加topic
        boolean ifSucc=topicService.addTopic(topic);
        userService.addCredit(1,userId);
        mv=new ModelAndView("redirect:/");
        return mv;
    }
    /**
     * 活跃/精华/最近 版面的跳转中转
     */
    @RequestMapping("/topic/{classifyName}")
    public String temp(@PathVariable("classifyName")String classifyName,HttpServletRequest request){
        String tabNameEn=request.getHeader("Referer");
        if(tabNameEn.contains("tab")){
            int index=tabNameEn.lastIndexOf("/");
            tabNameEn=tabNameEn.substring(index+1);
            //如果在具体某个tab上就转到对应的界面
            return "redirect:/topictab/"+classifyName+"/"+tabNameEn;
        }else {
            //如果不在具体某个tab上，就把tabName写为all
            return "redirect:/topictab/"+classifyName+"/all";
        }
    }
    /**
     * 每个版面都要有自己相应的 活跃/精华/最近
     * @param classifyName
     * @param session
     * @return
     */
    @RequestMapping("/topictab/{classifyName}/{tabNameEn}")
    public ModelAndView classify(@PathVariable("classifyName")String classifyName,@PathVariable("tabNameEn")String tabNameEn,HttpSession session){
        List<Topic> topics;
        if(!tabNameEn.equals("all")){
            //获取版面信息
            Tab tab=tabService.getByTabNameEn(tabNameEn);
            Integer tabId=tab.getId();
            if(classifyName.equals("active")){
                //获取活跃主题
                topics=topicService.listActiveTopics(tabId);
            }else if(classifyName.equals("essence")){
                //获取精品主题主题
                topics=topicService.listActiveTopics(tabId);
            }else{
                //获取最近主题
                topics=topicService.listRecentTopics(tabId);
            }

        }else {
            if(classifyName.equals("active")){
                //获取活跃主题
                topics=topicService.listAllActiveTopics();
            }else if(classifyName.equals("essence")){
                //获取精品主题主题
                topics=topicService.listAllActiveTopics();
            }else{
                //获取最近主题
                topics=topicService.listAllRecentTopics();
            }
        }
        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();
        //获取用户信息
        Integer uid= (Integer) session.getAttribute("userId");
        User user=userService.getUserById(uid);
        //获取热门主题
        List<Topic> hotestTopics=topicService.listMostCommentsTopics();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();
        ModelAndView mv=new ModelAndView("cate");
        mv.addObject("topics", topics);
        mv.addObject("topicsNum", topicsNum);
        mv.addObject("usersNum", usersNum);
        mv.addObject("user", user);
        mv.addObject("hotestTopics", hotestTopics);
        mv.addObject("visitorNum",visitorNum);
        return mv;
    }
}
