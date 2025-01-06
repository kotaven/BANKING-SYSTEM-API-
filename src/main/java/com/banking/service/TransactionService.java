package com.banking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.exception.BankAccountNotFoundException;
import com.banking.model.BankAccount;
import com.banking.model.Transaction;
import com.banking.model.TransactionResponseDTO;
import com.banking.repository.BankAccountRepository;
import com.banking.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionService(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(TransactionResponseDTO::fromTransaction)
                .collect(Collectors.toList());
    }
    
    public TransactionResponseDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
        return TransactionResponseDTO.fromTransaction(transaction);
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        BankAccount bankAccount = bankAccountRepository.findById(transaction.getBankAccount().getId())
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found with ID: " + transaction.getBankAccount().getId()));

        if ("credit".equalsIgnoreCase(transaction.getType())) {
            bankAccount.setBalance(bankAccount.getBalance() + transaction.getAmount());
        } else if ("debit".equalsIgnoreCase(transaction.getType())) {
            if (bankAccount.getBalance() < transaction.getAmount()) {
                throw new IllegalArgumentException("Insufficient balance for debit transaction.");
            }
            bankAccount.setBalance(bankAccount.getBalance() - transaction.getAmount());
        } else {
            throw new IllegalArgumentException("Invalid transaction type. Must be 'credit' or 'debit'.");
        }

        bankAccountRepository.save(bankAccount); 
        transaction.setBankAccount(bankAccount);  
        return transactionRepository.save(transaction);
    }

    public ResponseEntity<String> deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        BankAccount bankAccount = transaction.getBankAccount();
        if (bankAccount == null || bankAccount.getId() == null || !bankAccountRepository.existsById(bankAccount.getId())) {
            throw new BankAccountNotFoundException("Bank account with ID " + bankAccount.getId() + " not found.");
        }

        transactionRepository.deleteById(id); // Delete transaction
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Transaction deleted successfully.");
    }
}
