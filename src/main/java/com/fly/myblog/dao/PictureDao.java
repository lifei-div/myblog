package com.fly.myblog.dao;

import com.fly.myblog.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureDao {

    //查看所有图片
    List<Picture> listPicture();

    //添加图片
    int savePicture(Picture picture);

    //根据ID查询图片
    Picture getPictureById(Long id);

    //编辑修改想图片
    int updatePicture(Picture picture);

    //删除照片
    void deletePicture(Long id);
}
