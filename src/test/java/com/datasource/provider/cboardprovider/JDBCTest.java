package com.datasource.provider.cboardprovider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class JDBCTest {

    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/test";
        Connection conn = DriverManager.getConnection(url, "root", "root");
//        System.out.println(conn);
        Statement stat = conn.createStatement();
//        System.out.println(stat);
        ResultSet resultSet = stat.executeQuery("show create table employee");
        while (resultSet.next()){
            String ddl = resultSet.getString("Create Table");
            System.out.println(ddl);
            String name = resultSet.getString("Table");
            System.out.println(name);
        }
        // 5. 释放资源
        stat.close();
        conn.close();
    }

}

