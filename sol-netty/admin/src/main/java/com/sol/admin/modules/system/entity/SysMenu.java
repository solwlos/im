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
 * 菜单表
 * </p>
 *
 * @author sol
 * @since 2024-02-20
 */
@Getter
@Setter
@TableName("sys_menu")
@Schema(name = "SysMenu", description = "菜单表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "名字")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "类型")
    private Integer type;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "父id")
    private Long pid;

    @Schema(description = "排序")
    private String sort;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "状态 0未禁用、1禁用")
    private Byte status;

    @Schema(description = "乐观锁")
    private Integer version;

    @Schema(description = "是否删除，0未删除、1删除")
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;

    @Schema(description = "创建时间")
    private Timestamp createTime;

    @Schema(description = "修改时间")
    private Timestamp updateTime;
}
