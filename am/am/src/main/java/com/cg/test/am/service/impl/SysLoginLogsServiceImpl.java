package com.cg.test.am.service.impl;

import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.model.SysLoginLog;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysLoginLogsService;
import com.cg.test.am.mapper.SysLoginLogMapper;
import com.cg.test.am.vo.request.SysLoginLogListReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysLoginLogsServiceImpl implements SysLoginLogsService {

    @Resource
    SysLoginLogMapper sysLoginLogMapper;
    @Override
    public Map<String, Object> list(SysLoginLogListReq req) {

        try {
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysLoginLogMapper.count(req);
            List<SysLoginLog> list = sysLoginLogMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }
}
