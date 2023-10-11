• /oauth/authorize ：授权端点。
•/oauth/token ：令牌端点。
•/oauth/confirm_access ：用户确认授权提交端点。
•/oauth/error ：授权服务错误信息端点。
•/oauth/check_token ：用于资源服务访问的令牌解析端点。
•/oauth/token_key ：提供公有密匙的端点，如果你使用 T令牌的话。
需要注意的是授权端点这个URL应该被Spring Security保护起来只供授权用户访问.