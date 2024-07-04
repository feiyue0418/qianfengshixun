package com.ziru.service.impl;

import com.ziru.bean.Member;
import com.ziru.mapper.MemberMapper;
import com.ziru.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2024-07-03
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

}
