package service.Impl;

import dao.TabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Tab;
import service.TabService;

import java.util.List;
@Service
public class TabServiceImpl implements TabService {
    @Autowired
    TabMapper tabMapper;
    //获取全部版面
    public List<Tab> getAllTabs() {
        return tabMapper.getAllTabs();
    }
    //根据名字选择版面
    public Tab getByTabNameEn(String tabName) {
        return tabMapper.getByTabNameEn(tabName);
    }
}
