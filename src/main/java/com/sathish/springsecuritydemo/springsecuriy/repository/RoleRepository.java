package com.sathish.springsecuritydemo.springsecuriy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sathish.springsecuritydemo.springsecuriy.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
