package com.example.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 赛事表
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
@Getter
@Setter
@ApiModel(value = "Game对象", description = "赛事表")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("赛事名称")
    private String name;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("浏览次数")
    private Integer count;

    @ApiModelProperty("赛事分类")
    private String type;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("赛事开始时间")
    private String startTime;

    @ApiModelProperty("赛事结束时间")
    private String endTime;

    @TableField(exist = false)
    private Integer status;  //0:赛事未开始 1:赛事开始 2：赛事结束


}
