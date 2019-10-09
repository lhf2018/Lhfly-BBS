package service;

import pojo.User;

public interface UserService {
    /*
    用户注册
     */
    boolean addUser(User user);
    /*
    登陆验证
     */
    int login(String username,String password);
    /*
     * 添加积分
     */
    boolean addCredit(Integer points,Integer id);
    /*
    检查username是否存在
     */
    boolean existUsername(String username);
    /*
    获取用户信息
     */
    User getUserByUsername(String username);
    /*
    获取用户数
     */
    int getUserCount();
    boolean updateUser(User user);
    User getUserById(Integer id);
}
