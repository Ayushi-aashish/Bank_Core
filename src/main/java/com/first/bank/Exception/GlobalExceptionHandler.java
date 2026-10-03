package com.first.bank.Exception;



import com.first.bank.Dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(AccountNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleAccountNotFound(
                AccountNotFoundException ex) {

            ErrorResponse error = new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage()
            );

            return new ResponseEntity<>(
                    error,
                    HttpStatus.NOT_FOUND
            );
        }
        @ExceptionHandler(InvalidAmountException.class)
        public ResponseEntity<ErrorResponse> handleInvalidAmount(
                InvalidAmountException ex) {

            ErrorResponse error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            );

            return new ResponseEntity<>(
                    error,
                    HttpStatus.BAD_REQUEST
            );
        }
        @ExceptionHandler(InsufficientFundsException.class)
        public ResponseEntity<ErrorResponse> handleInsufficientFunds(
                InsufficientFundsException ex) {

            ErrorResponse error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            );

            return new ResponseEntity<>(
                    error,
                    HttpStatus.BAD_REQUEST
            );
        }
        @ExceptionHandler(SameAccountTransferException.class)
        public ResponseEntity<ErrorResponse> handleSameAccountTransfer(
                SameAccountTransferException ex) {

            ErrorResponse error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            );

            return new ResponseEntity<>(
                    error,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

