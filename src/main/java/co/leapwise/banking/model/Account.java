package co.leapwise.banking.model;

import co.leapwise.banking.common.AccountType;

public record Account(
    Long accountId,
    Long accountNumber,
    AccountType accountType,
    Long balance,
    Long pastMonthTurnover) {}
