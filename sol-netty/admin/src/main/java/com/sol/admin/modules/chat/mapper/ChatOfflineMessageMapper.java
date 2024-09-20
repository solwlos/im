package com.sol.admin.modules.chat.mapper;

import com.sol.admin.modules.chat.entity.ChatOfflineMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 离线消息表 Mapper 接口
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@Mapper
public interface ChatOfflineMessageMapper extends BaseMapper<ChatOfflineMessage> {

}
