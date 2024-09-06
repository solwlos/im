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
 * 权限表
 * </p>
 *
 * @author sol
 * @since 2024-08-21
 */
@Getter
@Setter
@TableName("sys_permission")
@Schema(name = "SysPermission", description = "权限表")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "权限名")
    private String name;

    @Schema(description = "权限路径")
    private String interfacePath;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "类型")
    private Integer type;

    @Schema(description = "状态：0 正常、1 已下架")
    private Integer status;

    @Schema(description = "是否删除")
    @TableLogic(value = "0", delval = "1")
    private Byte isDeleted;

    @Schema(description = "乐观锁")
    private Integer version;

    @Schema(description = "创建时间")
    private Timestamp createdTime;

    @Schema(description = "更新时间")
    private Timestamp updatedTime;
}
