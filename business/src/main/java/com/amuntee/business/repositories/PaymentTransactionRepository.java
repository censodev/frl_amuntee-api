package com.amuntee.business.repositories;

import com.amuntee.business.models.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {
    PaymentTransaction findTopByOrderByCodeDesc();

    List<PaymentTransaction> findByOrderCode(String orderCode);
}
