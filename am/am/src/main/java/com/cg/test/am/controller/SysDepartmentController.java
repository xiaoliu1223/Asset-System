package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.request.SysDeparymentListReq;
import com.cg.test.am.vo.request.SysDeparymentReq;
import com.cg.test.am.vo.response.SysDeparymentResp;
import com.cg.test.am.model.SysDepartment;
import com.cg.test.am.service.SysDepartmentService;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.SysTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "部门管理")
@RestController
@RequestMapping("/sysDepartment")
public class SysDepartmentController {

    @Resource
    SysDepartmentService sysDepartmentService;


    @ApiOperation(value = "添加部门", notes = "管理端API")
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysDeparymentReq req) {

        SysDepartment sysDepartment = new SysDepartment();
        CopyUtils.copyProperties(req, sysDepartment);
        sysDepartmentService.save(sysDepartment);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "部门列表", notes = "管理端API")
    @GetMapping("/list")
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysDeparymentListReq req) {

        return ChorResponseUtils.success(sysDepartmentService.list(req));

    }

    @ApiOperation(value = "根据id查询部门详情", notes = "管理端API")
    @GetMapping("/{id}")
    public ChorResponse<SysDeparymentResp> find(@PathVariable Long id) {

        return ChorResponseUtils.success(sysDepartmentService.find(id));

    }

    @ApiOperation(value = "修改部门详情", notes = "管理端API")
    @PutMapping("/{id}")
    public ChorResponse<Void> modify(@RequestBody SysDeparymentReq req, @PathVariable Long id) {

        sysDepartmentService.modify(req, id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "删除部门", notes = "管理端API")
    @DeleteMapping("/{id}")
    public ChorResponse<Void> remove(@PathVariable Long id) {

        sysDepartmentService.remove(id);
        return ChorResponseUtils.success();

    }


    @ApiOperation(value = "部门树状图数据",notes = "管理端API")
    @RequestMapping(value = "/treeData",method = RequestMethod.GET)
    @ResponseBody
    public ChorResponse<List<SysTree>>  getOrgTreeData(){
       return ChorResponseUtils.success(sysDepartmentService.treeList(ParamsConstant.DEL_FLAG_DEFAULT));
    }

    @ApiOperation(value = "用户部门列表", notes = "管理端API")
    @GetMapping("/listByUser")
    public ChorResponse<List<SysDepartment>> listByUser() {
        return ChorResponseUtils.success(sysDepartmentService.listByUser());
    }

}
