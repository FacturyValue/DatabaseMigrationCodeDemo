package com.datasource.provider.service.Impl;

import com.datasource.provider.domain.Categorys;
import com.datasource.provider.mapper.TableMapper;
import com.datasource.provider.service.TableMapperServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TableMapperServieImpl implements TableMapperServie {
    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<Categorys> listTable() {

        return tableMapper.listTable();
    }

    @Override
    public List<Map<String, Object>> findByTableName(String tableName) {
        return tableMapper.findByTableName(tableName);
    }

    @Override
    public List<Map<String,Object>> findDDLStatement(String tableName) {
        return tableMapper.findDDLStatement(tableName);
    }

    @Override
    public List<Map<String, Object>> getTableListInfo(String name) {
        List<Map<String,Object>> list = new ArrayList<>(16);
        StringBuffer stringBuffer =new StringBuffer(16);
        Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
        //获取表所有记录,记录全部转换成insert sql语句
        List<Map<String,Object>> insertList = tableMapper.findByTableName(name);
//        List<Map<String,Object>> tableData = tableMapper.findByTableName(name);
        //遍历查询到所有的记录
        for (Map<String,Object> jsonMap:insertList) {

            stringBuffer.append(" insert into ");
            stringBuffer.append(name);
            stringBuffer.append("(");
            for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
                String field = entry.getKey();
                stringBuffer.append(field).append(",");
            }
            stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
            stringBuffer.append(")");
            stringBuffer.append(" VALUES ").append("(");
            for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
                Object value = entry.getValue();
                if (null!=value){
                    stringBuffer.append("'").append(value).append("'").append(",");
                }else {
                    stringBuffer.append(value).append(",");
//                    stringBuffer.append(" '' ").append(",");
                }
            }
            stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
            stringBuffer.append(") ");
            stringBuffer.append("<>");
        }
        stringBuffer.delete(stringBuffer.length()-2,stringBuffer.length());
        String sql = stringBuffer.toString();
        String[] split = sql.split("<>");
        //转换为insert 语句
        List<String> tableData = Arrays.asList(split);
        map.put("tableData",tableData);
        //获取DDl语句
        List<Map<String,Object>> ddlStatement =  tableMapper.findDDLStatement(name);
        Map<String, Object> objectMap = ddlStatement.get(0);
        map.put("tableDDL",objectMap.get("Create Table"));
        map.put("tableName",name);
        list.add(map);
        return list;
    }

    @Override
    public List<Categorys> getTableNameAndComment() {
        return tableMapper.listTable();
    }
}
