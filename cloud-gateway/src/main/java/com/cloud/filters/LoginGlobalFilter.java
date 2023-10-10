package com.cloud.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cloud.config.AuthProperties;
import com.ligong.common.exception.UnauthorizedException;
import com.ligong.common.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @description:
 * @since: 2023/10/9 15:43
 * @author: sdw
 */
@Component
@RequiredArgsConstructor
public class LoginGlobalFilter implements GlobalFilter, Ordered {


    private final AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request
        ServerHttpRequest request = exchange.getRequest();

        if (isAllowPath(request)) {
            //放行
            return chain.filter(exchange);
        }

        //2.获取token
        List<String> headers = request.getHeaders().get("authorization");
        //3.判断当前请求是否需要被拦截
        if (CollectionUtils.isEmpty(headers)) {
            //没有token，需要拦截
            throw new UnauthorizedException("未登录，无法访问");
        }
        String token = headers.get(0);
        //4.需要，解析token
        String userId = null;
        try {
            DecodedJWT verify = JWTUtils.verify(token);
            userId = verify.getClaim("userId").asString();

        } catch (Exception e) {

            ServerHttpResponse response = exchange.getResponse();
            response.setRawStatusCode(401);
            return response.setComplete();
        }

        System.out.println("userId=" + userId);
        //TODO 传递用户信息到下游服务
        //修改下游所有的请求，在请求头上加上userInfo
        String userInfo = userId.toString();
        ServerWebExchange serverWebExchange = exchange.mutate().request(builder -> builder.header("user-info", userInfo)).build();

        //5.不需要，放行
        return chain.filter(serverWebExchange);
    }

    private boolean isAllowPath(ServerHttpRequest request) {
        boolean flag = false;

        String path = request.getPath().toString();

        //需要放行的路径
        for (String excludePath : authProperties.getExcludePaths()) {

            if (path.equals(excludePath)){
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
