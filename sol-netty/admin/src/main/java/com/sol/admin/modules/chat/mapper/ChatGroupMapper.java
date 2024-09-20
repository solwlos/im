package com.sol.admin.modules.chat.mapper;

import com.sol.admin.modules.chat.entity.ChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 群聊表 Mapper 接口
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@Mapper
public interface ChatGroupMapper extends BaseMapper<ChatGroup> {

}
