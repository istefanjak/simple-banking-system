package co.leapwise.banking.model;

import java.time.LocalDateTime;

public record Transaction(
    Long transactionId,
    Long senderAccountId,
    Long receiverAccountId,
    Long amount,
    Long currencyId,
    String message,
    LocalDateTime timeStamp) {}
