package com.sol.admin.modules.system.service.impl;

import com.sol.admin.modules.system.entity.ChatMessage;
import com.sol.admin.modules.system.mapper.ChatMessageMapper;
import com.sol.admin.modules.system.service.IChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天消息表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-02-22
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements IChatMessageService {

}
