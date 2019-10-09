package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testlog {
    @Autowired
    testtopic testtopic;
    @RequestMapping("test")
    public String test(){
        testtopic.testTopic();
        return "cate";
    }
}
