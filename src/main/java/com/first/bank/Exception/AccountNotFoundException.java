package com.first.bank.Exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(long id){
        super("Account Not Found "+ id);
    }


}
