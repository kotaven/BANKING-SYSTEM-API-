package com.banking.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.model.BankAccount;
import com.banking.repository.BankAccountRepository;
import com.banking.repository.BankAccountService;

import jakarta.transaction.Transactional;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository repository;

    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return repository.save(bankAccount);
    }

    @Override
    public BankAccount getBankAccountById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Bank account not found."));
    }

    @Override
    @Transactional
    public BankAccount transferAmount(Long fromAccountId, Long toAccountId, double amount) {
        BankAccount fromAccount = repository.findById(fromAccountId).orElseThrow(() -> new IllegalArgumentException("From account not found."));
        BankAccount toAccount = repository.findById(toAccountId).orElseThrow(() -> new IllegalArgumentException("To account not found."));

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for transfer.");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        repository.save(fromAccount);
        repository.save(toAccount);

        return fromAccount;
    }

    @Override
    public BankAccount updateBankAccount(Long id, BankAccount bankAccount) {
        BankAccount existingAccount = getBankAccountById(id);
        existingAccount.setAccountHolderName(bankAccount.getAccountHolderName());
        existingAccount.setAccountNumber(bankAccount.getAccountNumber());
        existingAccount.setBalance(bankAccount.getBalance());
        return repository.save(existingAccount);
    }

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return repository.findAll();
    }

    @Override
    public void deleteBankAccount(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Bank account not found.");
        }
        repository.deleteById(id);
    }
}
