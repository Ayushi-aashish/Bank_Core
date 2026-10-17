package com.first.bank.Controller;



import com.first.bank.Dto.AccountRequest;
import com.first.bank.Dto.AccountResponse;
import com.first.bank.Dto.MoneyRequest;
import com.first.bank.Dto.TransactionResponse;
import com.first.bank.Entity.Account;
import com.first.bank.Entity.Transaction;
import com.first.bank.Service.AccountService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponse createAccount(@RequestBody AccountRequest account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @GetMapping
    public Page<AccountResponse> getAllAccounts(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {

        return accountService.getAllAccounts(pageable);
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(
            @PathVariable Long id,
            @Valid @RequestBody MoneyRequest request) {
        System.out.println("Amount received by controller: " + request.getAmount());
        return accountService.deposit(id, request.getAmount());
    }

    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(
            @PathVariable Long id,
            @Valid @RequestBody MoneyRequest request) {

        return accountService.withdraw(id, request.getAmount());
    }


    @GetMapping("/{id}/transactions")
    public Page<TransactionResponse> getTransactions(
            @PathVariable Long id,
            @PageableDefault(
                    size = 10,
                    sort = "timestamp",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return accountService.getTransactions(id, pageable);
    }
}