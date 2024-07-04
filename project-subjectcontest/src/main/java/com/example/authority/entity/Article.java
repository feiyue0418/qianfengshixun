package com.example.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class Article {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String type;

    private String user;

    private String url;

    private String content;

    private Date createTime;


}
