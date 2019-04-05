package com.datasource.provider.mapper;
import com.datasource.provider.domain.Categorys;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TableMapper {
    //获取表名和表中文注释
    @Results({
            @Result(property = "TABLE_NAME", column = "TABLE_NAME"),
            @Result(property = "TABLE_COMMENT", column = "TABLE_COMMENT")
    })
    @Select("select TABLE_NAME name,TABLE_COMMENT comment from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<Categorys> listTable();
    //根据表名获取表所有记录
    List<Map<String,Object>> findByTableName(@Param("tableName") String tableName);
    //获取表结构语句
    @Select("show create table ${tableName}")
    List<Map<String,Object>> findDDLStatement(@Param("tableName") String tableName);

}
