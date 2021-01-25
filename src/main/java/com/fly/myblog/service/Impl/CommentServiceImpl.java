package com.fly.myblog.service.Impl;

import com.fly.myblog.dao.BlogDao;
import com.fly.myblog.dao.CommentDao;
import com.fly.myblog.entity.Comment;
import com.fly.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;

    //存在迭代找出的所有子代集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //查询出所有父节点
        List<Comment> comments = commentDao.findByBlogIdParentIdNull(blogId, Long.parseLong("-1"));
        for (Comment comment: comments) {
            Long id = comment.getId();
            String parentNickname1 = comment.getNickname();
            //根据父id查询一级评论
            List<Comment> childComents = commentDao.findByBlogIdParentIdNotNull(blogId, id);
            combineChildren(blogId,childComents,parentNickname1);

            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    /**
     * 查询出子评论
     * @param blogId
     * @param childComents 所有子评论
     * @param parentNickname1 父评论姓名
     */
    private void combineChildren(Long blogId, List<Comment> childComents, String parentNickname1) {
        //判断是否有一级子评论
        if (childComents.size() > 0){
            //循环找出子评论的id
            for (Comment childComment: childComents) {
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(parentNickname);
                tempReplys.add(childComment);
                Long childId = childComment.getId();
                //查询子二级评论
                recursively(blogId,childId,parentNickname);
            }
        }

    }

    /**
     * 循环迭代找出子集回复
     * @param blogId
     * @param childId 子评论id
     * @param parentNickname1 子评论姓名
     */
    private void recursively(Long blogId, Long childId, String parentNickname1) {
        //根据一级评论的id找的子二级评论
        List<Comment> replayComments = commentDao.findByBlogIdAndReplayId(blogId, childId);

        if (replayComments.size() > 0){
            for (Comment replayComment: replayComments) {
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }

    //新增评论
    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        int comments = commentDao.saveComment(comment);
        //文章评论计数
        blogDao.getCommentCountById(comment.getBlogId());
        return comments;
    }

    //删除评论
    @Override
    public void deleteComment(Comment comment, Long id) {
        commentDao.deleteComment(id);
        blogDao.getCommentCountById(comment.getBlogId());
    }
}
