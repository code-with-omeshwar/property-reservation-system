package com.omeshwar.project.airBNB.repository;

import com.omeshwar.project.airBNB.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}