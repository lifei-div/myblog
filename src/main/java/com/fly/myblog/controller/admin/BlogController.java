package com.fly.myblog.controller.admin;

import com.fly.myblog.entity.Blog;
import com.fly.myblog.entity.Type;
import com.fly.myblog.entity.User;
import com.fly.myblog.queryvo.BlogQuery;
import com.fly.myblog.queryvo.BlogSearch;
import com.fly.myblog.queryvo.BlogShow;
import com.fly.myblog.service.BlogService;
import com.fly.myblog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;


    //查询所有Blog
    @RequestMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        //按照字段倒序
        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum, 10, orderBy);
        List<BlogQuery> blogs = blogService.getAllBlog();
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs";
    }

    //添加Blog，跳转到添加页面
    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    //博客新增
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        //设置blog中type
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        //设置blog中typeID熟悉
        blog.setTypeId(blog.getType().getId());
        //设置用户的Id
        blog.setUserId(blog.getUser().getId());
        //设置时间
        blog.setUpdateTime(new Date());
        int b = blogService.save(blog);

        if (b==0){
            redirectAttributes.addFlashAttribute("message","新增失败");
        }else {
            redirectAttributes.addFlashAttribute("message","新增成功");
        }

        return "redirect:/admin/blogs";
    }


    //删除博客
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id,RedirectAttributes attributes){

        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    //跳转到修改页面
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        BlogShow blog = blogService.findBlogById(id);
        List<Type> allType = typeService.getAllType();
        model.addAttribute("blog",blog);
        model.addAttribute("types",allType);
        return "admin/blogs-input";
    }

    //编辑
    @PostMapping("/blogs/{id}")
    public String edit(@Valid BlogShow blogShow,RedirectAttributes attributes){
        blogShow.setUpdateTime(new Date());
        int i = blogService.updateBlog(blogShow);
        if(i == 0){
            attributes.addFlashAttribute("message", "修改失败");
        }else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/blogs";



    }


//    //按照条件查询
//    @GetMapping("/blogs/search")
//    public String search(String title, Model model,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum){
//        System.out.println("1");
//        List<BlogQuery> blogs =  blogService.search(title);
//        PageHelper.startPage(pageNum,10);
//        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogs);
//        model.addAttribute("pageInfo",pageInfo);
//
//        return "admin/blogs :: blogList";
//    }



}
