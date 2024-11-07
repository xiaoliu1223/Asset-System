package com.cg.test.am.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.cg.test.am.mapper.SysAssetTypeMapper;
import com.cg.test.am.model.SysApplicationRecord;
import com.cg.test.am.model.SysAssetType;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysApplicationRecordService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.utils.MultipartFileToFile;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysApplicationRecordListResp;
import com.cg.test.am.vo.response.SysApplicationRecordResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@Api(tags = "资产申请")
@RestController
@RequestMapping("/sysApplicationRecord")
public class SysApplicationRecordController {

    @Resource
    SysApplicationRecordService sysApplicationRecordService;

    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Resource
    HttpServletRequest httpServletRequest;

    @Value("${chor.fileSystem}")
    private String filePath;

    @ApiOperation(value = "新增资产申请", notes = "管理端API")
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysApplicationRecordReq req) {

        SysApplicationRecord sysApplicationRecord = new SysApplicationRecord();
        CopyUtils.copyProperties(req, sysApplicationRecord);
        sysApplicationRecordService.save(sysApplicationRecord);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "资产申请列表", notes = "管理端API")
    @GetMapping("/list")
    public ChorResponse<Map<String, Object>> list(SysApplicationRecordListReq req) {

        return ChorResponseUtils.success(sysApplicationRecordService.list(req));

    }

    @ApiOperation(value = "资产申请审核列表", notes = "管理端API", response = SysApplicationRecordListResp.class)
    @GetMapping("/auditList")
    public ChorResponse<Map<String, Object>> auditList(AuditListReq req) {

        return ChorResponseUtils.success(sysApplicationRecordService.auditList(req));

    }

    @ApiOperation(value = "根据id查询申请资产详情", notes = "管理端API")
    @GetMapping("/{id}")
    public ChorResponse<SysApplicationRecordResp> find(@PathVariable Long id) {

        return ChorResponseUtils.success(sysApplicationRecordService.find(id));

    }

    @ApiOperation(value = "修改资产申请详情", notes = "管理端API")
    @PutMapping("/{id}")
    public ChorResponse<Void> modify(@RequestBody SysApplicationRecordReq req, @PathVariable Long id) {

        sysApplicationRecordService.modify(req, id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "主动撤销资产申请", notes = "管理端API")
    @PutMapping("/undo/{id}")
    public ChorResponse<Void> undo(@PathVariable Long id) {

        sysApplicationRecordService.undo(id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "审核资产申请", notes = "web、applet API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "审核对象不存在、或已经撤销申请"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/audit/{id}")
    public ChorResponse<Void> audit(@RequestBody SysAuditLogReq req, @PathVariable Long id) {

        sysApplicationRecordService.audit(req, id);
        return ChorResponseUtils.success();
    }

    @ApiOperation(value = "批量通过资产申请", notes = "web、applet API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "审核对象不存在、或已经撤销申请"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/batchAudit")
    public ChorResponse<Void> batchAudit(@RequestBody SysAuditLogBatchReq req) {

        String token = httpServletRequest.getHeader("Authorization");
        Integer auditBy = JwtUtil.getUserIdByToken(token);
        sysApplicationRecordService.batchAudit(req, auditBy);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "查询资产申请审批流列表", notes = "applet API", response = FlowPathResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "查询对象不存在、或已经撤销申请"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/audit/flow/{id}")
    public ChorResponse<List<FlowPathResp>> auditFlowList(@PathVariable Long id) {

        List<FlowPathResp> resp = sysApplicationRecordService.auditFlowList(id);
        return ChorResponseUtils.success(resp);

    }

    @ApiOperation(value = "下载添加资产申请模板", notes = "applet API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/downloadAddTemp")
    public void downloadAddTemp(HttpServletResponse response) {
        SysAssetTypeListReq req = new SysAssetTypeListReq();
        req.setPid(3);//批量导入只允许低值易耗品
        List<SysAssetType> list = sysAssetTypeMapper.list(req, 0, 10000);
        InputStream fileIn = null;
        HSSFWorkbook wb=null;
        try {
            fileIn = new FileInputStream(filePath+"/assets-manager/excelTemp/assetAdd.xls");
            wb = new HSSFWorkbook(fileIn);
            HSSFSheet listSheet = wb.createSheet("ShtDictionary");
            for(int i = 0;i<list.size();i++){
                String cellValue = list.get(i).getId()+"、"+list.get(i).getName();
                listSheet.createRow(i).createCell(0).setCellValue(cellValue);
            }

            HSSFSheet sheet = wb.getSheetAt(0);
            // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
            CellRangeAddressList regions = new CellRangeAddressList(3,255, 2, 2);
            String cell = "\"ShtDictionary!$A$1:$A$"+(list.size()-1)+"\"";
            // 这句话是关键 引用ShtDictionary 的单元格
            DVConstraint constraint = DVConstraint.createFormulaListConstraint("INDIRECT("+cell+ ")");
            // 数据有效性对象
            HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
            sheet.addValidationData(data_validation_list);

            OutputStream output = null;
            try {
                output = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+java.net.URLEncoder.encode("资产管理申请表", "UTF-8")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
        } catch (Exception e) {
            System.out.println("发生错误，优先检查"+filePath+"/assets-manager/excelTemp/assetAdd.xls"+"是否存在");
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "上传资产新增", notes = "applet API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/uploadAddTemp")
    public ChorResponse uploadAddTemp(@RequestPart("file") MultipartFile file) {
        ExcelReader reader = null;
        File file1=null;
        try{
            file1 = MultipartFileToFile.multipartFileToFile(file);
            reader = ExcelUtil.getReader(file1);
            List<SysApplicationRecord> sysApplicationRecord = reader.read(1,3,SysApplicationRecord.class);
            if(sysApplicationRecord!=null && sysApplicationRecord.size()>0){
                String relateJobNo = null;
                for(SysApplicationRecord sar : sysApplicationRecord){
                    String[] assetTypeTemp = sar.getAssetTypeTemp().split("、");
                    sar.setAssetType(Long.valueOf(assetTypeTemp[0]));
                    sar.setAssetName(assetTypeTemp[1]);
                    sar.setRelateJobNo(relateJobNo);
                    relateJobNo=sysApplicationRecordService.save(sar);
                }
            }
        } catch (IOException e) {
            System.out.println("发生错误，文件上传错误");
            e.printStackTrace();
        }finally {
            if(reader!=null){
                reader.close();
            }
            MultipartFileToFile.delteTempFile(file1);
        }
        return ChorResponseUtils.success();
    }

    @ApiOperation(value = "根据relateJobNo查询申请资产详情", notes = "管理端API")
    @GetMapping("/getByRelateJobNo/{relateJobNo}")
    public ChorResponse<List<SysApplicationRecordResp>> find(@PathVariable String relateJobNo) {

        return ChorResponseUtils.success(sysApplicationRecordService.getByRelateJobNo(relateJobNo));

    }
}
