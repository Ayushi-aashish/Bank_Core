package com.first.bank.Exception;

import java.math.BigDecimal;

public class InvalidAmountException extends RuntimeException{

    public InvalidAmountException(){

        super("Amount must be greater than zero");

    }
}
