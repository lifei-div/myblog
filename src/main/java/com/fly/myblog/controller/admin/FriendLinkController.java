package com.fly.myblog.controller.admin;

import com.fly.myblog.entity.FriendLink;
import com.fly.myblog.service.FriendLinkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    //查询所有友链
    @GetMapping("/friendlinks")
    private String freind(Model model, @RequestParam(defaultValue = "1" ,value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,5);
        List<FriendLink> friendLinks = friendLinkService.listFriendLink();
        PageInfo<FriendLink> pageInfo = new PageInfo<>(friendLinks);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/friendlinks";
    }

    //跳转到友链新增页面
    @GetMapping("/friendlinks/input")
    public String input(Model model){
        model.addAttribute("friendlink",new FriendLink());
        return "admin/friendlinks-input";
    }

    //友链新增
    @PostMapping("/friendlinks")
    public String post(@Valid FriendLink friendLink, BindingResult result, RedirectAttributes attributes){

        FriendLink friendLinkByBlogAddress = friendLinkService.getFriendLinkByBlogAddress(friendLink.getBlogaddress());
        if (friendLinkByBlogAddress != null) {
            attributes.addFlashAttribute("message", "不能添加相同的网址");
            return "redirect:/admin/friendlinks/input";
        }

        if (result.hasErrors()){
            return "admin/friendlinks-input";
        }
        friendLink.setCreateTime(new Date());
        int f = friendLinkService.saveFriendLink(friendLink);
        if (f==0){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");

        }

        return "redirect:/admin/friendlinks";

    }

    //跳转到友链修改页面
    @GetMapping("/friendlinks/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("friendlink",friendLinkService.getFriendLinkById(id));
        return "admin/friendlinks-input";

    }

    //编辑
    @PostMapping("/friendlinks/{id}")
    public String edit(@Valid FriendLink friendLink, RedirectAttributes attributes){
        int i = friendLinkService.updateFriendLink(friendLink);
        if (i == 0){
            attributes.addFlashAttribute("message","编辑失败");
        }else {
            attributes.addFlashAttribute("message","编辑成功");
        }
        return "redirect:/admin/friendlinks";
    }




    //删除友链
    @GetMapping("/friendlinks/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        friendLinkService.deleteFriendLink(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/friendlinks";

    }
}
