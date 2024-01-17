package co.leapwise.banking.dev;

import co.leapwise.banking.common.AccountType;
import co.leapwise.banking.common.Utils;
import co.leapwise.banking.model.Account;
import co.leapwise.banking.model.Currency;
import co.leapwise.banking.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service for generating/mapping sample data to/ from a file */
@Service
@RequiredArgsConstructor
public class GeneratorService {
  private static final Long MIN_GENERATED_TRANSACTION_AMOUNT = 1L;
  private static final Long MAX_GENERATED_TRANSACTION_AMOUNT = 1000L;
  private static final Long MAX_BALANCE = 999999999L;

  private final ObjectMapper objectMapper;

  public void generateAndSaveRandomTransactions(
      int numberOfTransactions,
      String fileName,
      List<Account> accounts,
      List<Currency> currencies) {
    try (var writer = new BufferedWriter(new FileWriter(fileName))) {
      IntStream.range(0, numberOfTransactions)
          .mapToObj((i) -> generateOneTransaction(accounts, currencies))
          .map(this::serialize)
          .forEach((json) -> writeJsonToFile(writer, json));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<SerializedTransaction> readTransactions(String fileName) {
    var transactions = new ArrayList<SerializedTransaction>();

    try (var reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        var transaction = objectMapper.readValue(line, SerializedTransaction.class);
        transactions.add(transaction);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return transactions;
  }

  private String serialize(Object o) {
    try {
      return objectMapper.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private void writeJsonToFile(BufferedWriter writer, String json) {
    try {
      writer.write(json);
      writer.newLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private SerializedTransaction generateOneTransaction(
      List<Account> accounts, List<Currency> currencies) {
    var sender = Utils.getRandom(accounts);
    var receiver = Utils.getRandom(accounts);
    var currency = Utils.getRandom(currencies);
    var amount =
        Utils.getRandomLong(MIN_GENERATED_TRANSACTION_AMOUNT, MAX_GENERATED_TRANSACTION_AMOUNT);

    return SerializedTransaction.builder()
        .senderId(sender.getAccountId())
        .receiverId(receiver.getAccountId())
        .amount(amount)
        .currencyId(currency.getCurrencyId())
        .message("Random message " + amount)
        .timeStamp(Utils.getRandomLocalDateTime())
        .build();
  }

  public Customer generateCustomer() {
    return Customer.builder()
        .name("Ivan Horvat")
        .address("Ilica 1")
        .email("ivan@mail.com")
        .phoneNumber("091 0000000")
        .accounts(new ArrayList<>())
        .build();
  }

  public List<Account> generateAccounts(Customer customer) {
    var accounts =
        List.of(
            Account.builder()
                .accountNumber("NL95RABO9961700368")
                .accountType(AccountType.BUSINESS)
                .balance(MAX_BALANCE)
                .customer(customer)
                .senderTransactions(new ArrayList<>())
                .receiverTransactions(new ArrayList<>())
                .build(),
            Account.builder()
                .accountNumber("NL23ABNA5525255073")
                .accountType(AccountType.PERSONAL)
                .balance(MAX_BALANCE)
                .customer(customer)
                .senderTransactions(new ArrayList<>())
                .receiverTransactions(new ArrayList<>())
                .build(),
            Account.builder()
                .accountNumber("NL88INGB7356737620")
                .accountType(AccountType.PERSONAL)
                .balance(MAX_BALANCE)
                .customer(customer)
                .senderTransactions(new ArrayList<>())
                .receiverTransactions(new ArrayList<>())
                .build(),
            Account.builder()
                .accountNumber("NL52INGB4982444048")
                .accountType(AccountType.BUSINESS)
                .balance(MAX_BALANCE)
                .customer(customer)
                .senderTransactions(new ArrayList<>())
                .receiverTransactions(new ArrayList<>())
                .build());

    accounts.forEach((account) -> customer.getAccounts().add(account));

    return accounts;
  }

  public List<Currency> generateCurrencies() {
    return List.of(
        Currency.builder().name("EUR").transactions(new ArrayList<>()).build(),
        Currency.builder().name("USD").transactions(new ArrayList<>()).build(),
        Currency.builder().name("CAD").transactions(new ArrayList<>()).build());
  }
}
