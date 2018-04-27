package com.wenda.controller;

import com.wenda.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录相关控制器
 *
 * @author evilhex
 *         2017/12/10
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param model
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(path = { "/reg/" }, method = { RequestMethod.POST })
    public String register(Model model, @RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam(value = "next", required = false) String next, HttpServletResponse response) {

        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";

            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        } catch (Exception e) {
            return "login";
        }

    }

    /**
     * 登录
     *
     * @param model
     * @param username
     * @param password
     * @param rememberme
     * @param response
     * @return
     */
    @RequestMapping(path = { "/login" }, method = { RequestMethod.POST })
    public String register(Model model, @RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
            @RequestParam(value = "next", required = false) String next, HttpServletResponse response) {
        try {

            Map<String, String> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";

            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        } catch (Exception e) {
            return "login";
        }

    }

    /**
     * 登录注册
     *
     * @return
     */
    @RequestMapping(path = { "/reglogin" }, method = { RequestMethod.GET })
    public String register(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";
    }

    /**
     * 注销
     *
     * @param ticket
     * @return
     */
    @RequestMapping(path = { "/logout" }, method = { RequestMethod.GET })
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

}
