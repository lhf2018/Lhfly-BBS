package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pojo.Tab;
import service.*;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    TopicService topicService;
    @Autowired
    ReplyService replyService;
    @Autowired
    TabService tabService;
    @Autowired
    UserService userService;
    @Autowired
    VisitorService visitorService;
    /**
     * 进入登陆页面
     */
    @RequestMapping("/signin")
    public ModelAndView signin(){
        ModelAndView mv=new ModelAndView("signin");

        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int userNum=userService.getUserCount();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();
        mv.addObject("topicsNum", topicsNum);
        mv.addObject("usersNum", userNum);
        mv.addObject("visitorNum",visitorNum);
        mv.addObject("todayVisitor", visitorService.todayVisitor());
        return mv;
    }
    /**
     * 进入注册界面
     */
    @RequestMapping("/signup")
    public ModelAndView signup(){
        ModelAndView mv=new ModelAndView("signup");

        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();
        mv.addObject("topicsNum",topicsNum);
        mv.addObject("usersNum",usersNum);
        mv.addObject("visitorNum",visitorNum);
        mv.addObject("todayVisitor", visitorService.todayVisitor());
        return  mv;
    }
    /**
     * 进入新建主题界面
     */
    @RequestMapping("/new")
    public ModelAndView newTopic(){
        ModelAndView mv=new ModelAndView("new");
        List<Tab> tabs=tabService.getAllTabs();
        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        int usersNum=userService.getUserCount();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();
        mv.addObject("tabs",tabs);
        mv.addObject("topicsNum",topicsNum);
        mv.addObject("usersNum",usersNum);
        mv.addObject("visitorNum",visitorNum);
        mv.addObject("todayVisitor", visitorService.todayVisitor());
        return mv;
    }
    /**
     * 配置404界面
     */
    @RequestMapping("*")
    public String notFind(){
        return "404";
    }
}
