package service;

import pojo.Visitor;

public interface VisitorService {
    boolean insert(Visitor visitor);
    int countVisitor();
    int todayVisitor();
}
