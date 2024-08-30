package com.sol.admin.modules.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
//@Schema(name = "UserDTO", description = "用户信息")
public class UserInfo {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "角色id")
    private Long roleId;

    @Schema(description = "角色名")
    private String roleName;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "真实名字")
    private String realname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "性别 null 未知，0男，1女")
    private Byte sex;

    @Schema(description = "头像")
    private String image;

    @Schema(description = "描述、签名")
    private String description;

    @Schema(description = "乐观锁")
    private Integer version;

    @Schema(description = "是否删除，0未删除、1删除")
    @TableLogic(delval = "0", value = "1")
    private Byte isDeleted;

    @Schema(description = "创建时间")
    private Timestamp createTime;

    @Schema(description = "修改时间")
    private Timestamp updateTime;

}
