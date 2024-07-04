package com.example.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.authority.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-05-01
 */
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("select * from comment where parent_id = #{pid}")
    List<Comment> selectListByPid(@Param("pid") Integer pid);

    List<Comment> selectTree(@Param("relationId") Integer relationId, @Param("module") String module);
}
