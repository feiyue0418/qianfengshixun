package com.ziru.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ADMINS")
@ApiModel("管理员实体类")
public class Admins {

    //数据库中主键自动增长
    @ApiModelProperty("管理员ID")
    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    @TableField(value = "USERNAME")
    private String username;

    @ApiModelProperty("密码")
    @TableField(value = "PASSWORD")
    private String password;

    @ApiModelProperty("删除状态 1 删除 0未删除")
    @TableField(value = "DEL")
    private String del;

    @ApiModelProperty("角色")
    @TableField(value = "ROLE")
    private String role;
}
