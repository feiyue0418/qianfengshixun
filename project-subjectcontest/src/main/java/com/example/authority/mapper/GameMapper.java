package com.example.authority.mapper;

import com.example.authority.entity.Game;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 赛事表 Mapper 接口
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
public interface GameMapper extends BaseMapper<Game> {

    @Update("update game set count = count + 1 where id = #{id}")
    void updateCount(@Param("id") Integer id);
}
