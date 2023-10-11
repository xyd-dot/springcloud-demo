//package com.cloud.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//
//import javax.sql.DataSource;
//import java.util.concurrent.TimeUnit;
//
///**
// * @description: 该类的作用就是表示这个是一个授权服务
// * @since: 2023/10/10 09:14
// * @author: sdw
// */
//@Configuration
//@EnableAuthorizationServer
//public class JdbcAuthorizationServer extends AuthorizationServerConfigurerAdapter {
//
//    private final DataSource dataSource;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    public JdbcAuthorizationServer(DataSource dataSource, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
//        this.dataSource = dataSource;
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Bean
//    public ClientDetailsService clientDetails(){
//        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
//        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
//        return jdbcClientDetailsService;
//    }
//
//    /**
//     * 客户端授权信息存储到数据库
//     * @param clients
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetails());
//    }
//
//    @Bean
//    public TokenStore tokenStore(){
//        return new JdbcTokenStore(dataSource);
//    }
//
//    /**
//     * 配置令牌存储到数据库
//     * AuthorizationServerEndpointsConfigurer:用来配置授权(authorization)以及令牌(token)的访问端点和令牌服务(token services)
//     * 装载类
//     * @param endpoints
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager)   //认证管理器
//                .tokenStore(tokenStore());       //配置令牌存储到数据库
//
//        //设置令牌配置参数
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setReuseRefreshToken(true);
//
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());   //设置客户端信息
//        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());   //设置令牌存储增强策略
//        //访问令牌默认有效期
//        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));
//        //刷新令牌有效期
//        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(3));
//
//        endpoints.tokenServices(tokenServices);
//    }
//}
