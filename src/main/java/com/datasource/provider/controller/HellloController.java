package com.datasource.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class HellloController {
    @RequestMapping("/hello")
    public String  index(){
        return "Hello World";
    }
}
