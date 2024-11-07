import React from "react";
import { Button, message } from "antd";
import Axios from "../../common/axios";
export const SelectedAction = ({ selectedRowKeys, btns }) => {
  const hasSelected = selectedRowKeys.length > 0;

  const postData = (url, auditStatus) => {
    Axios({
      method: "post",
      url,
      data: {
        idList: selectedRowKeys,
        auditStatus,
      },
    }).then((res) => {
      if (res.data.code === "000000") {
        message.info("审批成功", 1);
        setTimeout(() => {
          window.location.reload();
        }, 1500);
      } else {
        message.info(res.data.message, 1);
      }
    });
  };

  const Buttons = btns.map((item) => {
    return (
      <Button
        type={item.type}
        disabled={!hasSelected}
        style={{ margin: "0 10px" }}
        onClick={() => postData(item.postUrl, item.auditStatus)}
      >
        {item.text}
      </Button>
    );
  });
  return (
    <div>
      <span style={{ marginRight: 10 }}>
        {hasSelected ? `选择了 ${selectedRowKeys.length} 项` : ""}
      </span>
      {/* <Button
        type="primary"
        disabled={!hasSelected}
        style={{ margin: "0 30px" }}
      >
        批量通过
      </Button>
      <Button type="danger" disabled={!hasSelected}>
        批量删除
      </Button> */}
      {Buttons}
    </div>
  );
};
