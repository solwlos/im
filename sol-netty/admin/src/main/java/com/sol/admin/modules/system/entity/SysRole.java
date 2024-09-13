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
 * 角色表
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@Getter
@Setter
@TableName("sys_role")
@Schema(name = "SysRole", description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "名字")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "是否删除，0未删除、1删除")
    @TableLogic(value = "0", delval = "1")
    private Byte isDeleted;

    @Schema(description = "创建时间")
    private Timestamp createTime;

    @Schema(description = "修改时间")
    private Timestamp updateTime;
}
