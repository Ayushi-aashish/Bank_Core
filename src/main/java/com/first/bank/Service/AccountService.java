package com.first.bank.Service;

import com.first.bank.Dto.TransferRequest;
import com.first.bank.Entity.Account;
import com.first.bank.Entity.Transaction;
import com.first.bank.Entity.TransactionType;
import com.first.bank.Exception.AccountNotFoundException;
import com.first.bank.Exception.SameAccountTransferException;
import com.first.bank.Repository.AccountRepo;
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

    public AccountService(AccountRepo accountRepository,TransactionRepo transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;

    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public Account deposit(Long id, BigDecimal amount) {

        Account account = getAccount(id);

        account.deposit(amount);
        Account savedAccount = accountRepository.save(account);

        createTransaction(
                account,
                TransactionType.DEPOSIT,
                amount
        );


        return savedAccount;
    }
    public Account withdraw(Long id, BigDecimal amount) {

        Account account = getAccount(id);

        account.withdraw(amount);
        Account savedAccount = accountRepository.save(account);

        createTransaction(
                account,
                TransactionType.WITHDRAWAL,
                amount
        );



        return savedAccount;
    }
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

    public List<Transaction> getTransactions(Long accountId) {

        getAccount(accountId);

        return transactionRepository.findByAccountId(accountId);
    }
    @Transactional
    public void transfer(TransferRequest request) {

        Account sender =
                getAccount(request.getSenderAccountId());

        Account receiver =
                getAccount(request.getReceiverAccountId());

        if (sender.getId().equals(receiver.getId())) {
            throw new SameAccountTransferException();
        }

        sender.withdraw(request.getAmount());

        receiver.deposit(request.getAmount());

        accountRepository.save(sender);
        accountRepository.save(receiver);

        createTransaction(
                sender,
                TransactionType.TRANSFER,
                request.getAmount()
        );

        createTransaction(
                receiver,
                TransactionType.TRANSFER,
                request.getAmount()
        );
    }
}