package com.sol.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.logging.log4j.message.Message;

@Mapper
public interface ChatMessageMapper  extends BaseMapper<Message> {
}
