import React, { useEffect, useState } from "react";
import { Timeline } from "antd";
import moment from "moment";
const TimeLineContent = (props) => {
  const [TimeLineData, setTimeLineData] = useState(props.data);
  useEffect(() => {
    setTimeLineData(props.data);
  }, [props.data]);
  return (
    <Timeline>
      {TimeLineData.length == 0 ? (
        <p style={{ textAlign: "center" }}>暂无审核记录</p>
      ) : (
        TimeLineData.map((ele, index) => {
          let color = "gray";

          if (ele.auditStatus === -1) {
            color = "red";
          } else if (ele.auditStatus === 1) {
            color = "green";
          } else {
            color = "gray";
          }

          if (ele.postName === "申请人") {
            color = "blue";
          }

          return (
            <Timeline.Item key={index} color={color}>
              <p>
                <span style={{ marginRight: "30px" }}>{ele.postName}</span>
                <span>{ele.username}</span>
              </p>
              {ele.postName !== "申请人" ? (
                <p>
                  <span style={{ marginRight: "30px" }}>审核状态</span>
                  <span>
                    {ele.auditStatus == 1
                      ? "通过"
                      : ele.auditStatus == -1
                      ? "驳回"
                      : "待审批"}
                  </span>
                </p>
              ) : (
                ""
              )}
              {}
              {/* <p>
                <span style={{ marginRight: "30px" }}>
                  {ele.id ? "审核时间" : "申请时间"}
                </span>
                <span>
                  {ele.auditTime == 0
                    ? 0
                    : moment(ele.auditTime).format("YYYY-MM-DD HH:mm:ss")}
                </span>
              </p> */}
              {ele.reason == null ? null : (
                <p>
                  <span style={{ marginRight: "30px" }}>原因描述</span>
                  <span>{ele.reason}</span>
                </p>
              )}
            </Timeline.Item>
          );
        })
      )}
    </Timeline>
  );
};

export default TimeLineContent;
