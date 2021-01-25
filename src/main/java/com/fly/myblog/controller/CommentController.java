package com.fly.myblog.controller;

import com.fly.myblog.entity.Comment;
import com.fly.myblog.entity.User;
import com.fly.myblog.queryvo.DetailedBlog;
import com.fly.myblog.service.BlogService;
import com.fly.myblog.service.CommentService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 评论控制器
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;


    @Value("${comment.avatar}")
    private String avatar;

    //查询评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    //新增评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session,Model model){
        Long blogId = comment.getBlogId();
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            //设置头像
            comment.setAvatar(avatar);
        }
        if (comment.getParentComment().getId() != null){
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.saveComment(comment);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);
        return "blog :: commentList";

    }

    //删除评论
    @GetMapping("/comment/{blogId}/{id}/delete")
    public String delete(@PathVariable Long blogId,@PathVariable Long id,
                         Comment comment,Model model) throws NotFoundException {
        commentService.deleteComment(comment,id);
        DetailedBlog detailedBlog = blogService.getDetailedBlog(blogId);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("blog", detailedBlog);
        model.addAttribute("comments", comments);
        return "blog";
    }

}
