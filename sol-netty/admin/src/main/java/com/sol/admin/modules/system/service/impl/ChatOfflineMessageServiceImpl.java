package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.modules.system.entity.ChatOfflineMessage;
import com.sol.admin.modules.system.mapper.ChatOfflineMessageMapper;
import com.sol.admin.modules.system.service.ChatOfflineMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 离线消息表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@Service
public class ChatOfflineMessageServiceImpl extends ServiceImpl<ChatOfflineMessageMapper, ChatOfflineMessage> implements ChatOfflineMessageService {

    @Resource
    ChatOfflineMessageMapper mapper;

    @Override
    public List<ChatOfflineMessage> getChatOfflineMessage(String userId) {
        QueryWrapper<ChatOfflineMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(ChatOfflineMessage::getToUserId,userId)
            .eq(ChatOfflineMessage::getIsDeleted,0);
        return mapper.selectList(queryWrapper);
    }
}
