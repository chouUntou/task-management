package com.spinach.taskmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF，因为我们使用的是无状态的 JWT 认证
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection

                // 配置 Session 管理，使用无状态的策略 // Enable CORS with custom configuration
                // .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 关闭 CORS
                .cors(cors -> cors.disable())

                // 配置请求的授权规则
                .authorizeHttpRequests(auth -> auth
                        // 允许未认证的用户访问登录和测试接口 // Allow unauthenticated access to specific endpoints
                        .requestMatchers("/api/login", "/api/test").permitAll()
                        // 其他所有请求都需要认证 // All other requests require authentication
                        .anyRequest().authenticated())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Set session to stateless

        return http.build();
    }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration configuration = new CorsConfiguration();
    // configuration.addAllowedOrigin("*"); // Allow all origins
    // configuration.addAllowedMethod("*"); // Allow all HTTP methods
    // configuration.addAllowedHeader("*"); // Allow all headers

    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // // Apply this CORS configuration to all paths
    // source.registerCorsConfiguration("/**", configuration);
    // return source;
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 进行密码加密
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @SuppressWarnings("null")
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:8080") // Replace with your frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        // .allowCredentials(true)
                        ;
            }
        };
    }
}
