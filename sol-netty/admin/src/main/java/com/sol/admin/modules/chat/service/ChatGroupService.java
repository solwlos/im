package com.sol.admin.modules.chat.service;

import com.sol.admin.modules.chat.entity.ChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 群聊表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
public interface ChatGroupService extends IService<ChatGroup> {

    List<ChatGroup> getGroupByUserID(String userId);

    Boolean addChatGroup(ChatGroup chatGroup);

    Boolean deleteChatGroup(String id);

    Boolean updateChatGroup(ChatGroup chatGroup);
}
