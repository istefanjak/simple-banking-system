package co.leapwise.banking.model;

import java.util.List;

public record Customer(
    Long customerId,
    String name,
    String address,
    String email,
    String phoneNumber,
    List<Account> accounts) {}
