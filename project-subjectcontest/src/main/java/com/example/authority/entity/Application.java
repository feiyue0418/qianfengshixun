package com.example.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
@Getter
@Setter
@ApiModel(value = "Application对象", description = "")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("报名用户")
    private Integer userId;

    @ApiModelProperty("赛事id")
    private Integer gameId;

    @ApiModelProperty("审核回复")
    private String reply;

    @ApiModelProperty("审核状态 0：待审核，1：审核成功 2：审核拒绝")
    private Integer status;


}
