package com.cloud.filters;

import com.alibaba.fastjson.JSON;
import com.cloud.config.AuthProperties;
import com.cloud.domain.RestErrorResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @description:
 * @since: 2023/10/10 17:36
 * @author: sdw
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {


    private static final Logger log
            = LoggerFactory.getLogger(AuthGlobalFilter.class);

    private final AuthProperties authProperties;

    @Autowired
    private TokenStore tokenStore;

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    public AuthGlobalFilter(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    @Autowired
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        //判断path是否在白名单中，如果在，则放行
        List<String> paths = authProperties.getExcludePaths();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String path : paths) {
            if(antPathMatcher.match(path,request.getPath().value())){
                //放行
                return chain.filter(exchange);
            }
        }

        String token = this.getToken(exchange);
        if (token == null){
            //token为null,没有认证
            return buildReturnMono("没有认证",exchange);
        }

        //判断是否是有效的token
        OAuth2AccessToken oAuth2AccessToken;
        try {
            oAuth2AccessToken = tokenStore.readAccessToken(token);

            boolean expired = oAuth2AccessToken.isExpired();
            if (expired) {
                return buildReturnMono("认证令牌已过期",exchange);
            }
            return chain.filter(exchange);
        } catch (InvalidTokenException e) {
            log.info("认证令牌无效: {}", token);
            return buildReturnMono("认证令牌无效",exchange);
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 获取请求头中的token
     * jwt
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

    private Mono<Void> buildReturnMono(String error, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        String jsonString = JSON.toJSONString(new RestErrorResponse(error));
        byte[] bits = jsonString.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
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
}
