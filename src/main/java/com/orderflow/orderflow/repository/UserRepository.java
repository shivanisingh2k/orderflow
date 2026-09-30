package com.orderflow.orderflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.orderflow.orderflow.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

}