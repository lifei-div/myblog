package com.fly.myblog.dao;

import com.fly.myblog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论持久层接口
 */
@Mapper
@Repository
public interface CommentDao {

    /**
     * 查询父级评论
     */
    List<Comment> findByBlogIdParentIdNull(@Param("blogId")Long blogId,@Param("blogParentId") Long blogParentId);

    /**
     * 根据父级id一级回复
     */
    List<Comment> findByBlogIdParentIdNotNull(@Param("blogId") Long blogId, @Param("id") Long id);

    /**
     * 根据子回复的id循环迭代查询出所有子集回复
     */
    List<Comment> findByBlogIdAndReplayId(@Param("blogId") Long blogId,@Param("childId") Long childId);


    //添加一个评论
    int saveComment(Comment comment);

    //删除评论
    void deleteComment(Long id);
}
