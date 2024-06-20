package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.modules.system.entity.ChatGroupUser;
import com.sol.admin.modules.system.mapper.ChatGroupUserMapper;
import com.sol.admin.modules.system.service.IChatGroupUserService;
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
public class ChatGroupUserServiceImpl extends ServiceImpl<ChatGroupUserMapper, ChatGroupUser> implements IChatGroupUserService {

    @Resource
    ChatGroupUserMapper mapper;

    @Override
    public List<ChatGroupUser> selectGroupUser(String id) {
        QueryWrapper<ChatGroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(ChatGroupUser::getId,id)
            .eq(ChatGroupUser::getIsDeleted,0);
        return mapper.selectList(queryWrapper);
    }
}
