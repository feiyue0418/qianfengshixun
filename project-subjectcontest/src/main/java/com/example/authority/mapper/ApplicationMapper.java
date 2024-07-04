package com.example.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.authority.entity.Application;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
public interface ApplicationMapper extends BaseMapper<Application> {

    @Select("SELECT g.name ,count(*) as total FROM application a left join game g \n" +
            "on g.id = a.game_id where g.name is not null group by a.game_id")
    List<Map<String, Object>> echart();
}
