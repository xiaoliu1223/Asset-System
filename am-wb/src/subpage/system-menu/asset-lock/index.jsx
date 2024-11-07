import React from "react";
import { Switch } from "antd";
import { useState, useEffect } from "react";
import BreadcrumbComponent from "../../../components/Breadcrumb/Breadcrumb";
import Axios from "../../../common/axios";

export const AssetLock = () => {
  const [checked, setChecked] = useState(false);

  useEffect(() => {
    Axios({
      method: "get",
      url: "/sysLock/getLockStatus",
    }).then((res) => {
      if (res.data.code === "000000") {
        setChecked(res.data.data.isLock);
      }
    });
  }, []);

  const onChange = (checked) => {
    Axios({
      method: "put",
      url: "/sysLock/switchAssetLock",
      data: {
        isLock: checked ? 1 : 0,
      },
    }).then((res) => {
      if (res.data.code === "000000") {
        setChecked(checked);
      }
    });
  };
  return (
    <div>
      <BreadcrumbComponent title="盘点开关" />
      <div
        style={{
          margin: "30px 0",
        }}
      >
        <Switch
          checkedChildren="开启"
          unCheckedChildren="关闭"
          checked={checked}
          onChange={onChange}
        />
      </div>

      <div>
        <h2
          style={{
            textAlign: "left",
          }}
        >
          {checked
            ? "资产盘点开关已开启，此状态下只允许进行资产盘点操作；"
            : "资产盘点开关已关闭，此状态下可进行资产领用、归还、核销等相关操作；"}
        </h2>
      </div>
    </div>
  );
};
