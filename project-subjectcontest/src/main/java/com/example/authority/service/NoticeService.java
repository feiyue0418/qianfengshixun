package com.example.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authority.entity.Notice;

import java.util.List;

public interface NoticeService extends IService<Notice> {
    List<Notice> findNewNoticeList();
}
