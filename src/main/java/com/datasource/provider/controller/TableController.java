package com.datasource.provider.controller;

import com.alibaba.fastjson.JSON;
import com.datasource.provider.domain.Categorys;
import com.datasource.provider.domain.ServiceResult;
import com.datasource.provider.service.TableMapperServie;
import com.datasource.provider.service.TableServie;
import com.datasource.provider.utils.DataUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/api/table")
public class TableController {
    @Autowired
    private TableServie tableServie;

    @Autowired
    private TableMapperServie tableMapperServie;

    /**
     * 返回catagory
     * 1.表名
     * 2.表中文注释
     */
    @RequestMapping(value = "/catagory",method = RequestMethod.GET)
    public ServiceResult getCatagory(){
        try {
            List<Categorys> Categorys=tableMapperServie.getTableNameAndComment();
//            List<Map<String,Object>> Categorys=tableServie.getJDBCTableNameAndComment();
           return ServiceResult.success(Categorys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServiceResult.exception("","系统异常");
    }

    /**
     * 返回数据库DDL语句
     * 返回表名
     * 返回表数据
     * @param
     * @return
     */
    @RequestMapping(value = "/listInfo/{name}",method = RequestMethod.GET)
    public  ServiceResult getTableInfo(@PathVariable("name") String name){
        try {
            if (DataUtils.isNullOrEmpty(name)){
                return ServiceResult.failure("","参数不正确");
            }
            List<Map<String,Object>> tableListInfo=tableMapperServie.getTableListInfo(name);
//          List<Map<String,Object>> tableListInfo=tableServie.getJDBCListInfo(name);
            return ServiceResult.success(tableListInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServiceResult.exception("","系统异常");
    }

    //自定义查询语句 实现转变为insert语句
    @RequestMapping(value = "/getInsert/{name}")
    public ServiceResult transInsert(@PathVariable("name") String name){
//        List<Map<String, Object>> maps = tableMapperServie.findByTableName(name);
       List<Map<String, Object>> maps = tableServie.queryTableData(name);
//        tableServie.insertTableData(name,maps);
        return ServiceResult.success(maps);
    }
}

