package com.sol.admin.modules.system.service;

import com.sol.admin.modules.system.entity.ChatGroup;
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
public interface IChatGroupService extends IService<ChatGroup> {

    List<ChatGroup> selectGroupByUserID(String userId);
}
