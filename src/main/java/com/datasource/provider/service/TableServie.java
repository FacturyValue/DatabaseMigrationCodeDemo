package com.datasource.provider.service;

import java.util.List;
import java.util.Map;


public interface TableServie {

    //jdbc操作查询tableName和tableComment
    List<Map<String,Object>> queryTableNameAndComment();
    //jdbc操作查询DDL结构语句
    String queryDDLStatement(String name);
    //jdbc操作根据表名查询表所有记录
    List<Map<String,Object>> queryTableData(String name);

    List<Map<String,Object>> getJDBCListInfo(String name);
    // 获取表名和中文注释
    List<Map<String,Object>> getJDBCTableNameAndComment();


    //插入之前查询出来的数据
    void insertTableData(String name, List<Map<String, Object>> maps);
}
