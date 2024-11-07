package com.cg.test.am.response.core;

import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 返回数据据格式
 * @author hao.yan
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChorPageResponse<T> extends Result {

    private static final long serialVersionUID = -6270179091882389906L;

    /**
     * 数据集
     */
    private Collection<T> datas;

    /**
     * 条数
     */
    private Integer count;

    /**
     * 一页展示数量
     */
    private Integer pageSize;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 总数量
     */
    private Long total;

    public ChorPageResponse(Integer pageNum, Integer pageSize, Long total, Collection<T> datas){
        this(ErrorCode.SUCCESS, pageNum, pageSize, total, datas);
    }

    public ChorPageResponse(ErrorCode errorCode){
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public ChorPageResponse(String code, String message){
        super(code, message);
    }

    public ChorPageResponse(ErrorCode errorCode, Integer pageNum, Integer pageSize, Long total, Collection<T> datas){
        this(errorCode.getCode(), errorCode.getMessage(), pageNum, pageSize, total, datas);
    }

    public ChorPageResponse(String code, String message, Integer pageNum, Integer pageSize, Long total, Collection<T> datas){
        super(code, message);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        if (!CollectionUtils.isEmpty(datas)){
            this.datas = datas;
            this.count = datas.size();
        }
    }


}
