package com.fly.myblog.service.Impl;

import com.fly.myblog.dao.BlogDao;
import com.fly.myblog.entity.Blog;
import com.fly.myblog.queryvo.*;
import com.fly.myblog.service.BlogService;
import com.fly.myblog.util.MarkdownUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    /**
     admin中blog
     */
    @Override
    public List<BlogQuery> getAllBlog() {
        List<BlogQuery> blogList = blogDao.getAllBlog();
        return blogList;
    }

    @Override
    public int save(Blog blog) {

        return  blogDao.save(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteBlog(id);
    }

    @Override
    public BlogShow findBlogById(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public int updateBlog(BlogShow blogShow) {
        return blogDao.updateBlog(blogShow);
    }

    @Override
    public List<BlogQuery> search(String  title) {
        return blogDao.search(title);
    }


    /**
     * 首页blog
     */
    @Override
    public List<FirstPageBlog> getFirstPageBlog() {
        return blogDao.getFirstPageBlog();
    }

    @Override
    public List<RecommendBlog> getRecommendedBlog() {
        return blogDao.getRecommendedBlog();
    }

    @Override
    public List<FirstPageBlog> getSearchBlog(String query) {
        return blogDao.getSearchBlog(query);
    }





    @Override
    public Integer getBlogTotal() {
        return blogDao.getBlogTotal();
    }

    @Override
    public Integer getBlogViewTotal() {
        return blogDao.getBlogViewTotal();
    }

    @Override
    public Integer getBlogCommentTotal() {
        return blogDao.getBlogCommentTotal();
    }

    @Override
    public Integer getBlogMessageTotal() {
        return blogDao.getBlogMessageTotal();
    }

    @Override
    public DetailedBlog getDetailedBlog(Long id) throws NotFoundException {
        DetailedBlog detailedBlog = blogDao.getDetailedBlog(id);
        if (detailedBlog == null){
            throw new com.fly.myblog.NotFoundException("该博客不存在");
        }
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //文章访问数量自增
        blogDao.updateViews(id);
        //文章评论数量更新
        blogDao.getCommentCountById(id);
        return detailedBlog;
    }

    @Override
    public List<FirstPageBlog> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }
}
