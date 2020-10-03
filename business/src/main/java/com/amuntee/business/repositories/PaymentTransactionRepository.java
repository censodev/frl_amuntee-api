package com.amuntee.business.repositories;

import com.amuntee.business.models.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {
    PaymentTransaction findTopByOrderByIdDesc();
}
