package com.sol.admin.modules.system.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MenuDTO {
    @Schema(description = "主键id")
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


    @Schema(description = "创建时间")
    private Timestamp createTime;

    @Schema(description = "修改时间")
    private Timestamp updateTime;

    private List<MenuDTO> children;
}
