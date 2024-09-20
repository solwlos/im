package com.sol.admin.modules.chat.service;

import com.sol.admin.modules.chat.entity.ChatOfflineMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 离线消息表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
public interface ChatOfflineMessageService extends IService<ChatOfflineMessage> {

    List<ChatOfflineMessage> getChatOfflineMessage(String userId);
}
