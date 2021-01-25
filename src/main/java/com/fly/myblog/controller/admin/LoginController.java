package com.fly.myblog.controller.admin;

import com.fly.myblog.entity.User;
import com.fly.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     *  跳转到登陆页面
     * @return  返回登陆页面
     */
    @GetMapping("/admin")
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 登陆校验
     * @param username： 用户名
     * @param password： 密码
     * @param session： session域
     * @param attributes：返回消息页面
     * @return 登录成功跳转登录成功页面，登录失败返回登录页面
     */
    @PostMapping("admin/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }


    /**
     * 注销
     * @param session
     * @return
     */
    @GetMapping("/admin/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
