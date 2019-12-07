package com.learn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(){
        logger.info("index页");
        return "Index";
    }

}
