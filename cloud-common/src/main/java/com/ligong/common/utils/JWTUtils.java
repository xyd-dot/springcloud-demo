package com.ligong.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;

/**
 * @description:
 * @since: 2023/10/9 17:45
 * @author: sdw
 */
public class JWTUtils {

    private static final String SECRET = "#$#fdas!%";

    /**
     * 生成 token
     */
    public static String getToken(HashMap<String, String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7); // 默认7天过期

        // 创建 JTW builder
        JWTCreator.Builder builder = JWT.create();
        // payload
        map.forEach((k, v)->{
            builder.withClaim(k, v);
        });

        // 指定令牌过期时间
        builder.withExpiresAt(instance.getTime());

        // 签名
        String token = builder.sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 验证并获取 token 信息
     */
    public static DecodedJWT verify(String token){

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }
}

