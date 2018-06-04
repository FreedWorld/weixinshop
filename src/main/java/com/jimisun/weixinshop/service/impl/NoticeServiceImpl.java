package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.dao.NoticeDao;
import com.jimisun.weixinshop.entity.Notice;
import com.jimisun.weixinshop.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 11:58 2018-05-24
 * @Modified By:
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Notice findNew() {
        Notice notice = noticeDao.getOne(1);
        return notice;
    }

    @Override
    public Notice save(Notice notice) {
        return noticeDao.save(notice);
    }
}
