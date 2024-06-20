package com.sol.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sol.admin.modules.system.entity.ChatUserLink;
import com.sol.admin.modules.system.mapper.ChatUserLinkMapper;
import com.sol.admin.modules.system.service.IChatUserLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关系表 服务实现类
 * </p>
 *
 * @author sol
 * @since 2024-02-22
 */
@Service
public class ChatUserLinkServiceImpl extends ServiceImpl<ChatUserLinkMapper, ChatUserLink> implements IChatUserLinkService {

    @Resource
    ChatUserLinkMapper mapper;

    @Override
    public List<ChatUserLink> getChatUserLink(String id) {
//        QueryWrapper<ChatUserLink> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda()
//            .eq();
//        return mapper.selectList();
        return null;
    }
}
