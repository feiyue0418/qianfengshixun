package com.example.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authority.entity.Comment;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-05-01
 */
public interface CommentService extends IService<Comment> {

    void deepDelete(Integer id);

    List<Comment> selectTree(Integer fid, String module);

    Integer selectCount(Integer fid, String module);

    void add(Comment comment);
}
