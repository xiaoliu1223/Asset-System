package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysMessageService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.mapper.SysMessageMapper;
import com.cg.test.am.vo.response.SysMessageResp;
import com.cg.test.am.model.SysMessage;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.SysMessageListReq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMessageServiceImpl implements SysMessageService {

    @Resource
    SysMessageMapper sysMessageMapper;

    @Override
    public Map<String, Object> list(SysMessageListReq req, Long userId) {

        try {

            Page<SysMessage> page = new Page<>(req.getCurrent(), req.getLimit());
            QueryWrapper<SysMessage> query = new QueryWrapper<>();
            query.eq("to_user", userId);

            if (null != req.getType()) {
                query.eq("type", req.getType());
            }
            query.orderByDesc("create_time");
            Page<SysMessage> sysMessagePage = sysMessageMapper.selectPage(page, query);
            long total = sysMessagePage.getTotal();
            List<SysMessage> records = sysMessagePage.getRecords();
            Map<String, Object> map = new HashMap<>();
            map.put("total_record", total);
            map.put("data", records);
            return map;

        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysMessageResp detail(Long userId, Long id) {

        try {

            // 判断消息是否存在
            SysMessage sysMessageInfo = sysMessageMapper.selectOne(new QueryWrapper<SysMessage>()
                    .eq("id", id).eq("to_user", userId).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            if (null == sysMessageInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            // 标记已读
            sysMessageInfo.setStatus(ParamsConstant.MESSAGE_STATUS_FOR_READ);
            sysMessageInfo.setUpdateTime(System.currentTimeMillis());
            sysMessageMapper.updateById(sysMessageInfo);

            // 数据处理
            SysMessageResp sysMessageResp = new SysMessageResp();
            CopyUtils.copyProperties(sysMessageInfo, sysMessageResp);
            return sysMessageResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void allMark(Long userId) {

        try {

            sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                    .set("status", ParamsConstant.MESSAGE_STATUS_FOR_READ).eq("to_user", userId).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).eq("status", ParamsConstant.MESSAGE_STATUS_FOR_UNREAD));

        } catch (Exception e) {

            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Long userId, Long id) {

        try {

            // 删除对象是否存在
            SysMessage sysMessageInfo = sysMessageMapper.selectOne(new QueryWrapper<SysMessage>()
                    .eq("id", id).eq("to_user", userId).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            if (null == sysMessageInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            sysMessageInfo.setDelFlag(ParamsConstant.DEL_FLAG_DELETED);
            sysMessageMapper.updateById(sysMessageInfo);

        } catch (ChorBizException e) {

            throw e;

        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public Integer stats(Long userId) {

        int count = sysMessageMapper.selectCount(new QueryWrapper<SysMessage>()
                .eq("to_user", userId).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).eq("status", ParamsConstant.MESSAGE_STATUS_FOR_UNREAD));

        return count;
    }
}
