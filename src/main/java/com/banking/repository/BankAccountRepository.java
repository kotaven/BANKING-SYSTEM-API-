package com.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);  // Add this method
}
