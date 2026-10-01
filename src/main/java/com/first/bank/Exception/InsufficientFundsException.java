package com.first.bank.Exception;

public class InsufficientFundsException extends RuntimeException{


        public InsufficientFundsException() {
            super("Insufficient funds");
        }

}
