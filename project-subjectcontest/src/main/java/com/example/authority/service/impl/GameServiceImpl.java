package com.example.authority.service.impl;

import com.example.authority.entity.Game;
import com.example.authority.mapper.GameMapper;
import com.example.authority.service.GameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 赛事表 服务实现类
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements GameService {

    @Override
    public void updateCount(Integer id) {
        this.baseMapper.updateCount(id);
    }
}
