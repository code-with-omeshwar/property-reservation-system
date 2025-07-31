package com.omeshwar.project.airBNB.repository;

import com.omeshwar.project.airBNB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}