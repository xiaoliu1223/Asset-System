package com.cg.test.am.service;

import com.cg.test.am.model.VerificationCode;
import com.cg.test.am.repository.VerificationCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 标识这是一个服务类，Spring 会将其作为一个 Bean 进行管理
@Service
public class VerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;
    // 构造函数，通过依赖注入获取 VerificationCodeRepository 的实例
    public VerificationCodeService(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
    }
    // 开启事务，用于保存验证码的操作
    @Transactional
    public void saveVerificationCode(String code) {
        // 创建一个 VerificationCode 对象
        VerificationCode verificationCode = new VerificationCode();
        // 设置该对象的验证码为传入的 code
        verificationCode.setCode(code);
        // 将带有验证码的对象保存到数据库中
        verificationCodeRepository.save(verificationCode);
    }

    // 开启只读事务，用于验证验证码的操作
    @Transactional(readOnly = true)
    public boolean verifyVerificationCode(String enteredCode) {
        // 调用 VerificationCodeRepository 的 existsByCode 方法，判断传入的 enteredCode 在数据库中是否存在，返回一个布尔值
        return verificationCodeRepository.existsByCode(enteredCode);
    }
}
