package com.cg.test.am.controller;

import com.cg.test.am.model.SysMessage;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysMessageService;
import com.cg.test.am.vo.request.SysMessageListReq;
import com.cg.test.am.vo.response.SysMessageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "消息模块")
@RestController
@RequestMapping("/message")
public class SysMessageController {

    @Resource
    SysMessageService sysMessageServiceImpl;


    @ApiOperation(value = "消息列表", notes = "管理端API", response = SysMessage.class)
    @GetMapping("/{userId}/list")
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysMessageListReq req, @PathVariable Long userId) {

        return ChorResponseUtils.success(sysMessageServiceImpl.list(req, userId));
    }

    @ApiOperation(value = "消息详情", notes = "管理端API", response = SysMessageResp.class)
    @GetMapping("/detail/{id}")
    public ChorResponse<SysMessageResp> detail(@RequestParam(value = "userId") Long userId, @PathVariable Long id) {

        SysMessageResp resp = sysMessageServiceImpl.detail(userId, id);
        return ChorResponseUtils.success(resp);
    }


    @ApiOperation(value = "消息全部标记已读", notes = "管理端API")
    @PutMapping("/mark/{userId}")
    public  ChorResponse<Void> allMark(@PathVariable Long userId) {

        sysMessageServiceImpl.allMark(userId);
        return ChorResponseUtils.success();
    }

    @ApiOperation(value = "删除指定消息", notes = "管理端API")
    @DeleteMapping("/{id}")
    public ChorResponse<Void> remove(@RequestParam(value = "userId") Long userId, @PathVariable Long id) {
        sysMessageServiceImpl.remove(userId, id);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "未读消息数量", notes = "管理端API")
    @GetMapping("/stats/{userId}")
    public ChorResponse<Integer> stats(@PathVariable Long userId) {
        Integer unreadNum = sysMessageServiceImpl.stats(userId);
        return ChorResponseUtils.success(unreadNum);
    }


}
