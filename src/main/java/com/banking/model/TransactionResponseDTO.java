package com.banking.model;

public class TransactionResponseDTO {
    private Long id;
    private double amount;
    private String type;
    private BankAccountDTO bankAccount; // Assuming BankAccountDTO is structured as shown earlier

    public TransactionResponseDTO(Long id, double amount, String type, BankAccountDTO bankAccount) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.bankAccount = bankAccount;
    }

    public static TransactionResponseDTO fromTransaction(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        BankAccountDTO bankAccountDTO = new BankAccountDTO(
            transaction.getBankAccount().getId(),
            transaction.getBankAccount().getAccountNumber(),
            transaction.getBankAccount().getAccountHolderName(),
            transaction.getBankAccount().getBalance()
        );
        return new TransactionResponseDTO(
            transaction.getId(),
            transaction.getAmount(),
            transaction.getType(),
            bankAccountDTO
        );
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
    }
}
