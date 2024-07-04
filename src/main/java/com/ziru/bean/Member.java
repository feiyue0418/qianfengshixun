package com.ziru.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author bruce
 * @since 2024-07-03
 */
@ApiModel(value="Member对象", description="")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员账号")
    private String uname;

    @ApiModelProperty(value = "会员密码")
    private String upass;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员手机号")
    private String phone;

    @ApiModelProperty(value = "会员头像")
    private String photos;

    @ApiModelProperty(value = "会员注册日期")
    private String createtime;

    @ApiModelProperty(value = "是否删除 1删除 0未删除")
    private String del;

    @ApiModelProperty("角色")
    @TableField(value = "ROLE")
    private String role;

}
