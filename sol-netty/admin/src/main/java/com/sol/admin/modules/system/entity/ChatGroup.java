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
 * 群聊表
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@Getter
@Setter
@TableName("chat_group")
@Schema(name = "ChatGroup", description = "群聊表")
public class ChatGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "群名字")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "群成员数量")
    private Integer number;

    @Schema(description = "是否删除 0：没有、1：删除")
    private Byte isDeleted;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;
}
