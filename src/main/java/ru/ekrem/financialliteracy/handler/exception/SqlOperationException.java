package ru.ekrem.financialliteracy.handler.exception;

public class SqlOperationException extends RuntimeException {
    public SqlOperationException(String message){ super(message);}
}
