import React, { useEffect, useState } from "react";
import { Timeline } from "antd";
import moment from "moment";
const TodoDetailContent = (props) => {
    const data = props.data;

    var styles = {
        'bigTitle': {
            color: '#a0a0a0',
            fontSize: '14px',
            width: '100%',
            backgroundColor: '#f6f6f6',
            padding: '8px'
        },
        'title': {
            color: '#a0a0a0',
            fontSize: '10px'
        },
        'content': {
            fontSize: '13px',
        },
        'module': {
            marginBottom: '8px'
        },
        'bigModule': {
            marginBottom: '15px'
        }
    }

    const a = data&&data.map(({assetName,specification,num,budgetPrice,description},index) =>{
        var indexNum = index + 1;
        return (
            <div style={styles.bigModule}>
                <div style={styles.bigTitle}>明细{indexNum}</div>
                <div style={styles.module}>
                    <span style={styles.title}>资产名称：</span>
                    <br/>
                    <span style={styles.content}>{assetName}</span>
                </div>
                <div style={styles.module}>
                    <span style={styles.title}>规格型号：</span>
                    <br/>
                    <span style={styles.content}>{specification}</span>
                </div>
                <div style={styles.module}>
                    <span style={styles.title}>数量：</span>
                    <br/>
                    <span style={styles.content}>{num}</span>
                </div>
                <div style={styles.module}>
                    <span style={styles.title}>单价：</span>
                    <br/>
                    <span style={styles.content}>{budgetPrice}</span>
                </div>
                <div style={styles.module}>
                    <span style={styles.title}>申购/核销理由：</span>
                    <br/>
                    <span style={styles.content}>{description}</span>
                </div>
            </div>
        );
    });

    return (
        <div> { a } </div>
    )
};

export default TodoDetailContent;
