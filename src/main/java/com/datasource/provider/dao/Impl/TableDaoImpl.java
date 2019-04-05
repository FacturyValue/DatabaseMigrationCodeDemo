package com.datasource.provider.dao.Impl;

import com.datasource.provider.dao.TableDao;
import com.datasource.provider.domain.Categorys;
import com.datasource.provider.utils.DataBaseUtil;
import com.sun.javafx.scene.control.skin.FXVK;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@PropertySource({"classpath:/targetdatasource.properties"})
public class TableDaoImpl implements TableDao {

    @Value("${target.jdbc.url}")
    private String url;
    @Value("${target.jdbc.username}")
    private String username;
    @Value("${target.jdbc.password}")
    private String password;
    @Value("${target.jdbc.driver-class-name}")
    private String driverClassName;

    /**
     * 查询表名和中文表注释
     * @return
     */
    @Override
    public List<Map<String,Object>> queryTableNameAndComment() {
        Connection connection = DataBaseUtil.getConnection(url, username, password,driverClassName);
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sql="select TABLE_NAME name,TABLE_COMMENT comment from information_schema.TABLES where TABLE_SCHEMA=(select database())";
            ps = connection.prepareStatement(sql);//预处理
            rs = ps.executeQuery();
            //new 一个空的list集合用来存放查询结果
            List<Map<String ,Object>> list=new ArrayList<>();
            //获取结果集的列数
            int count = rs.getMetaData().getColumnCount();
            //对结果集遍历每一条数据是一个Map集合，列是k,值是v
            while(rs.next()){
                //一个空的map集合，用来存放每一行数据
                Map<String, Object> map=new HashMap<String, Object>();
                String name = rs.getString("name");
                String comment = rs.getString("comment");
                map.put("name",name);
                map.put("comment",comment);
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //调用close()方法关闭资源
            DataBaseUtil.close(rs,ps,connection);
        }
        return null;
    }

    /**
     * 根据表名查询所有表记录
     * @param tableName
     * @return
     */
    @Override
    public List<Map<String, Object>> queryTableData(String tableName) {
        Connection connection = DataBaseUtil.getConnection(url, username, password,driverClassName);
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sql="select * from "+tableName+" limit 0,5000";
            ps = connection.prepareStatement(sql);//预处理
            rs = ps.executeQuery();
            //new 一个空的list集合用来存放查询结果
            List<Map<String ,Object>> list=new ArrayList<>();
            //获取结果集的列数
            int count = rs.getMetaData().getColumnCount();
            //对结果集遍历每一条数据是一个Map集合，列是k,值是v
            while(rs.next()){
                //一个空的map集合，用来存放每一行数据
                Map<String, Object> map=new HashMap<String, Object>();
                for(int i=0;i<count;i++){
                    Object ob=null;
//                    Object ob=rs.getObject(i+1);//获取值
                    int columnType = rs.getMetaData().getColumnType(i+1);
                    switch (columnType){
                        case Types.NUMERIC:  ob=rs.getLong(i+1);
                        break;
                        case Types.INTEGER : ob=rs.getInt(i+1);
                        break;
                        case Types.DATE : ob=rs.getDate(i+1);
                        break;
                        case Types.BOOLEAN:ob=rs.getBoolean(i+1);
                        break;
                        case Types.TINYINT:ob=rs.getBoolean(i+1);
                        break;
                        case Types.DECIMAL:ob=rs.getLong(i+1);
                        break;
                        case Types.TIMESTAMP:ob=rs.getTimestamp(i+1);
                        default:ob=rs.getObject(i+1);
                        break;
                    }
                    String key = rs.getMetaData().getColumnName(i+1);//获取k即列名
                    map.put(key, ob);
                }
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //调用close()方法关闭资源
            DataBaseUtil.close(rs,ps,connection);
        }
        return null;
    }

    /**
     * 根据表名获取DDL语句
     * @param tableName
     * @return
     */
    @Override
    public String queryDDLStatement(String tableName) {
        Connection connection = DataBaseUtil.getConnection(url, username, password,driverClassName);
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sql="show create table "+tableName;
            ps = connection.prepareStatement(sql);//预处理
            rs = ps.executeQuery();
            String ddlStatement=null;
            //获取结果集的列数
            int count = rs.getMetaData().getColumnCount();
            //对结果集遍历每一条数据是一个Map集合，列是k,值是v
            while (rs.next()){
                 ddlStatement = rs.getString("Create Table");
            }
            return ddlStatement;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //调用close()方法关闭资源
            DataBaseUtil.close(rs,ps,connection);
        }
        return null;
    }

    /**
     * 插入之前查询的数据
     * @param name
     * @param maps
     */
    @Override
    public void insetTableDao(String name, List<Map<String, Object>> maps) {
        Connection connection = DataBaseUtil.getConnection(url, username, password,driverClassName);
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            StringBuffer stringBuffer =new StringBuffer(16);
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            for (Map<String,Object> jsonMap:maps) {

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
                        //将所有null值转换空字符串
                        stringBuffer.append(value).append(",");
                    }
                }
                stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
                stringBuffer.append(") ");
                stringBuffer.append("<>");
            }
            stringBuffer.delete(stringBuffer.length()-2,stringBuffer.length());
            String sql = stringBuffer.toString();
            String[] split = sql.split("<>");
            for (String insetSql:split){
                statement.addBatch(insetSql);
            }
            int[] counts = statement.executeBatch();
            for (int i : counts) {
                if (i == 0) {
                    connection.rollback();
                }
            }
            connection.setAutoCommit(true);   //在完成批量操作后恢复默认的自动提交方式，提高程序的可扩展性
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //调用close()方法关闭资源
            DataBaseUtil.close(rs,ps,connection);
        }
    }
}
