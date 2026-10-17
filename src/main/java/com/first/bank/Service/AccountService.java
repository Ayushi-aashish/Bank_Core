package com.first.bank.Service;

import com.first.bank.Dto.AccountRequest;
import com.first.bank.Dto.AccountResponse;
import com.first.bank.Dto.TransactionResponse;
import com.first.bank.Dto.TransferRequest;
import com.first.bank.Entity.Account;
import com.first.bank.Entity.Customer;
import com.first.bank.Entity.Transaction;
import com.first.bank.Entity.TransactionType;
import com.first.bank.Exception.AccountNotFoundException;
import com.first.bank.Exception.CustomerNotFoundException;
import com.first.bank.Exception.SameAccountTransferException;
import com.first.bank.Repository.AccountRepo;
import com.first.bank.Repository.CustomerRepo;
import com.first.bank.Repository.TransactionRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepo accountRepository;
    private final TransactionRepo transactionRepository;
    private final CustomerRepo customerRepository;

    public AccountService(
            AccountRepo accountRepository,
            TransactionRepo transactionRepository,
            CustomerRepo customerRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    // Create Account
    public AccountResponse createAccount(AccountRequest request) {

        // 1. Find the customer
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException(request.getCustomerId()));

        // 2. Create Account entity
        Account account = new Account();

        account.setAccountNumber(request.getAccountNumber());
        account.setBalance(request.getInitialBalance());
        account.setCustomer(customer);

        // 3. Save account
        Account savedAccount = accountRepository.save(account);

        // 4. Convert Entity -> Response DTO
        return toAccountResponse(savedAccount);
    }

    // Get Account
    public AccountResponse getAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException(id));

        return toAccountResponse(account);
    }

    // Get All Accounts
    public List<AccountResponse> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(this::toAccountResponse)
                .toList();
    }

    // Deposit
    @Transactional
    public AccountResponse deposit(Long id, BigDecimal amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException(id));
        System.out.println("Amount received by service: " + amount);
        System.out.println("Amount is null: " + (amount == null));

        // Business logic lives inside Account
        account.deposit(amount);


        Account savedAccount = accountRepository.save(account);

        // Record transaction only after successful deposit
        createTransaction(
                savedAccount,
                TransactionType.DEPOSIT,
                amount
        );

        return toAccountResponse(savedAccount);
    }

    // Withdraw
    @Transactional
    public AccountResponse withdraw(Long id, BigDecimal amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException(id));

        // Account checks amount and sufficient balance
        account.withdraw(amount);

        Account savedAccount = accountRepository.save(account);

        // Record transaction only after successful withdrawal
        createTransaction(
                savedAccount,
                TransactionType.WITHDRAWAL,
                amount
        );

        return toAccountResponse(savedAccount);
    }

    // Create Transaction
    private void createTransaction(
            Account account,
            TransactionType type,
            BigDecimal amount) {

        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    // Get Transaction History
    public List<TransactionResponse> getTransactions(Long accountId) {

        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getTimestamp(),
                        transaction.getAccount().getId()
                ))
                .toList();
    }

    // Transfer
    @Transactional
    public void transfer(TransferRequest request) {

        // 1. Find sender
        Account sender = accountRepository.findById(
                request.getSenderAccountId()
        ).orElseThrow(() ->
                new AccountNotFoundException(
                        request.getSenderAccountId()
                ));

        // 2. Find receiver
        Account receiver = accountRepository.findById(
                request.getReceiverAccountId()
        ).orElseThrow(() ->
                new AccountNotFoundException(
                        request.getReceiverAccountId()
                ));

        // 3. Sender and receiver cannot be same
        if (sender.getId().equals(receiver.getId())) {
            throw new SameAccountTransferException();
        }

        // 4. Withdraw from sender
        // This also checks amount > 0 and sufficient balance
        sender.withdraw(request.getAmount());

        // 5. Deposit into receiver
        receiver.deposit(request.getAmount());

        // 6. Save both accounts
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // 7. Create transaction for sender
        createTransaction(
                sender,
                TransactionType.TRANSFER,
                request.getAmount()
        );

        // 8. Create transaction for receiver
        createTransaction(
                receiver,
                TransactionType.TRANSFER,
                request.getAmount()
        );
    }

    // Entity -> Response DTO
    private AccountResponse toAccountResponse(Account account) {

        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getCustomer().getId()
        );
    }
}