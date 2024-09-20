package com.sol.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 群聊用户表
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@Getter
@Setter
@TableName("chat_group_user")
@Schema(name = "ChatGroupUser", description = "群聊用户表")
public class ChatGroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "编号 id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "群id")
    private Long groupId;

    private Long userId;

    private Integer lastAckMsgid;

    @Schema(description = "是否删除 0：没有、1：删除")
    @TableLogic(value = "0", delval = "1")
    private Byte isDeleted;

    @Schema(description = "创建时间")
    private Timestamp createTime;

    @Schema(description = "修改时间")
    private Timestamp updateTime;
}
