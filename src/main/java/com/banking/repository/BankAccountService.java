package com.banking.repository;

import java.util.List;

import com.banking.model.BankAccount;

public interface BankAccountService {
    BankAccount saveBankAccount(BankAccount bankAccount);
    BankAccount getBankAccountById(Long id);
    BankAccount transferAmount(Long fromAccountId, Long toAccountId, double amount);
    BankAccount updateBankAccount(Long id, BankAccount bankAccount);
    List<BankAccount> getAllBankAccounts();
    void deleteBankAccount(Long id);  // Add this method
}
