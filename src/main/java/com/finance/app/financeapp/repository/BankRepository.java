package com.finance.app.financeapp.repository;

import com.finance.app.financeapp.dto.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    List<Bank> findByUserId(Long userId);
}