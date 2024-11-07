package com.cg.test.am.vo;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * 常量
 */
public class ParamsConstant {

    /** 数据状态 --> 默认 value: 0 **/
    static public final Integer DEL_FLAG_DEFAULT = 0;

    /** 数据状态 --> 默认 value: 0 **/
    static public final Long DEL_FLAG_DEFAULT_VALUE = 0L;

    /** 数据状态 --> 已删除 value: -1 **/
    static public final Integer DEL_FLAG_DELETED = -1;

    /** 权限类型 --> 菜单 value: 1 **/
    static public final Integer PERMISSION_TYPE_FOR_MENU = 1;

    /** 权限类型 --> 按钮 value: 2 **/
    static public final Integer PERMISSION_TYPE_FOR_BUTTION = 2;

    /** =========================资产审批类型====================================**/
    /** 资产申请审批 value: 1 **/
    static public final int AUDIT_TYPE_FOR_APPLICATION = 1;

//    /** 资产采购 value: 2 **/
//    static public Integer AUDIT_TYPE_FOR_PURCHASE = 2;

    /** 资产领用审批 value: 3 **/
    static public final int AUDIT_TYPE_FOR_RECEIVE = 3;

    /** 资产归还审批 value: 4 **/
    static public final int AUDIT_TYPE_FOR_RETURN = 4;

    /** 资产核销审批 value: 5 **/
    static public final int AUDIT_TYPE_FOR_CHARGE_OFF = 5;


    /** =========================审批状态====================================**/
    /** 审核默认状态 value: 0 **/
    static public final Integer AUDIT_STATUS_DEFAULT = 0;

    /** 审核通过 value: 1 **/
    static public final Integer AUDIT_STATUS_APPROVE = 1;

    /** 审核驳回 value: -1 **/
    static public final Integer AUDIT_STATUS_REJECT = -1;

    /** 审核中 value: -2 **/
    static public final Integer AUDIT_STATUS_ING = -2;

    /** 主动撤回 value: 2 **/
    static public final Integer AUDIT_STATUS_BACKOUT = 2;

    /** 审批流程--> 结束 value: -1 **/
    static public final Integer AUDIT_FLOWPAHT_FINISH = -1;



    /** =========================资产库存状态====================================**/
    /** 资产库存状态--> 在库 value: 1 **/
    static public final Integer ASSET_INVENTORY_STATUS_IN = 1;

    /** 资产库存状态--> 出库 value: 2 **/
    static public final Integer ASSET_INVENTORY_STATUS_OUT = 2;

    /** 资产库存状态--> 报废 value: 3 **/
    static public final Integer ASSET_INVENTORY_STATUS_SCRAP = 3;

    /** =========================资产状态====================================**/
    /** 资产状态--> 完好 value: 0 **/
    static public final Integer ASSET_STATUS_GOOD = 0;

    /** 资产状态--> 可继续使用 value: 1 **/
    static public final Integer ASSET_STATUS_ON = 1;

    /** 资产状态--> 报废 value: 2 **/
    static public final Integer ASSET_STATUS_OFF = 2;


    /** =========================资产锁定状态====================================**/

    /** 资产锁定状态 --> 未锁定 value: 0 **/
    static public final Integer ASSET_LOCKED_STATUS_OFF = 0;

    /** 资产锁定状态 --> 锁定 value: 1**/
    static public final Integer ASSET_LOCKED_STATUS_ON = 1;

    /** =========================资产类别====================================**/
    /** 资产类别--> 低值易耗品 value: 3 **/
    static public final Long ASSET_TYPE_CONSUMABLES = 3L;

    /** 资产类别--> 办公资产 value: 2 **/
    static public final Long ASSET_TYPE_OFFICE = 2L;

    /** 资产类别--> 固定资产 value: 1 **/
    static public final Long ASSET_TYPE_FIXED = 1L;

    /** =========================部门====================================**/
    /** 部门--> 综合管理部 value: 5 **/
    static public final Long DEPARTMENT_GENERAL_MANAGEMENT = 5L;

