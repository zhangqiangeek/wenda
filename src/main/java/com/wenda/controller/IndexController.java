package com.wenda.controller;

import com.wenda.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zq on 2016/7/10.
 */
@Controller
public class IndexController {

    @RequestMapping(path = { "/", "/index" }, method = { RequestMethod.GET })
    @ResponseBody
    public String index(HttpSession session) {
        return "hello spring boot " + session.getAttribute("msg");
    }

    @RequestMapping(path = { "/profile/{groupId}/{userId}" })
    @ResponseBody
    public String profile(@PathVariable("userId") int userId, @PathVariable("groupId") String groupId,
            @RequestParam(value = "type", defaultValue = "1") int type, @RequestParam(value = "key", required = false) String key) {
        return String.format("Profile Page of %s / %d, t:%d k: %s", groupId, userId, type, key);
    }

    /**
     * 模板
     *
     * @param model
     * @return
     */
    @RequestMapping(path = { "/vm" }, method = { RequestMethod.GET })
    public String home(Model model) {

        User user = new User("zhang");
        model.addAttribute("value", "value");
        model.addAttribute("user", user);

        return "home";
    }

    /**
     * request 和response
     *
     * @param model
     * @param response
     * @param request
     * @param session
     * @param sessionId
     * @return
     */
    @RequestMapping(path = { "/request" }, method = { RequestMethod.GET })
    @ResponseBody
    public String template(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session,
            @CookieValue("JSESSIONID") String sessionId) {
        StringBuilder sb = new StringBuilder();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                sb.append("Cookie:" + cookie.getName() + "value:" + cookie.getValue() + "<br>");
            }
        }

        sb.append(sessionId);
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getCookies() + "<br>");
        sb.append(request.getAuthType() + "<br>");
        sb.append(request.getRequestURL() + "<br>");
        sb.append(request.getSession() + "<br>");

        response.addHeader("test", "hello its test");
        response.addCookie(new Cookie("userName", "userName"));

        return sb.toString();
    }

    /**
     * 301跳转
     *
     * @param code
     * @param session
     * @return
     */
    @RequestMapping(path = { "/redirect/{code}" }, method = { RequestMethod.GET })
    public RedirectView redirect(@PathVariable("code") int code, HttpSession session) {
        session.setAttribute("msg", "jump from redirect!");
        RedirectView redirectView = new RedirectView("/", true);
        if (code == 301) {
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return redirectView;
    }

    @RequestMapping(path = "/admin", method = { RequestMethod.GET })
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if (key.equals("admin")) {
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error" + e.getMessage();
    }

}
