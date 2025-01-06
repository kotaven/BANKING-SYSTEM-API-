package com.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banking.exception.BankAccountNotFoundException;
import com.banking.model.BankAccount;
import com.banking.service.BankAccountService;

@RestController
@RequestMapping("/api/bankAccounts")
public class BankAccountController {
    @Autowired
    private BankAccountService service;

    @PostMapping
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            BankAccount createdAccount = service.saveBankAccount(bankAccount);
            return ResponseEntity.status(201).body(createdAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBankAccountById(@PathVariable Long id) {
        try {
            BankAccount bankAccount = service.getBankAccountById(id);
            return ResponseEntity.ok(bankAccount);
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        List<BankAccount> bankAccounts = service.getAllBankAccounts();
        return ResponseEntity.ok(bankAccounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBankAccount(@PathVariable Long id, @RequestBody BankAccount bankAccount) {
        try {
            BankAccount updatedAccount = service.updateBankAccount(id, bankAccount);
            return ResponseEntity.ok(updatedAccount);
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long id) {
        try {
            service.deleteBankAccount(id);
            return ResponseEntity.ok("Bank account deleted with ID: " + id);
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferAmount(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam double amount) {

        try {
            BankAccount updatedFromAccount = service.transferAmount(fromAccountId, toAccountId, amount);
            return ResponseEntity.ok(updatedFromAccount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
