package com.banking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.exception.BankAccountNotFoundException;
import com.banking.model.BankAccount;
import com.banking.repository.BankAccountRepository;

import jakarta.transaction.Transactional;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository repository;

    public BankAccount saveBankAccount(BankAccount bankAccount) {
        Optional<BankAccount> existingAccount = repository.findByAccountNumber(bankAccount.getAccountNumber());
        if (existingAccount.isPresent()) {
            throw new IllegalArgumentException("Account number must be unique.");
        }
        return repository.save(bankAccount);
    }

    public BankAccount getBankAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with ID " + id + " not found."));
    }

    @Transactional
    public BankAccount transferAmount(Long fromAccountId, Long toAccountId, double amount) {
        BankAccount fromAccount = repository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("From account not found."));
        BankAccount toAccount = repository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("To account not found."));

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for transfer.");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        repository.save(fromAccount);  // Save the updated 'from' account
        repository.save(toAccount);    // Save the updated 'to' account

        return fromAccount;  // Optionally return the from account for further use
    }

    public BankAccount updateBankAccount(Long id, BankAccount bankAccount) {
        BankAccount existingAccount = getBankAccountById(id);
        existingAccount.setAccountHolderName(bankAccount.getAccountHolderName());
        existingAccount.setAccountNumber(bankAccount.getAccountNumber());
        existingAccount.setBalance(bankAccount.getBalance());
        return repository.save(existingAccount);
    }

    public List<BankAccount> getAllBankAccounts() {
        return repository.findAll();
    }

	public void deleteBankAccount(Long id) {
		// TODO Auto-generated method stub
		
	}
}
