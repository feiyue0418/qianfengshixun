package com.ziru.bean;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2024-07-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="House对象", description="")
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "面积")
    private Double area;

    @ApiModelProperty(value = "租金")
    private Integer rent;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "图片 存储地址")
    private String photos;

    @ApiModelProperty(value = "是否删除 1  0 未删除")
    private String del;

}
