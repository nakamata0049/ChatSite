package com.example.genkichat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.genkichat.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name);
}
