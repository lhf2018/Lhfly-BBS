package dao;

import pojo.LoginLog;

public interface LoginLogMapper {
    LoginLog selectByPrimaryKey(long id);
    int deleteByPrimaryKey(int id);
    int insert(LoginLog loginLog);
    int insertSelective(LoginLog loginLog);
    int updateByPrimaryKey(LoginLog loginLog);
    int updateByPrimaryKeySelective(LoginLog loginLog);
}
