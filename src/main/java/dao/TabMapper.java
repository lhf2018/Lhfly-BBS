package dao;

import pojo.Tab;

import java.util.List;

public interface TabMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Tab tab);
    int insertSelective(Tab tab);

    Tab selectByPrimaryKey(Integer id);//根据id选择版面
    Tab getByTabNameEn(String tabName);//根据名字选择版面

    int updateByPrimaryKeySelective(Tab record);
    int updateByPrimaryKey(Tab tab);

    List<Tab> getAllTabs();//获取全部版面
}
