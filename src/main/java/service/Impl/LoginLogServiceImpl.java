package service.Impl;

import dao.LoginLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.LoginLog;
import service.LoginLogService;
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    LoginLogMapper loginLogMapper;
    public boolean addLoginLog(LoginLog loginLog) {
        int result=loginLogMapper.insert(loginLog);
        if(result>0){
            return true;
        }else {
            return false;
        }
    }
}
