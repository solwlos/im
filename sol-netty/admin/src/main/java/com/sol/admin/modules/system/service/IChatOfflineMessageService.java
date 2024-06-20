package com.sol.admin.modules.system.service;

import com.sol.admin.modules.system.entity.ChatOfflineMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sol.admin.modules.system.entity.ChatUserLink;
import java.util.List;

/**
 * <p>
 * 离线消息表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
public interface IChatOfflineMessageService extends IService<ChatOfflineMessage> {

    List<ChatOfflineMessage> getChatOfflineMessage(String userId);
}
