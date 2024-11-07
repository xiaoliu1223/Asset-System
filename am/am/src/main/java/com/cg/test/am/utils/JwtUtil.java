package com.cg.test.am.utils;

import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.response.exception.ChorBizException;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

	/**
	 * 用户登录成功后生成的JWT
	 * @param ttlMillis 过期时间 单位ms
	 * @param sysUser 登录后的用户对象
	 * @param fillArgs 补充字段
	 * @return
	 */
	public static String createJwt(long ttlMillis, SysUser sysUser, String fillArgs) {
	
		// 指定签名的时候使用的签名算法
		SignatureAlgorithm signatureAlgotithm = SignatureAlgorithm.HS256;
		
		// 生成JWT的时间
		long nowMillis  = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		
		// 创建payload私有声明
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("id", sysUser.getId());
		claims.put("name", sysUser.getUsername());
		claims.put("departmentId", sysUser.getDepartmentId());
		claims.put("fillArgs", fillArgs);
		
		// 生成签名时使用的secret
		String key = "token16546461";
		
		// 生成签发人
		String subject = sysUser.getUsername();
		
		JwtBuilder builder = Jwts.builder()
				.setClaims(claims) // 私有声明
				.setId(UUID.randomUUID().toString()) // jwt唯一标识
				.setIssuedAt(now) // 签发时间
				.setSubject(subject) // 签发主体，此jwt的所有人
				.signWith(signatureAlgotithm, key); // 设置签名实用的签名算法和使用的密钥
		
		// 默认设置7天过期
		long expMillis = ttlMillis > 0 ? nowMillis + ttlMillis : nowMillis + 604800000L;
		Date exp = new Date(expMillis); 
		// 设置过期时间
		builder.setExpiration(exp);
		
		return builder.compact();
	}
	
	/**
	 * Token解密
	 * @param token 加密后的token
	 * @return
	 * @throws ChorBizException
	 */
	public static Claims parseJwt(String token) throws ChorBizException {
		
		try {
			if(StringUtils.isEmpty(token)) {
				throw new ChorBizException(AmErrorCode.LOG_EXPIRED);
			}
			// 签名密钥，必须和生成该toekn的签名密钥一致		
			String key = "token16546461";
			Claims claims = Jwts.parser()
					.setAllowedClockSkewSeconds(604800) // 允许7天的偏移
					.setSigningKey(key) // 设置签名密钥
					.parseClaimsJws(token).getBody(); // 设置需要解析的JWT
			return claims;
		} catch (ChorBizException e) {
		    System.out.println(" Token expired ");
			throw e;
		} catch (ExpiredJwtException e) {
			throw new ChorBizException(AmErrorCode.LOG_EXPIRED);
		}
	}

	/**
	 * 通过token获取登录用户id
	 * @param token 用户登录生成的token
	 * @return
	 */
	public static Integer getUserIdByToken (String token) {

		Claims claims = parseJwt(token);
		Boolean flag = jwtVerify(claims, null);

		if (!flag) {
			throw new ChorBizException(AmErrorCode.LOG_EXPIRED);
		}

		String userId = String.valueOf(claims.get("id"));
		return Integer.parseInt(userId);
	}

	/**
	 * 用于检验该token是否有效
	 * @param claims
	 * @return
	 */
	public static Boolean jwtVerify(Claims claims, SysUser sysUser) {

		// 取得当前时间
		long now = new Date().getTime();

		// 取得过期时间
		Date exp = claims.getExpiration();
		long expL = exp.getTime();

		System.out.println("expL:" + expL);
		if ( now <= expL) {
			return true;
		}
		return false;
	}


	public static void main(String[] args) throws ChorBizException {
//		getUserId();
		Claims claims = parseJwt("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLmtYvor5UiLCJuYW1lIjoi5rWL6K-VIiwiaWQiOm51bGwsImV4cCI6MTU5NDcxOTI3MSwiaWF0IjoxNTk0MTE0NDcxLCJqdGkiOiJkYmE3NjMzYy02YWY3LTQ2NjctOWFkNC0zNTYzMDQzZjhiZWIifQ.PudBCfuXZeXYC06BxTcFX8oyHuVEgcqA8yTX4igvJ2c");
		System.out.println(claims);
	}
}
