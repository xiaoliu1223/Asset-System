//获取当前路由的json
export const INIT_CURRENT_ROUTER = "init_current_router";
//用户的登录
export const GET_USER_NAME = "get_user_name"; //获取登录的用户信息
//获取菜单
export const GET_MENU = "get_menu"; //左侧菜单
// 权限管理
//菜单授权菜单列表
export const MENU_DOENLOAD = "menu_download";
//设置菜单
export const SETTING_MENU = "setting_menu";

//获取父级菜单
export const FATHER_ORGIN_LIST = "father_orgin_list";
//菜单管理列表
export const MENU_LIST = "menu_lsit";
//增加菜单
export const ADD_MENU = "add_menu";
//修改菜单
export const EDIT_MENU = "edit_menu";
//删除菜单
export const DELETE_MENU = "delete_menu";
//西站类型下拉菜单
export const ATTRIBUTES_OPTION = "attributes_option"; //列表
//资产类别
export const ATTRIBUTES_LIST = "attributes_list"; //列表
export const ATTRIBUTES_DELETE = "attributes_delete"; //删除
export const ATTRIBUTES_ADD = "attributes_add"; //添加
export const ATTRIBUTES_EDIT = "attributes_edit"; //编辑
//组织列表
export const ORGANIZATION_LIST = "organization_list"; //列表
export const ORGANIZATION_ADD = "organization_add"; //添加
export const ORGANIZATION_EDIT = "organization_edit"; //添加
export const ORGANIZATION_DELETE = "organization_delete"; //删除
//资产申请
export const PURCHASE_LIST = "purchase_list"; //列表
export const PURCHASE_ADD = "purchase_add"; //添加
export const PURCHASE_EDIT = "purchase_edit"; //添加
export const PURCHASE_DELETE = "purchase_delete"; //删除
export const PURCHASE_DETAIL = "purchase_detail"; //详情
export const PURCHASE_BACK = "purchase_back"; //撤回
//资产采购
export const BUY_LIST = "buy_list"; //列表
export const BUY_CONFIRM = "buy_confirm"; //确认采购

//资产库存
export const WAREHOUSING_LIST = "warehousing_list";
export const WAREHOUSING_ADD = "warehousing_add";
export const WAREHOUSING_EDIT = "warehousing_edit";
// export const WAREHOUSING_DELETE = "warehousing_delete"

//新增领用
export const ADD_GET = "add_get";
//新增核销
export const ADD_DELETE = "add_delete";
//资产归还
export const RETURN_LIST = "return_list";
export const RETURN_BACK = "return_back";
export const RETURN_CONFIRM = "return_confirm";
export const RETURN_DETAIL = "return_detail";

//资产领用
export const COLLECT_LIST = "collect_list";
export const COLLECT_BACK = "collect_back";
export const COLLECT_CONFIRM = "collect_confirm";
export const ADD_RETURN = "add_return";
export const COLLECT_DETAIL = "collect_detail";

//资产核销
export const WRITE_LIST = "write_list";
export const WRITE_BACK = "write_back";
export const WRITE_CONFIRM = "write_confirm";
export const WRITE_DETAIL = "write_detail";

//用户管理
export const USER_LIST = "user_list";
export const USER_DELE = "user_dele";
export const USER_ADD = "user_add";
export const USER_EDIT = "user_edit";

// 角色列表
export const ROLE_LIST = "role_list";
export const ADD_RULE = "add_rule";
export const EDIT_RULE = "edit_rule";
export const DELETE_RULE = "delete_rule";
//图表分析
export const ANALYSIS_STATE = "analysis_state";
export const ANALYSIS_BUY = "analysis_buy";
export const ANALYSIS_GET = "analysis_get";

//待办事项Auditapply 审批管理
export const TODO_APPLY_LIST = "todo_apply_list"; //申请审批
export const AUDIT_APPLY = "audit_apply"; //审核
export const TODO_GET_LIST = "todo_get_list"; //领用审批
export const AUDIT_GET = "audit_get"; //审核
export const TODO_BACK_LIST = "todo_back_list"; //归还审批
export const AUDIT_BACK = "audit_back"; //归还审批
export const TODO_DELETE_LIST = "todo_delete_list"; //核销审批
export const AUDIT_DELETE = "audit_delete"; //归还审批
export const TODO_DETAIL = "todo_detail"; //有relateJobNo的审批记录 查看明细
export const COMPLETE_DETAIL = "complete_detail";

//当前权限
export const AUTH = "auth";
//打印图片
export const PRINT_IMAGE = "print_image";
//导出
export const PRINT_EXCEL = "print_excel";
//验证密码
export const CHECK_PASSWORD = "check_password";
//修改密码
export const CONFIRM_NEW_PASSWORD = "conform_new_password";
// 单位列表
export const GET_UNITS = "get_units";

// 资产名称列表
export const GET_ASSET_NAME = "get_asset_names";
