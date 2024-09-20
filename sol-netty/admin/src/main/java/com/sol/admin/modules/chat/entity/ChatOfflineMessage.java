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
 * 离线消息表
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@Getter
@Setter
@TableName("chat_offline_message")
@Schema(name = "ChatOfflineMessage", description = "离线消息表")
public class ChatOfflineMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "发送用户id")
    private Long fromUserId;

    @Schema(description = "接受用户id")
    private Long toUserId;

    @Schema(description = "类型")
    private Integer type;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "是否删除 0：没有、1：删除")
    @TableLogic(value = "0", delval = "1")
    private Byte isDeleted;

    @Schema(description = "创建时间")
    private Timestamp createTime;

    @Schema(description = "修改时间")
    private Timestamp updateTime;
}
