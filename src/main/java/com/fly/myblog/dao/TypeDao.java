package com.fly.myblog.dao;

import com.fly.myblog.entity.Type;
import com.fly.myblog.queryvo.FirstPageBlog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章分类持久层接口
 */
@Mapper
@Repository
public interface TypeDao {


    List<Type> getAllType();


    Type getTypeByName(String name);

    int saveType(Type type);

    Type getTypeById(Long id);

    int updateType(Type type);

    void deleteType(Long id);




    //查询所有分类
    List<Type> getAllTypeAndBlog();


}
