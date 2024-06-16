package com.sol.admin.modules.system.service.impl;

import com.sol.admin.modules.system.entity.ChatOfflineMessage;
import com.sol.admin.modules.system.mapper.ChatOfflineMessageMapper;
import com.sol.admin.modules.system.service.IChatOfflineMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ChatOfflineMessageServiceImpl extends ServiceImpl<ChatOfflineMessageMapper, ChatOfflineMessage> implements IChatOfflineMessageService {

}
