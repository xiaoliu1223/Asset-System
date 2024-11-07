package com.cg.test.am.configuration;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysUserMapper;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {

    @Resource
    SysUserMapper sysUserMapper;

//    @Resource
//    RedisTemplate<String, String> redisTemplate;


//    private static final String houseToken = "house-token";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String token = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(token);
            Boolean flag = JwtUtil.jwtVerify(claims, null);
            if (!flag) {
                throw new ChorBizException(AmErrorCode.LOG_EXPIRED);
            }
            String userId = String.valueOf(claims.get("id"));


            SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));
            if(null==user){
                throw new ChorBizException(AmErrorCode.LOG_EXPIRED);
            }else{
                return true;
            }
        }catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
