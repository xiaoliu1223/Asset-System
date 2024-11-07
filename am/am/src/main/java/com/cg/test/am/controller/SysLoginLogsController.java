package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysLoginLogsService;
import com.cg.test.am.vo.request.SysLoginLogListReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "登录日志")
@RestController
@RequestMapping("/syslogs")
public class SysLoginLogsController {

    @Resource
    SysLoginLogsService sysLogsService;

    @ApiOperation(value = "登录记录列表", notes = "管理端API")
    @GetMapping("/list")
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysLoginLogListReq req) {

        return ChorResponseUtils.success(sysLogsService.list(req));

    }
}
