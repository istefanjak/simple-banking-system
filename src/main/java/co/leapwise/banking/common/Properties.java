package co.leapwise.banking.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "co.leapwise.banking")
public record Properties(
    String ibanRegex, Integer numberOfTransactions, String generatedFileName) {}
