package com.cg.test.am.controller;

import com.cg.test.am.model.SysAssetType;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.request.SysAssetTypeListReq;
import com.cg.test.am.vo.response.SysAssetTypeResp;
import com.cg.test.am.service.SysAssetTypeService;
import com.cg.test.am.vo.request.SysAssetTypeReq;
import com.cg.test.am.vo.response.SysTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "资产类别")
@RestController
@RequestMapping("/sysAssetType")
public class SysAssetTypeController {
    @Resource
    SysAssetTypeService sysAssetTypeService;


    @ApiOperation(value = "添加资产类别", notes = "管理端API")
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysAssetTypeReq req) {

        SysAssetType sysAssetType = new SysAssetType();
        CopyUtils.copyProperties(req, sysAssetType);
        sysAssetTypeService.save(sysAssetType);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "资产类别列表", notes = "管理端API")
    @GetMapping("/list")
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysAssetTypeListReq req) {

        return ChorResponseUtils.success(sysAssetTypeService.list(req));

    }

    @ApiOperation(value = "根据id查询资产类别详情", notes = "管理端API")
    @GetMapping("/{id}")
    public ChorResponse<SysAssetTypeResp> find(@PathVariable Long id) {

        return ChorResponseUtils.success(sysAssetTypeService.find(id));

    }

    @ApiOperation(value = "修改资产类别详情", notes = "管理端API")
    @PutMapping("/{id}")
    public ChorResponse<Void> modify(@RequestBody SysAssetTypeReq req, @PathVariable Long id) {

        sysAssetTypeService.modify(req, id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "删除资产类别", notes = "管理端API")
    @DeleteMapping("/{id}")
    public ChorResponse<Void> remove(@PathVariable Long id) {

        sysAssetTypeService.remove(id);
        return ChorResponseUtils.success();

    }


    @ApiOperation(value = "资产类别树状图数据", notes = "管理端API")
    @RequestMapping(value = "/treeData", method = RequestMethod.GET)
    @ResponseBody
    public ChorResponse<List<SysTree>> getOrgTreeData() {
        //查出数据
        return ChorResponseUtils.success(sysAssetTypeService.treeList());
    }

}
