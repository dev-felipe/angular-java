package com.angularjava.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angularjava.project.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
}
