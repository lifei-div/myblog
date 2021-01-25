package com.fly.myblog.service.Impl;

import com.fly.myblog.dao.FriendLinkDao;
import com.fly.myblog.entity.FriendLink;
import com.fly.myblog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendLinkDao friendLinkDao;

    @Override
    public List<FriendLink> listFriendLink() {
        return friendLinkDao.listFriendLink();
    }

    @Override
    public int saveFriendLink(FriendLink friendLink) {
        return friendLinkDao.saveFriendLink(friendLink);
    }

    @Override
    public FriendLink getFriendLinkByBlogAddress(String blog) {
        return friendLinkDao.getFriendLinkByBlogAddress(blog);
    }

    @Override
    public FriendLink getFriendLinkById(Long id) {
        return friendLinkDao.getFriendLinkById(id);
    }

    @Override
    public int updateFriendLink(FriendLink friendLink) {
        int i = friendLinkDao.updateFriendLink(friendLink);
        return i;
    }

    @Override
    public void deleteFriendLink(Long id) {
            friendLinkDao.deleteFriendLink(id);
    }
}
