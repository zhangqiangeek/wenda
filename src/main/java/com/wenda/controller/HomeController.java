package com.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by evilhex on 2017/11/18.
 */
@Controller
public class HomeController {

    @RequestMapping(path = {"/","/index"})
    public String index(){
        return "index";
    }
}
