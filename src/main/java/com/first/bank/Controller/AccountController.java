package com.first.bank.Controller;



import com.first.bank.Dto.MoneyRequest;
import com.first.bank.Entity.Account;
import com.first.bank.Entity.Transaction;
import com.first.bank.Service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    @PostMapping("/{id}/deposit")
    public Account deposit(
            @PathVariable Long id,
            @RequestBody MoneyRequest request) {

        return accountService.deposit(id, request.getAmount());
    }
    @PostMapping("/{id}/withdraw")
    public Account withdraw(
            @PathVariable Long id,
            @RequestBody MoneyRequest request) {

        return accountService.withdraw(id, request.getAmount());
    }
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(
            @PathVariable Long id) {

        return accountService.getTransactions(id);
    }
}
