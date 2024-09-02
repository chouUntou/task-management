package com.spinach.taskmanagement.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spinach.taskmanagement.dto.RegisterRequestDTO;
import com.spinach.taskmanagement.entity.Role;
import com.spinach.taskmanagement.entity.User;
import com.spinach.taskmanagement.entity.UserToken;
import com.spinach.taskmanagement.repository.UserRepository;
import com.spinach.taskmanagement.repository.UserTokenRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        // 验证角色
        String roleStr = registerRequestDTO.getRole().toUpperCase();
        Role role;
        switch (roleStr.toUpperCase()) {
            case "MANAGER":
                role = Role.MANAGER;
                break;
            case "GUEST":
                role = Role.GUEST;
                break;
            default:
                role = Role.MEMBER;
        }
        user.setRole(role);

        return userRepository.save(user);
    }

    public String login(String username, String password, Role role) {
        // 检查用户名和密码
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            // 生成Token
            String token = Jwts.builder()
                    .setSubject(username)
                    .claim("role", role.name())
                    .setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretKey")
                    .compact();

            // 保存Token到数据库
            UserToken userToken = new UserToken();
            userToken.setUser(userOpt.get());
            userToken.setToken(token);
            userTokenRepository.save(userToken);

            return token;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public User register(String username, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
}
