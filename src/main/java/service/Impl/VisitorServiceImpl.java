package service.Impl;

import dao.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Visitor;
import service.VisitorService;

@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    VisitorMapper visitorMapper;
    public boolean insert(Visitor visitor) {
        return visitorMapper.insert(visitor)>0;
    }

    public int countVisitor() {
        return visitorMapper.countVisitor();
    }

    public int todayVisitor() {
        return visitorMapper.todayVisitor();
    }
}
