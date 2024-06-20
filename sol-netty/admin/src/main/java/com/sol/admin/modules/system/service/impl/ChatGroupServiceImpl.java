package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.modules.system.entity.ChatGroup;
import com.sol.admin.modules.system.entity.ChatGroupUser;
import com.sol.admin.modules.system.mapper.ChatGroupMapper;
import com.sol.admin.modules.system.service.IChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群聊表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@Service
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroup> implements IChatGroupService {

    @Resource
    ChatGroupMapper mapper;

    @Override
    public List<ChatGroup> getGroupByUserID(String userId) {
        QueryWrapper<ChatGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(ChatGroup::getId,userId)
            .eq(ChatGroup::getIsDeleted,0);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Boolean addChatGroup(ChatGroup chatGroup) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        chatGroup.setCreateTime(timestamp);
        chatGroup.setUpdateTime(timestamp);
        return mapper.insert(chatGroup) == 1;
    }

    @Override
    public Boolean deleteChatGroup(String id) {
        return mapper.deleteById(id) == 1;
    }

    @Override
    public Boolean updateChatGroup(ChatGroup chatGroup) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        chatGroup.setUpdateTime(timestamp);
        return mapper.updateById(chatGroup) == 1;
    }
}
