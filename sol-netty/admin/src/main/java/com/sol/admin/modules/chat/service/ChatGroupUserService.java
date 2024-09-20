package com.sol.admin.modules.system.service;

import com.sol.admin.modules.system.entity.ChatGroupUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 群聊用户表 服务类
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
public interface ChatGroupUserService extends IService<ChatGroupUser> {


    List<ChatGroupUser> getGroupUser(String groupId);

    Boolean delGroupUser(String groupId,String userId);
}
