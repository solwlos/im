package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.modules.system.entity.ChatUserLink;
import com.sol.admin.modules.system.mapper.ChatUserLinkMapper;
import com.sol.admin.modules.system.service.IChatUserLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关系表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-02-22
 */
@Service
public class ChatUserLinkServiceImpl extends ServiceImpl<ChatUserLinkMapper, ChatUserLink> implements IChatUserLinkService {

    @Resource
    ChatUserLinkMapper mapper;

    @Override
    public List<ChatUserLink> getChatUserLink(String userId) {
        QueryWrapper<ChatUserLink> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(ChatUserLink::getUserId,userId)
            .eq(ChatUserLink::getIsDeleted,0);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Boolean addChatUserLink(ChatUserLink link) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        link.setCreateTime(timestamp);
        link.setUpdateTime(timestamp);
        return mapper.insert(link) == 1;
    }

    @Override
    public Boolean updateChatUserLink(ChatUserLink link) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        link.setUpdateTime(timestamp);
        return mapper.updateById(link) == 1;
    }

    @Override
    public Boolean deleteChatUserLink(String id) {
        return mapper.deleteById(id) == 1;
    }
}
