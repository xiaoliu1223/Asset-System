package com.cg.test.am.service;

import com.cg.test.am.vo.response.SysMessageResp;
import com.cg.test.am.vo.request.SysMessageListReq;

import java.util.Map;

public interface SysMessageService {

    /**
     * 用户消息列表
     * @param req
     * @param userId
     * @return
     */
    Map<String, Object> list(SysMessageListReq req, Long userId);

    /**
     * 消息详情
     * @param userId
     * @param id
     * @return
     */
    SysMessageResp detail(Long userId, Long id);

    /**
     * 消息批量处理
     * @param userId
     */
    void allMark(Long userId);

    /**
     * 删除指定消息
     * @param userId
     * @param id
     */
    void remove(Long userId, Long id);

    /**
     * 统计消息未读数
     * @param userId
     * @return
     */
    Integer stats(Long userId);
}
