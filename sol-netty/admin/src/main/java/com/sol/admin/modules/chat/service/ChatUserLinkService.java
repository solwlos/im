package com.sol.admin.modules.chat.service;

import com.sol.admin.modules.chat.entity.ChatUserLink;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 关系表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-02-22
 */
public interface ChatUserLinkService extends IService<ChatUserLink> {

    List<ChatUserLink> getChatUserLink(String userId);

    Boolean addChatUserLink(ChatUserLink link);

    Boolean updateChatUserLink(ChatUserLink link);

    Boolean deleteChatUserLink(String userId);
}
