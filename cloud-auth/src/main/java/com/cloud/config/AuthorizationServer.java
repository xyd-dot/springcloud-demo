//package com.cloud.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//
///**
// * @description: 该类的作用就是表示这个是一个授权服务
// * @since: 2023/10/10 09:14
// * @author: sdw
// */
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final UserDetailsService userDetailsService;
//
//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    public AuthorizationServer(PasswordEncoder passwordEncoder,UserDetailsService userDetailsService,AuthenticationManager authenticationManager) {
//        this.passwordEncoder = passwordEncoder;
//        this.userDetailsService = userDetailsService;
//        this.authenticationManager = authenticationManager;
//    }
//
//    /**
//     * 配置客户端细节 如 客户端 id 秘钥 重定向 url 等
//     * 配置客户端身份信息，客户端
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("client")    //客户端名换
//                .secret(passwordEncoder.encode("secret"))    //客户端秘钥，客户端只有携带这个秘钥，才允许被授权
//                .redirectUris("http://www.baidu.com")   //授权跳转路径
//                .scopes("client:read,user:read")    //客户端授权范围
//                //授权服务器支持的授权模式
//                .authorizedGrantTypes("authorization_code", "refresh_token","implicit","password","client_credentials");
//    }
//
//    /**
//     * 注入授权服务器使用哪种userDetailsService
//     * @param endpoints
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.userDetailsService(userDetailsService)//开启刷新令牌必须指定
//                .authenticationManager(authenticationManager);
//
//    }
//}
