package com.sol.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 关系表
 * </p>
 *
 * @author sol
 * @since 2024-02-22
 */
@Getter
@Setter
@TableName("chat_user_link")
@Schema(name = "ChatUserLink", description = "关系表")
public class ChatUserLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "朋友id")
    private Long friendId;

    @Schema(description = "类型0、普通，1、亲密")
    private Integer type;

    @Schema(description = "是否删除，0未删除、1删除")
    private Byte isDeleted;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;
}
