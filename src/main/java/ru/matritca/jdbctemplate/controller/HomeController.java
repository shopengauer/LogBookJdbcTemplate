package ru.matritca.jdbctemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vasiliy on 17.08.2015.
 */
@Controller
public class HomeController{

    @RequestMapping(value = "/")
    public String home(){
        return "index";
    }




}
