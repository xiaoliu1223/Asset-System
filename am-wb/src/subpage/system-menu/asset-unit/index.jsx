import React from "react";
import { useState, useEffect } from "react";
import { Table, Button, Input, Modal, Popconfirm, Icon, message } from "antd";
import Style from "../../../common/list.less";
import BreadcrumbComponent from "../../../components/Breadcrumb/Breadcrumb";
import Axios from "../../../common/axios";

const { Search } = Input;

export const AssetUnit = () => {
  const [visible, setVisible] = useState(false);

  const [name, setName] = useState("");

  const [dataSource, setDataSource] = useState([]);

  useEffect(() => {
    getData();
  }, []);

  const getData = () => {
    Axios({
      method: "get",
      url: "/sysUnit/list",
    }).then((res) => {
      if (res.data.code === "000000") {
        const addIndexDataSource =
          res.data.data.length > 0 &&
          res.data.data.map((item, index) => {
            return {
              id: item.id,
              name: item.name,
              index: index + 1,
            };
          });
        setDataSource(addIndexDataSource);
      }
    });
  };

  const columns = [
    {
      title: "序号",
      dataIndex: "index",
      key: "index",
    },
    {
      title: "单位名称",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "操作",
      render: (record) => (
        <span>
          <Popconfirm
            title="是否确定删除?"
            onConfirm={() => deleteUnit(record.id)}
            okText="是"
            cancelText="否"
          >
            <Icon type="delete" />
          </Popconfirm>
          {/* 设置权限 */}
        </span>
      ),
    },
  ];

  const showModal = () => {
    setVisible(true);
  };

  const handleOk = (e) => {
    console.log(e);
    Axios({
      method: "post",
      url: "/sysUnit/create",
      data: {
        name,
      },
    }).then((res) => {
      if (res.data.code === "000000") {
        setVisible(false);
        message.success("添加成功");
        setTimeout(() => {
          window.location.reload();
        }, 1000);
      }
    });
  };

  const handleCancel = (e) => {
    console.log(e);
    setName("");
    setVisible(false);
  };

  const deleteUnit = (id) => {
    Axios({
      method: "delete",
      url: `/sysUnit/${id}`,
    }).then((res) => {
      if (res.data.code === "000000") {
        message.success("删除成功");
        setTimeout(() => {
          window.location.reload();
        }, 1000);
      }
    });
  };

  const searchData = (value) => {
    Axios({
      method: "get",
      url: "/sysUnit/list",
    }).then((res) => {
      if (res.data.code === "000000") {
        setDataSource(res.data.data);
        if (res.data.data.length > 0) {
          const addIndexDataSource =
            res.data.data.length > 0 &&
            res.data.data.map((item, index) => {
              return {
                id: item.id,
                name: item.name,
                index: index + 1,
              };
            });
          const searchedData = addIndexDataSource.filter((item) => {
            return item.name.indexOf(value) > -1;
          });
          setDataSource(searchedData);
        } else {
          setDataSource([]);
        }
      }
    });
  };

  return (
    <div className={Style.banner}>
      <div className={Style.Breadcrumb}>
        <BreadcrumbComponent title="单位管理" />
        <div className={Style.formname}>
          <Button type="primary" onClick={showModal}>
            添加
          </Button>
          <div style={{ height: "30px", marginLeft: "30px" }}>
            <Search
              placeholder="请输入单位"
              onSearch={(value) => searchData(value)}
              style={{ width: 200, height: "100%" }}
            />
          </div>
        </div>
      </div>
      <div className={Style.banner_list}>
        <Table dataSource={dataSource} columns={columns} />
      </div>
      <Modal
        title="添加单位"
        visible={visible}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Input
          placeholder="例如：个"
          onChange={(e) => setName(e.target.value)}
        />
      </Modal>
    </div>
  );
};
