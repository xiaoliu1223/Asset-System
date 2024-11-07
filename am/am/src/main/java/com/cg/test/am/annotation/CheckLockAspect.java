package com.cg.test.am.annotation;

import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysLockMapper;
import com.cg.test.am.model.SysLock;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.vo.ParamsConstant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
@Order(1)
public class CheckLockAspect {

    private final static Logger logger = LoggerFactory.getLogger(CheckLockAspect.class);

    @Resource
    SysLockMapper sysLockMapper;

    @Around("@annotation(com.cg.test.am.annotation.CheckLock)")
    public Object judgeLocked(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        try {

            // 判断是否锁库
            SysLock sysLock = sysLockMapper.selectById(1);

            logger.info("当前盘点状态为: {}", sysLock.getIsLock());
            if (sysLock.getIsLock().equals(ParamsConstant.ASSET_CHECK_STATUS_ON)) {
                throw new ChorBizException(AmErrorCode.ASSET_IN_CHECK);
            }

            return proceedingJoinPoint.proceed();

        } catch (ChorBizException e) {
            throw e;
        }
    }
}
