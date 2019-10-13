package controller;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pojo.LoginLog;
import pojo.Topic;
import pojo.User;
import service.LoginLogService;
import service.TopicService;
import service.UserService;
import service.VisitorService;
import util.ProduceMD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    LoginLogService loginLogService;
    @Autowired
    TopicService topicService;
    @Autowired
    VisitorService visitorService;
    //用户注册
    @RequestMapping("/user/add/do")
    public String addUser(HttpServletRequest request,HttpSession session){
        String username=request.getParameter("username");
        //如果存在就返回到注册页面
        if(userService.existUsername(username)){
            request.setAttribute("errorMessage","You are the best");
            //TODO: 校验
//            return "redirect:/signup/{errorMessage}";
            return "redirect:/signup";
        }

        //新建user对象
        User user=new User();
        String phoneNum=request.getParameter("tel");
        String areaCode=request.getParameter("areaCode");
        String phone=areaCode+phoneNum;
        //用户类型
        Byte type=new Byte("0");
        //密码加密处理
        String password= ProduceMD5.getMD5(request.getParameter("password"));
        //生成随机数，用于生成头像URL
        Random random=new Random();
        int randomNum=random.nextInt(10)+1;
        String avatarUrl="/img/avatar/avatar-default-"+randomNum+".png";
        //初始化User对象
        user.setUsername(request.getParameter("username"));
        user.setPassword(password);
        user.setEmail(request.getParameter("email"));
        user.setPhoneNum(phone);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setCredit(0);
        user.setType(type);
        user.setAvatar(avatarUrl);

        boolean success=userService.addUser(user);
        System.out.println(success);
        return "redirect:/";

    }
    /**
     * 用户登陆 ajax从后台发送请求验证
     * @param request
     * @param session
     * @return 0:用户名不存在 1:密码错误 2:登录成功
     */
    @RequestMapping("/api/loginCheck")
    @ResponseBody
    public Object signin(HttpServletRequest request, HttpSession session){
        //处理参数
        String password=ProduceMD5.getMD5(request.getParameter("password"));
        String username=request.getParameter("username");
        //验证用户名密码
        int loginVerify=userService.login(username,password);

        HashMap<String,String> res=new HashMap<String, String>();

        //登陆成功
        if(loginVerify==2){
            User user=userService.getUserByUsername(username);
            Integer userId=user.getId();
            //添加积分
            boolean ifSuccAddCredit=userService.addCredit(1, userId);
            //用户信息写入session
            session.setAttribute("userId",userId);
            session.setAttribute("username", username);
            //获取登陆信息
            String ip=getRemortIP(request);
            UserAgent userAgent=UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            //获取用户浏览器名
            String userbrowser=userAgent.getBrowser().toString();
            //写入登陆日志
            LoginLog log=new LoginLog();
            log.setDevice(userbrowser);
            log.setDevice(userbrowser);
            log.setUserId(userId);
            log.setIp(ip);
            log.setLoginTime(new Date());
            boolean ifSuccAddLog=loginLogService.addLoginLog(log);

            res.put("stateCode","2");
        }
        //密码错误
        else if(loginVerify==1){
            res.put("stateCode", "1");
        }else {
            res.put("stateCode", "0");
        }
        return res;
    }
    /**
     * 判断用户是否存在
     */
    @RequestMapping("/existUsername")
    @ResponseBody
    public String existUsername(HttpServletRequest request){
        String username=request.getParameter("username");
        boolean isExist=userService.existUsername(username);
        if(isExist){
            return "1";
        }else {
            return "0";
        }
    }

    /**
     * 用户登出
     */
    @RequestMapping("/signout")
    public String signout(HttpSession session){
        session.removeAttribute("userId");
        session.removeAttribute("username");
        return "redirect:/";
    }
    /**
     * 获取客户端IP
     */
    private String getRemortIP(HttpServletRequest request){
        if(request.getHeader("x-forwarded-for")==null){
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
    /**
     * 用户个人主页
     */
    @RequestMapping("/member/{username}")
    public ModelAndView personalCenter(@PathVariable("username") String username,HttpSession session){
        boolean ifExistUser=userService.existUsername(username);
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
        ModelAndView mv=new ModelAndView("user_info");
        mv.addObject("hotestTopics",hotestTopics);
        mv.addObject("visitorNum", visitorNum);
        mv.addObject("todayVisitor", visitorService.todayVisitor());
        if(ifExistUser){
            User resultUser=userService.getUserByUsername(username);
            mv.addObject("userInfo",resultUser);
            mv.addObject("topicsNum",topicsNum);
            mv.addObject("usersNum",usersNum);
            mv.addObject("user",user);
            return mv;
        }else {
            String errorInfo="会员未找到";
            mv.addObject("errorInfo", errorInfo);
            return mv;
        }
    }
    @RequestMapping("/settings")
    public ModelAndView settings(HttpServletRequest request, HttpSession session){

        Integer uid=(Integer) session.getAttribute("userId");
        User user=userService.getUserById(uid);

        //最热主题
        List<Topic> hotestTopics=topicService.listMostCommentsTopics();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();

        ModelAndView mv=new ModelAndView("settings");
        mv.addObject("user",user);
        mv.addObject("hotestTopics",hotestTopics);
        mv.addObject("visitorNum",visitorNum);
        mv.addObject("todayVisitor", visitorService.todayVisitor());
        return mv;
    }
    @RequestMapping(value = "/settings/avatar",method = RequestMethod.GET)
    public ModelAndView updateAvatar(HttpServletRequest request, HttpSession session){

        Integer uid=(Integer) session.getAttribute("userId");
        User user=userService.getUserById(uid);

        //最热主题
        List<Topic> hotestTopics=topicService.listMostCommentsTopics();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();

        ModelAndView mv=new ModelAndView("update_avatar");
        mv.addObject("user",user);
        mv.addObject("hotestTopics",hotestTopics);
        mv.addObject("visitorNum",visitorNum);
        mv.addObject("todayVisitor", visitorService.todayVisitor());
        return mv;
    }
    @RequestMapping(value = "/settings/avatar/update",method = RequestMethod.POST)
    public ModelAndView updateAvatarDo(@RequestPart("avatar")MultipartFile avatarFile,HttpServletRequest request,HttpSession session){
        Integer uid= (Integer) session.getAttribute("userId");

        String fileName=avatarFile.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        Long date=new Date().getTime();
        String newFileName=date+"-"+uid+"."+suffix;
        //绝对路径
        String absolutePath=session.getServletContext().getRealPath("/static/img/avatar")+"/"+newFileName;
        //相对路径
        String relativePath="/img/avatar"+"/"+newFileName;
        User newUser=new User();
        newUser.setAvatar(relativePath);
        newUser.setId(uid);
        File file=new File(absolutePath);
        //如果这个文件并不存在
        if(!file.exists()){
            try {
                avatarFile.transferTo(file);
                userService.updateUser(newUser);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        User user=userService.getUserById(uid);
        //最热主题
        List<Topic> hotestTopics=topicService.listMostCommentsTopics();
        //获取访问量
        Integer visitorNum=visitorService.countVisitor();
        ModelAndView mv=new ModelAndView("update_avatar");
        mv.addObject("user", user);
        mv.addObject("hotestTopics", hotestTopics);
        mv.addObject("visitorNum",visitorNum);
        mv.addObject("todayVisitor", visitorService.todayVisitor());
        return mv;
    }
}
