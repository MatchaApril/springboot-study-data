package com.example.springbootstudy03data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.rowset.spi.SyncResolver;
import java.util.List;
import java.util.Map;

@RestController
public class JdbcController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //查询数据库的所有信息(没有实体类，如何获取数据库中的东西？->>使用map)
    @GetMapping("/userList")
    public List<Map<String,Object>> userList(){
        String sql= "select * from user";
        List<Map<String, Object>> list_maps = jdbcTemplate.queryForList(sql);
        return list_maps;
    }
    //增加（每运行之后打开网页就会增加一条。注：仅运行不打开网页是不会增加的）
    @GetMapping("/addUser")
    public String addUser(){
        String sql = "insert into mybatis.user(id,name,pwd) values(6,'6','111') ";
        jdbcTemplate.update(sql);
        return "add-OK";
    }
    //修改(根据id修改)
    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") int id){
        String sql = "update mybatis.user set name=?,pwd=? where id= "+id;
        //封装
        Object[] objects = new Object[2];
        objects[0]="小明2";
        objects[1]="zzzzzz";

        jdbcTemplate.update(sql,objects);
        return "update-OK";
    }
    //删除(根据id修改)
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        String sql = "delete from mybatis.user where id=?";
        jdbcTemplate.update(sql,id);
        return "delete-OK";
    }
}
