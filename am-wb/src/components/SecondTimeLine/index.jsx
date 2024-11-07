import React, { useEffect, useState } from "react";
import { Timeline } from "antd";
import moment from "moment";
const SecondTimeLineContent = (props) => {
  const [SecondTimeLineData, setSecondTimeLineData] = useState(props.data.sysAuditLogs);
  useEffect(() => {
    setSecondTimeLineData(props.data.sysAuditLogs);
  }, [props.data.sysAuditLogs]);

  return (
    <Timeline>
      {SecondTimeLineData&&SecondTimeLineData.length > 0 ? (
        SecondTimeLineData.map((ele, index) => {
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
                    <span>{ele.auditName}</span>
                </p>
                <p>
                    <span style={{ marginRight: "30px" }}>时间</span>
                    <span>{ele.auditTime == 0
                    ? 0
                    : moment(ele.auditTime).format("YYYY-MM-DD HH:mm:ss")}</span>
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
                    <span>发起申请</span>
                )}
                {ele.postName !== "申请人" ? (
                    <p>
                        <span style={{ marginRight: "30px" }}>审批意见</span>
                        <span>{ele.reason == null ? "无" : (
                            ele.reason
                        )}</span>
                    </p>
                ):(
                    ""
                )}
                </Timeline.Item>
            );
        })
      ) : (
        ''
      )}
    </Timeline>
  );
};

export default SecondTimeLineContent;
