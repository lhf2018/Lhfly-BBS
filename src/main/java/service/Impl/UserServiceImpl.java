package service.Impl;

import dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.User;
import service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    public boolean addUser(User user) {
        return userMapper.addUser(user)>0;
    }
    //登陆检查
    public int login(String username, String password) {
        //判断username是否存在
        boolean existUsername=existUsername(username);
        //如果存在，验证密码
        if(existUsername){
            User user=userMapper.selectByUsername(username);
            if(user.getPassword().equals(password)){
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public boolean addCredit(Integer points, Integer id) {
        return userMapper.addCredit(points,id)>0;
    }

    public boolean existUsername(String username) {
        return userMapper.existUsername(username)==1;
    }

    public User getUserByUsername(String username) {
        User user=userMapper.selectByUsername(username);
        return user;
    }

    public int getUserCount() {
        return userMapper.getUserCount();
    }

    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }

    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
