package com.cg.test.am.controller;

import com.cg.test.am.service.VerificationCodeService;
import org.springframework.web.bind.annotation.*;

// 标识这是一个 RESTful Web 服务控制器
@RestController
@RequestMapping("/verification")// 设置请求的基础路径为 /verification
public class VerificationCodeController {
    private final VerificationCodeService verificationCodeService;

    // 构造函数，通过依赖注入获取 VerificationCodeService 的实例
    public VerificationCodeController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    // 处理 POST 请求，路径为 /verification/save
    @PostMapping("/save")
    public void saveVerificationCode(@RequestBody String code) {
        // 调用 VerificationCodeService 的 saveVerificationCode 方法保存传入的验证码
        verificationCodeService.saveVerificationCode(code);
    }

    // 处理 POST 请求，路径为 /verification/verify
    @PostMapping("/verify")
    public boolean verifyVerificationCode(@RequestBody String enteredCode) {
        // 调用 VerificationCodeService 的 verifyVerificationCode 方法验证传入的验证码，并返回验证结果
        return verificationCodeService.verifyVerificationCode(enteredCode);
    }
}



