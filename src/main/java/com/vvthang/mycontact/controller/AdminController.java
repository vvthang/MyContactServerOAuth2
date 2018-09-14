package com.vvthang.mycontact.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String admin() {
        logger.debug("IN - admin()");
        logger.debug("OUT - admin()");
        return "admin";
    }
    
}
