package com.example.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value = "Awards对象", description = "")
public class Awards implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("获得奖项")
    private String awardName;

    @ApiModelProperty("获奖备注")
    private String description;

    @ApiModelProperty("赛事id")
    private Integer gameId;

    @ApiModelProperty("发奖时间")
    private Date createTime;


}
