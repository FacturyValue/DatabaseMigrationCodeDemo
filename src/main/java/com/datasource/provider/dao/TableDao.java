package com.datasource.provider.dao;

import com.datasource.provider.domain.Categorys;

import java.util.List;
import java.util.Map;

public interface TableDao {
    //查询表名及表中文注释
    List<Map<String,Object>> queryTableNameAndComment();
    //根据表名查询表所有数据
    List<Map<String,Object>> queryTableData(String tableName);
    //使用jdbc查询表结构定义语句DDL
    String queryDDLStatement(String tableName);

    //插入之前查询的数据
    void insetTableDao(String name, List<Map<String, Object>> maps);
}
