package com.fly.myblog.service;

import com.fly.myblog.entity.Blog;
import com.fly.myblog.queryvo.*;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;

public interface BlogService {

    /**
     admin中blog
     */
    //查询所有博客
    List<BlogQuery> getAllBlog();

    //添加博客
    int save(Blog blog);

    //删除博客
    void deleteBlog(Long id);

    //根据ID查询博客
    BlogShow findBlogById(Long id);

    //修改博客
    int updateBlog(BlogShow blogShow);

    //按照类型，查询
    List<BlogQuery> search(String title);

    /**
     * 首页blog
     */

    //查询首页博客最新列表
    List<FirstPageBlog> getFirstPageBlog();
    //查询首页博客最新推荐列表
    List<RecommendBlog> getRecommendedBlog();

    //搜索博客列表
    List<FirstPageBlog> getSearchBlog(String query);

    //统计博客总数
    Integer getBlogTotal();

    //统计访问总数
    Integer getBlogViewTotal();

    //统计评论总数
    Integer getBlogCommentTotal();

    //统计留言总数
    Integer getBlogMessageTotal();

    //查询博客详情
    DetailedBlog getDetailedBlog(Long id) throws NotFoundException;


    //根据TypeId查询博客列表，显示在分类页面
    List<FirstPageBlog> getByTypeId(Long typeId);

}
