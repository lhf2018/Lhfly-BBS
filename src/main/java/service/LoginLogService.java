package service;

import pojo.LoginLog;

public interface LoginLogService {
//插入一条登陆日志
    boolean addLoginLog(LoginLog loginLog);
}
