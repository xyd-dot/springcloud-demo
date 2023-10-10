package com.ligong.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.ligong.common.utils.UserLocal;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @since: 2023/10/9 16:53
 * @author: sdw
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userInfo = request.getHeader("user-info");
        if (StrUtil.isNotBlank(userInfo)){
            UserLocal.setUser(Long.valueOf(userInfo));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserLocal.removeUser();
    }
}
