package com.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nowcoder on 2016/7/10.
 */
@Controller
public class IndexController {
    
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index( ) {
        return "hello spring boot";
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", required = false) String key) {
        return String.format("Profile Page of %s / %d, t:%d k: %s", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
    public String template( ) {
        return "home";
    }



}
