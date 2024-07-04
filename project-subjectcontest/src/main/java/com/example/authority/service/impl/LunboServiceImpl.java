package com.example.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.entity.Lunbo;
import com.example.authority.mapper.LunboMapper;
import com.example.authority.service.LunboService;
import org.springframework.stereotype.Service;

@Service
public class LunboServiceImpl extends ServiceImpl<LunboMapper, Lunbo> implements LunboService {
}