    /** =========================岗位====================================**/

    /** 岗位 --> 总经理 **/
    static public final String POST_TYPE_GENERAL_MANAGER = "1";

    /** 岗位 --> 分管领导 **/
    static public final List<String> POST_TYPE_MANAGER_LEADER = asList("53", "50", "5", "51");

    /** 岗位 --> 部门经理 **/
    static public final List<String> POST_TYPE_DEPARTMENT_MANAGER = asList("53", "15", "16", "17", "18", "19", "52");

    /** 岗位 --> 项目经理 **/
    static public final List<String> POST_TYPE_PROJECT_MANAGER = asList("26", "27", "29", "30", "31", "32", "33", "34", "35", "36", "37");

    /** 岗位 --> 资产管理员 **/
    static public final List<String> POST_TYPE_ASSET_ADMIN = asList("38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49");

    /** 岗位 --> 集团资产管理员（综合部资产管理员）**/
    static public final String POST_GROUP_ASSET_MANAGER = "38";

    /** 岗位 --> 综合部资产管理员 **/
    static public final String POST_GENERAL_ASSET_MANAGER = "59";

    /** 岗位 --> 综合部分管领导--行政总监**/
    static public final String POST_GROUP_MANAGER_LEADER = "53";

    /** =========================资产领用状态====================================**/

    /** 资产领用状态--已领用 value: 3 **/
    static public final int RECEIVE_STATUS_RECEIVE = 3;

    /** 资产领用状态--归还中 value: 4 **/
    static public final int RECEIVE_STATUS_INTHEBACK = 4;

    /** 资产领用状态--已归还= value: 5 **/
    static public final int RECEIVE_STATUS_RETURNED = 5;

    /** =========================资产领用状态====================================**/
    /** 资产归还状态--确认归还 **/
    static public final int CONFIRM_TO_RETURN_STATUS = 3;

    /** =========================资产核销状态====================================**/
    /** 资产核销状态 -- 已核销 **/
    static public final int STATUS_FOR_HAS_SCRAPED = 3;

    /** =========================资产采购状态====================================**/
    /** 资产采购状态--成功入库 value: 1 **/
    static public Integer BUY_SUCCESS= 1;

    /** =========================其他====================================**/
    /** 选中 -->  value: 1 **/
    static public final Integer SELECT = 1;

    /** 主动撤销 value: 2 **/
    static public final Integer UNDO = 2;


    /** =========================资产盘点====================================**/
    /** 未盘点 value：0**/
    static public final Integer NOINVENTORY = 0;

    /** 已盘点 value：1**/
    static public final Integer HAVEINVENTORY = 1;


    /** =========================消息状态====================================**/

    /** 消息状态：未读 value：0 **/
    static public final Integer MESSAGE_STATUS_FOR_UNREAD = 0;

    /** 消息状态：已读 value：1 **/
    static public final Integer MESSAGE_STATUS_FOR_READ = 1;

    /** =========================消息推送类型====================================**/

    /** 消息推送类型：申请 value：1 **/
    static public final int MESSAGE_TYPE_FOR_APPLICATION = 1;

    /** 消息推送类型：采购 value：2 **/
    static public final int MESSAGE_TYPE_FOR_PURCHASE = 2;

    /** 消息推送类型：领用 value：3 **/
    static public final int MESSAGE_TYPE_FOR_RECEIVE = 3;

    /** 消息推送类型：归还 value：4 **/
    static public final int MESSAGE_TYPE_FOR_RETURN = 4;

    /** 消息推送类型：报废 value：5 **/
    static public final int MESSAGE_TYPE_FOR_SCRAP = 5;


    /** =========================盘点状态====================================**/

    /** 盘点状态：开启 value：1 **/
    static public final Integer ASSET_CHECK_STATUS_ON = 1;

    /** 盘点状态：关闭 value：0 **/
    static public final Integer ASSET_CHECK_STATUS_OFF = 0;

    /** =========================低值易耗品抄送人====================================**/

    static public final Integer CONSUMABLES_COPYTO_USERID = 37;
}
