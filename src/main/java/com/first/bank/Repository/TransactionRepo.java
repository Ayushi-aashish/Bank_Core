package com.first.bank.Repository;
import com.first.bank.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TransactionRepo extends JpaRepository<Transaction, Long>{
}
