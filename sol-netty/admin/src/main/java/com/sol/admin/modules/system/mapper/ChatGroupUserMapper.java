package com.sol.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.modules.system.entity.ChatGroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 群聊用户表 Mapper 接口
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
public interface ChatGroupUserMapper extends BaseMapper<ChatGroupUser> {

    default List<ChatGroupUser> getGroupUser(String id){
        QueryWrapper<ChatGroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ChatGroupUser::getGroupId,id)
                .eq(ChatGroupUser::getIsDeleted,0);
        return selectList(queryWrapper);
    }

    default Boolean delGroupUser(String groupId, String userId){
        QueryWrapper<ChatGroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ChatGroupUser::getGroupId,groupId)
                .eq(ChatGroupUser::getUserId,userId)
                .eq(ChatGroupUser::getIsDeleted,0);
        return delete(queryWrapper) == 1;
    }
}
