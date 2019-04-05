package com.datasource.provider.service;

import com.datasource.provider.domain.Categorys;

import java.util.List;
import java.util.Map;


public interface TableMapperServie {

    List<Categorys> listTable();
    //根据表名查询数据库表所有记录
    List<Map<String,Object>> findByTableName(String tableName);
    //查询DDL结构语句
    List<Map<String,Object>> findDDLStatement(String tableName);

    //获取所有表信息
    List<Map<String,Object>> getTableListInfo(String name);

    //获取表名和中文注释
    List<Categorys> getTableNameAndComment();
}
