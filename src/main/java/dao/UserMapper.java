package dao;

import org.apache.ibatis.annotations.Param;
import pojo.User;

public interface UserMapper {
    User selectByPrimaryKey(Integer id);
    //查询用户数
    int getUserCount();
    User selectByUsername(String username);
    //查询username是否存在
    int existUsername(String username);
    int deleteByPrimaryKey(Integer id);
    int addUser(User user);
    int insertSelective(User user);
    int updateByPrimaryKeySelective(User user);
    int updateByPrimaryKey(User user);
    int addCredit(@Param("points")Integer points,@Param("id")Integer id);//增加积分
}
