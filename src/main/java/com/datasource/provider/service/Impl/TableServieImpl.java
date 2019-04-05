package com.datasource.provider.service.Impl;

import com.datasource.provider.dao.TableDao;
import com.datasource.provider.service.TableServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TableServieImpl  implements TableServie {
    @Autowired
    private TableDao tableDao;

    /**
     * 使用jdbc操作查询所有表名及注释
     * @return
     */
    @Override
    public List<Map<String, Object>> queryTableNameAndComment() {
        List<Map<String,Object>> categorys=tableDao.queryTableNameAndComment();
        return categorys;
    }
    /**
     * 使用jdbc操作查询表结构语句
     * @return
     */
    @Override
    public String queryDDLStatement(String name) {
        //jdbc
        return tableDao.queryDDLStatement(name);
    }

    /**
     * 根据表名查询表所有记录
     * @param name
     * @return
     */
    @Override
    public List<Map<String, Object>> queryTableData(String name) {
        //jdbc
        return tableDao.queryTableData(name);
    }

    /**
     * jdbc操作获取listInfo
     * @param name
     * @return
     */
    @Override
    public List<Map<String,Object>> getJDBCListInfo(String name) {
        List<Map<String,Object>> list = new ArrayList<>(16);
        Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
        //获取表所有记录
        List<Map<String,Object>> tableData=tableDao.queryTableData(name);
        //将查询出来的数据变成DML语句
        map.put("tableData",tableData);

        ///
        //获取DDl语句
        String tableDDL=tableDao.queryDDLStatement(name);
        map.put("tableDDL",tableDDL);
        map.put("tableName",name);
        list.add(map);
        return list;
    }

    /**
     * 获取表名和表中文注释
     * @return
     */
    @Override
    public List<Map<String, Object>> getJDBCTableNameAndComment() {
        return tableDao.queryTableNameAndComment();
    }

    /**
     * 插入之前查询的数据
     * @param name
     */
    @Override
    public void insertTableData(String name, List<Map<String, Object>> maps) {
        tableDao.insetTableDao(name,maps);
    }
}
