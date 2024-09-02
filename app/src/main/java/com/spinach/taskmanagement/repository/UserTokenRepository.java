package com.spinach.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spinach.taskmanagement.entity.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
}
