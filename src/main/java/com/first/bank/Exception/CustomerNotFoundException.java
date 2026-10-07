package com.first.bank.Exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(long id){
        super("Customer Not Found"+ id);
    }

}
