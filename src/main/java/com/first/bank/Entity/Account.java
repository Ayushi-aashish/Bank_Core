
package com.first.bank.Entity;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import jakarta.persistence.*;

    @Getter
    @Setter
    @Entity
    public class Account {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(unique = true,nullable=false)
        private String accountNumber;
        @Column(nullable=false)
        private BigDecimal balance;
        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        public Account() {
        }

        public Account(Long id, String accountNumber,
                       BigDecimal balance, Customer customer) {
            this.id = id;
            this.accountNumber = accountNumber;
            this.balance = balance;
            this.customer = customer;
        }

        public void deposit(BigDecimal amount) {
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Deposit amount must be greater than zero"
                );
            }

            balance = balance.add(amount);
        }

        public void withdraw(BigDecimal amount) {
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Withdrawal amount must be greater than zero"
                );
            }

            if (amount.compareTo(balance) > 0) {
                throw new IllegalArgumentException(
                        "Insufficient balance"
                );
            }

            balance = balance.subtract(amount);
        }
    }
}
