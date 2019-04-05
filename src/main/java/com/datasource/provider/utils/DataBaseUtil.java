package com.datasource.provider.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * @author siniclolas
 * @since
 */

public class DataBaseUtil {


    private  String driverClassName;
    private  String url;
    private  String username;
    private  String password;

    public DataBaseUtil() {
    }

        public static Connection getConnection(String url, String username, String password,String driverClassName){

            try {
                Class.forName(driverClassName);
                return DriverManager.getConnection(url,username,password);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;

        }
        public static void close(ResultSet rs,PreparedStatement ps,Connection con){
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally{
                    if(ps!=null){
                        try {
                            ps.close();
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }finally{
                            if(con!=null){
                                try {
                                    con.close();
                                } catch (SQLException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
}
