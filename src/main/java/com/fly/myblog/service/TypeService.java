package com.fly.myblog.service;

import com.fly.myblog.entity.Type;

import java.util.List;

/**
 * 文章分类业务层接口
 */

public interface TypeService {

    //查询所以分类
    List<Type> getAllType();

    //根据名字添加（不能重复）
    Type getTypeByName(String name);

    //添加分类
    int saveType(Type type);

    //根据ID获取Type
    Type getTypeById(Long id);

    //修改
    int updateType(Type type);

    //删除
    void deleteType(Long id);


    //查询所有分类
    List<Type> getAllTypeAndBlog();
}
