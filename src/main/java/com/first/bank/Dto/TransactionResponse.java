package com.first.bank.Dto;



import com.first.bank.Entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private Long accountId;

    public TransactionResponse(
            Long id,
            TransactionType type,
            BigDecimal amount,
            LocalDateTime timestamp,
            Long accountId) {

        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getAccountId() {
        return accountId;
    }
}