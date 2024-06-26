package com.sol.admin.modules.system.service;

import com.sol.admin.modules.system.entity.ChatUserLink;
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
public interface IChatUserLinkService extends IService<ChatUserLink> {

    List<ChatUserLink> getChatUserLink(String userId);

    Boolean addChatUserLink(ChatUserLink link);

    Boolean updateChatUserLink(ChatUserLink link);

    Boolean deleteChatUserLink(String userId);
}
