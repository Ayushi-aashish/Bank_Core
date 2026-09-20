package com.first.bank.Repository;
import com.first.bank.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,Long>{

}
