package com.fanyin.service.operation;

import com.fanyin.dto.operation.NoticeAddRequest;
import com.fanyin.dto.operation.NoticeEditRequest;
import com.fanyin.dto.operation.NoticeQueryRequest;
import com.fanyin.model.operation.SystemNotice;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:11
 */
public interface SystemNoticeService {

    /**
     * 添加公告
     * @param request 前台参数
     */
    void addNotice(NoticeAddRequest request);

    /**
     * 更新公告
     * @param request 前台参数
     */
    void updateNotice(NoticeEditRequest request);

    /**
     * 删除公告
     * @param request 前台参数
     */
    void deleteNotice(NoticeEditRequest request);

    /**
     * 分页查询公告信息
     * @param request 查询条件
     * @return 结果集
     */
    PageInfo<SystemNotice> getByPage(NoticeQueryRequest request);
}

