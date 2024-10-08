package com.sol.admin.modules.chat.service.impl;

import com.sol.admin.modules.chat.entity.ChatGroupUser;
import com.sol.admin.modules.chat.mapper.ChatGroupUserMapper;
import com.sol.admin.modules.chat.service.ChatGroupUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群聊用户表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@Service
public class ChatGroupUserServiceImpl extends ServiceImpl<ChatGroupUserMapper, ChatGroupUser> implements ChatGroupUserService {

    @Resource
    ChatGroupUserMapper mapper;

    @Override
    public List<ChatGroupUser> getGroupUser(String id) {
        return mapper.getGroupUser(id);
    }

    @Override
    public Boolean delGroupUser(String groupId, String userId) {
        return mapper.delGroupUser(groupId,userId);
    }

}
