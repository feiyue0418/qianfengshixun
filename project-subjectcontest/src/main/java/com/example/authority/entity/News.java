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
 * 资讯表
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
@Getter
@Setter
@ApiModel(value = "News对象", description = "资讯表")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("浏览次数")
    private Integer count;

    @ApiModelProperty("分类")
    private String type;

    @ApiModelProperty("创建时间")
    private Date createTime;


}
