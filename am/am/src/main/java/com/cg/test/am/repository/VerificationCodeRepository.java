package com.cg.test.am.repository;

import com.cg.test.am.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// 定义 VerificationCode 的仓库接口，继承自 JpaRepository，指定实体类为 VerificationCode，主键类型为 Long
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    // 使用自定义的 JPQL 查询语句来判断给定的验证码在数据库中是否存在
    @Query("SELECT COUNT(vc) > 0 FROM VerificationCode vc WHERE vc.code = ?1")
    boolean existsByCode(String code);
}
