package com.example.securityhelloworld.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器类
 * 负责处理用户的登录请求和认证逻辑
 */
@RestController
public class LoginController {

    /**
     * Spring Security 认证管理器，用于验证用户凭据
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 构造函数 - 注入 AuthenticationManager
     * @param authenticationManager Spring Security 认证管理器
     */
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 处理用户登录请求
     * 接收用户名和密码，验证用户身份后返回相应响应
     * @param loginRequest 包含用户名和密码的登录请求对象
     * @return ResponseEntity 响应实体，包含登录结果信息
     */
    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // 创建用户名密码认证令牌
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        // 使用认证管理器进行身份验证
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 检查认证是否成功
        if (authentication.isAuthenticated()) {
            // 认证成功，返回成功消息
            return ResponseEntity.ok("Login successful");
        } else {
            // 认证失败，返回401未授权状态码
            return ResponseEntity.status(401).body("Login failed");
        }
    }

    /**
     * 登录请求数据传输对象(DTO)
     * 用于封装前端传递的登录信息
     */
    public static class LoginRequest {
        /**
         * 用户名字段
         */
        private String username;

        /**
         * 密码字段
         */
        private String password;

        // Getters and Setters

        /**
         * 获取用户名
         * @return 用户名字符串
         */
        public String getUsername() {
            return username;
        }

        /**
         * 设置用户名
         * @param username 用户名字符串
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * 获取密码
         * @return 密码字符串
         */
        public String getPassword() {
            return password;
        }

        /**
         * 设置密码
         * @param password 密码字符串
         */
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
