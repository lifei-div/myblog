package com.fly.myblog.service;

import com.fly.myblog.entity.Comment;

import java.util.List;

public interface CommentService {

    //根据博客id查询评论信息
    List<Comment> listCommentByBlogId(Long blogId);

    //添加保存评论
    int saveComment(Comment comment);

    //评论删除
    void deleteComment(Comment comment,Long id);
}
