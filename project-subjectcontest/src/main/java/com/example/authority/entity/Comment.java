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
import java.util.List;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-05-01
 */
@Getter
@Setter
@ApiModel(value = "Comment对象", description = "评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("评论人")
    private Integer userId;

    @ApiModelProperty("父级ID")
    private Integer parentId;

    @ApiModelProperty("根节点ID")
    private Integer rootId;

    @ApiModelProperty("评论时间")
    private Date time;

    @ApiModelProperty("关联ID")
    private Integer relationId;

    @ApiModelProperty("对应模块")
    private String module;

    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String headerUrl;

    @TableField(exist = false)
    private String replyUser;  // 回复给谁 就是谁的名称
}
