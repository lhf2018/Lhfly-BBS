package service;

import pojo.Tab;

import java.util.List;

public interface TabService {
    List<Tab> getAllTabs();
    Tab getByTabNameEn(String tabName);
}
