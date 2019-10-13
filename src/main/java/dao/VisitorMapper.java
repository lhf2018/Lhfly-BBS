package dao;

import pojo.Visitor;

public interface VisitorMapper {
    int insert(Visitor visitor);
    int countVisitor();
    int todayVisitor();
}
