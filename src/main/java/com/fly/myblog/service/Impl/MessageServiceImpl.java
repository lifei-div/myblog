package com.fly.myblog.service.Impl;

import com.fly.myblog.dao.MessageDao;
import com.fly.myblog.entity.Message;
import com.fly.myblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    //存放迭代找出的所有子代集合
    private List<Message> tempReplys = new ArrayList<>();


    /**
     * 查询留言
     * @return 留言消息
     */
    @Override
    public List<Message> listMessage() {
        //查询所有父节点
        List<Message> messages = messageDao.findByParentIdNotNull(Long.parseLong("-1"));
        for (Message message : messages) {
            Long id = message.getId();
            String parentNickname1 = message.getNickname();

            //查询出子留言
            List<Message> childMessages = messageDao.findByParentIdNull(id);
            combineChildren(childMessages, parentNickname1);
            message.setReplyMessages(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return messages;
    }

    /**
     * 查出 子留言
     * @param childMessages 所有子留言
     * @param parentNickname1 父留言的姓名
     */
    private void combineChildren(List<Message> childMessages, String parentNickname1) {
    //判断是否有一级回复
        if (childMessages.size() > 0){
            //循环找出子留言的id
            for (Message childMessage :childMessages) {
                String parentNickname = childMessage.getNickname();
                childMessage.setParentNickname(parentNickname1);
                tempReplys.add(childMessage);
                Long childId = childMessage.getId();
                //查询二级以及所有子集回复
                recursively(childId, parentNickname);
            }
        }

    }

    /**
     * 循环迭代找出子集回复
     * @param childId 子留言的id
     * @param parentNickname1 子留言的姓名
     */
    private void recursively(Long childId, String parentNickname1) {
        List<Message> replayMessages = messageDao.findByReplayId(childId);
        if (replayMessages.size() > 0){
            for (Message replayMessage : replayMessages) {
                String parentNickname = replayMessage.getNickname();
                replayMessage.setParentNickname(parentNickname1);
                Long replayId = replayMessage.getId();
                tempReplys.add(replayMessage);
                //循环迭代找出子集回复
                recursively(replayId,parentNickname);
            }
        }
    }

    @Override
    public int saveMessage(Message message) {
        message.setCreateTime(new Date());
        return messageDao.saveMessage(message);
    }

    @Override
    public void deleteMessage(Long id) {
        messageDao.deleteMessage(id);
    }
}
