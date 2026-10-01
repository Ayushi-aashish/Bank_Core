package com.first.bank.Exception;

public class SameAccountTransferException extends RuntimeException {

    public SameAccountTransferException() {
        super("Sender and receiver cannot be the same");
    }


}
