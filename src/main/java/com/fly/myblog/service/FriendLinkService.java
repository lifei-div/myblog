package com.fly.myblog.service;

import com.fly.myblog.entity.FriendLink;

import java.util.List;

public interface FriendLinkService {
    //查询友链管理链表
    List<FriendLink> listFriendLink();

    //新增友链
    int saveFriendLink(FriendLink friendLink);

    //根据网址查询友链
    FriendLink getFriendLinkByBlogAddress(String blog);

    //根据ID查询友链
    FriendLink getFriendLinkById(Long id);

    //编辑修改友链
    int updateFriendLink(FriendLink friendLink);

    //删除友链
    void deleteFriendLink(Long id);
}
